package com.sq3rrr.encrypter;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.Formatting;


public class ChatListener {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    // Detect: [ID: something] encryptedData
    private static final Pattern ID_PATTERN = Pattern.compile("\\[ID: ([^\\]]+)]\\s+(.+)");

    public static void register() {

        // 1) PLAYER chat (Not Secure + Secure)
        ClientReceiveMessageEvents.CHAT.register((message, sender, params, signedMessage, receptionType) -> {
            onChat(message);
        });

        // 2) SYSTEM chat (server messages)
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            onChat(message);
        });
    }

    private static void onChat(Text message) {
        String msg = message.getString();

        // Skip our own mod messages
        if (msg.startsWith("[EncrypterMod]")) return;

        Matcher matcher = ID_PATTERN.matcher(msg);
        if (!matcher.find()) return;

        String id = matcher.group(1).trim();
        String encrypted = matcher.group(2).trim();

        // Store message
        ChatHandler.storeEncryptedMessage(id, encrypted);

        // Notify user
        if (mc.player != null) {
            mc.player.sendMessage(
                    Text.literal("[EncrypterMod] Message ID detected: " + id)
                            .formatted(net.minecraft.util.Formatting.AQUA),
                    false // this stays in sendMessage
            );
        }


        // --- AUTO-DECRYPT ---
        if (ChatHandler.isAutoDecryptEnabled()) {
            String decrypted = ChatHandler.tryDecrypt(id);
            if (decrypted != null && mc.player != null) {
                mc.player.sendMessage(
                        Text.literal("→ [Decrypted] " + decrypted).formatted(Formatting.AQUA),
                        false
                );
            }
        }



    }}
