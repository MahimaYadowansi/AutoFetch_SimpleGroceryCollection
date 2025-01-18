package postman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleGroceryCollection {
	// Fetch API key from environment variable
    private static final String API_KEY = System.getenv("POSTMAN_API_KEY");
    private static final String BASE_URL = "https://api.getpostman.com/collections";
    
    // Directory for storing the collection
    private static final String OUTPUT_DIR = "postman_collections2";  // Folder in the project root

    // Specific collection name to fetch
    private static final String SPECIFIC_COLLECTION_NAME = "Simple Grocery Store API";  // The collection you want to fetch
    
    public static void main(String[] args) {
		
    	if (API_KEY == null || API_KEY.isEmpty()) {
            System.out.println("API Key not found. Please set the POSTMAN_API_KEY environment variable.");
            return;
        }

        try {
            // Ensure the output directory exists or is created in the project root
            Files.createDirectories(Paths.get(OUTPUT_DIR));
            File outputDir = new File(OUTPUT_DIR);
            if (outputDir.exists()) {
                System.out.println("Directory exists or was created successfully.");
            } else {
                System.out.println("Directory creation failed.");
            }

            // Fetch all collections
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(BASE_URL + "?apikey=" + API_KEY) // Append API key as query parameter
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                // Parse and extract collection details
                parseAndSaveCollections(client, responseBody);
            } else {
                System.out.println("Failed to fetch collections: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseAndSaveCollections(OkHttpClient client, String responseBody) {
        // Parse JSON response
        org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
        org.json.JSONArray collections = jsonResponse.getJSONArray("collections");

        System.out.println("Found " + collections.length() + " collections.");
        for (int i = 0; i < collections.length(); i++) {
            org.json.JSONObject collection = collections.getJSONObject(i);
            String collectionId = collection.getString("uid");
            String collectionName = collection.getString("name");

            // Check if the collection name matches the specific collection we're looking for
            if (SPECIFIC_COLLECTION_NAME.equals(collectionName)) {
                System.out.println("Fetching collection: " + collectionName);

                // Fetch individual collection details
                String collectionUrl = BASE_URL + "/" + collectionId + "?apikey=" + API_KEY;
                Request request = new Request.Builder()
                        .url(collectionUrl)
                        .build();

                try (Response collectionResponse = client.newCall(request).execute()) {
                    if (collectionResponse.isSuccessful() && collectionResponse.body() != null) {
                        saveCollection(collectionName, collectionResponse.body().string());
                    } else {
                        System.out.println("Failed to fetch collection: " + collectionName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;  // Stop once we've found the specific collection
            }
        }
    }

    private static void saveCollection(String collectionName, String collectionData) {
        // Clean up collection name to avoid invalid characters in file name
        String safeFileName = collectionName.replaceAll("[^a-zA-Z0-9._-]", "_") + ".json";
        String filePath = Paths.get(OUTPUT_DIR, safeFileName).toString();

        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(collectionData);
            System.out.println("Saved collection: " + filePath);
        } catch (IOException e) {
            System.out.println("Error while saving collection: " + collectionName);
            e.printStackTrace();
        }
    }
}


