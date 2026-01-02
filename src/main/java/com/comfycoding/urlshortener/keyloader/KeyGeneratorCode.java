package com.comfycoding.urlshortener.keyloader;

import com.comfycoding.urlshortener.utils.Constants;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class KeyGeneratorCode {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {

        // ---- CONFIG ----
        String connectionString = "mongodb+srv://comfyd:comfyd@cluster1.7eiaz.mongodb.net/?appName=Cluster1";
        String DB_NAME = "url_shortener";
        String COLLECTION_NAME = "keys_pool";
        int TOTAL_KEYS = 1000000;
        int BATCH_SIZE = 10000;
        // ----------------

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            System.out.println("Connected to MongoDB Atlas!");
            
            // Create index on isUsed field for faster queries when finding unused keys
            System.out.println("Creating index on 'isUsed' field...");
            collection.createIndex(Indexes.ascending("isUsed"));
            
            System.out.println("Starting key generation...");

            for (int start = 0; start < TOTAL_KEYS; start += BATCH_SIZE) {

                List<Document> batch = new ArrayList<>(BATCH_SIZE);

                for (int i = 0; i < BATCH_SIZE; i++) {
                    String key = toBase62(start + i);

                    Document doc = new Document("_id", key)
                            .append("isUsed", false)
                            .append("createdAt", System.currentTimeMillis());

                    batch.add(doc);
                }

                // Bulk insert (unordered = faster)
                try {
                    collection.insertMany(batch, new InsertManyOptions().ordered(false));
                    System.out.println("Inserted batch: " + (start + 1) + " to " + Math.min(start + BATCH_SIZE, TOTAL_KEYS) + " / " + TOTAL_KEYS);
                } catch (Exception e) {
                    System.err.println("Error inserting batch starting at " + start + ": " + e.getMessage());
                    throw e;
                }
            }

            System.out.println("Successfully inserted " + TOTAL_KEYS + " keys!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Convert integer → fixed 6-character Base62
    private static String toBase62(int num) {
        if (num == 0) {
            return "000000";
        }
        
        StringBuilder sb = new StringBuilder();
        
        // Convert to Base62 (build in reverse order)
        while (num > 0) {
            sb.append(BASE62.charAt(num % 62));
            num /= 62;
        }
        
        // Reverse to get correct order
        sb.reverse();
        
        // Pad with leading zeros to make it 6 characters
        while (sb.length() < Constants.SHORT_KEY_LENGTH) {
            sb.insert(0, '0');
        }
        
        return sb.toString();
    }
}
