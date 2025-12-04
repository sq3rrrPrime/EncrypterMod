package com.sq3rrr.encrypter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class KeyConfigHandler {

    private static final File CONFIG_FILE = new File("config/encrypter/key.json");
    private static final Gson gson = new Gson();

    // Load AES key from file
    public static SecretKey loadKey() {
        try {
            if (!CONFIG_FILE.exists()) {
                createDefaultKey();
                System.out.println("[KeyConfig] Created default key.json");
            }

            JsonObject json = gson.fromJson(new FileReader(CONFIG_FILE), JsonObject.class);
            String keyString = json.get("aes_key").getAsString();

            System.out.println("[KeyConfig] Loaded AES key: " + keyString);

            byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
            return new SecretKeySpec(keyBytes, "AES");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Save AES key to file
    public static void saveKey(SecretKey key) {
        try {
            CONFIG_FILE.getParentFile().mkdirs();

            String keyStr = new String(key.getEncoded(), StandardCharsets.UTF_8);

            JsonObject json = new JsonObject();
            json.addProperty("aes_key", keyStr);

            FileWriter writer = new FileWriter(CONFIG_FILE);
            gson.toJson(json, writer);
            writer.flush();
            writer.close();

            System.out.println("[KeyConfig] Saved AES key");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // First-time default key
    private static void createDefaultKey() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();

            JsonObject json = new JsonObject();
            json.addProperty("aes_key", "1234567890abcdef1234567890abcdef");

            FileWriter writer = new FileWriter(CONFIG_FILE);
            gson.toJson(json, writer);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
