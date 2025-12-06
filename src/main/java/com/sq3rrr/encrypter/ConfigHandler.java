package com.sq3rrr.encrypter;

import javax.crypto.SecretKey;

public class ConfigHandler {

    private static SecretKey secretKey;

    public static void loadConfig() {
        System.out.println("[ConfigHandler] Loading AES key using KeyConfigHandler...");
        secretKey = KeyConfigHandler.loadKey();
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }

    public static void saveConfig() {
        System.out.println("[ConfigHandler] Saving AES key using KeyConfigHandler...");
        KeyConfigHandler.saveKey(secretKey);
    }


}
