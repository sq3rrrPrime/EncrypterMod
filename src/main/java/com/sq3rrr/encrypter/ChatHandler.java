package com.sq3rrr.encrypter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatHandler {

    private static final Map<String, StoredMessage> storedEncryptedMessages = new HashMap<>();
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static class StoredMessage {
        final String sender;
        final String encrypted;

        StoredMessage(String sender, String encrypted) {
            this.sender = sender;
            this.encrypted = encrypted;
        }
    }

    public static void sendEncryptedMessage(String msg) {
        if (mc.player == null) return;

        String encrypted = EncryptionUtils.encrypt(msg);
        String senderName = mc.player.getName().getString();
        String id = generateMessageId(senderName);

        storedEncryptedMessages.put(id, new StoredMessage(senderName, encrypted));

        sendChat("[ID: " + id + "] " + encrypted);
        sendLocalFeedback("Message ID: " + id + " (share with friends for decrypt)");
    }

    public static void decryptMessage(String id) {
        StoredMessage stored = storedEncryptedMessages.get(id);
        if (stored == null) {
            sendLocalFeedback("No message found with ID: " + id);
            return;
        }

        String decrypted = EncryptionUtils.decrypt(stored.encrypted);
        sendLocalFeedback("[Decrypted] " + decrypted);
    }

    private static String generateMessageId(String sender) {
        return sender.toLowerCase() + "_" + UUID.randomUUID();
    }

    private static void sendChat(String msg) {
        ClientPlayerEntity player = mc.player;
        if (player == null) return;
        player.networkHandler.sendChatMessage(msg);
    }

    private static void sendLocalFeedback(String msg) {
        if (mc.player == null) return;
        mc.player.sendMessage(net.minecraft.text.Text.literal("[EncrypterMod] " + msg), false);
    }

    public static void storeEncryptedMessage(String id, String encrypted) {
        String senderName = "unknown";
        storedEncryptedMessages.put(id, new StoredMessage(senderName, encrypted));
    }


}
