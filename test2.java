import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubLookup {
    public static void main(String[] args) {
        // 1. Put your details here
        String owner = "facebook";
        String repo = "react";

        // 2. Build the GitHub API URL
        String urlString = "https://github.com" + owner + "/" + repo;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // GitHub API strictly requires a User-Agent header
            conn.setRequestProperty("User-Agent", "Java-Script");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();

                // 3. Extract the ID and Default Branch using simple string manipulation
                String githubId = parseValue(jsonResponse, "\"id\":");
                String defaultBranch = parseValue(jsonResponse, "\"default_branch\":");

                // 4. Print the results
                System.out.println("--- GitHub Repository Details ---");
                System.out.println("Owner Name:     " + owner);
                System.out.println("Repo Name:      " + repo);
                System.out.println("GitHub ID:      " + githubId + "  <-- Paste this into your form!");
                System.out.println("Default Branch: " + defaultBranch);

            } else {
                System.out.println("Error: Received HTTP response code " + responseCode);
                System.out.println("Please check that the owner and repository names are correct.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to roughly pull values out of the text response
    private static String parseValue(String json, String key) {
        int index = json.indexOf(key);
        if (index == -1) return "Not Found";
        
        int start = index + key.length();
        // Move past spaces or quotes
        while (json.charAt(start) == ' ' || json.charAt(start) == ':') {
            start++;
        }
        
        boolean isQuoted = json.charAt(start) == '"';
        if (isQuoted) start++; // Skip opening quote
        
        int end = start;
        while (end < json.length()) {
            char c = json.charAt(end);
            if (isQuoted && c == '"') break;
            if (!isQuoted && (c == ',' || c == '}')) break;
            end++;
        }
        
        return json.substring(start, end).trim();
    }
}
