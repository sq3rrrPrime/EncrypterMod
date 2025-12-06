// EncrypterMod.java
package com.sq3rrr.encrypter;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class EncrypterMod implements ClientModInitializer {

    public static final String VERSION = "1.0.0";

    @Override
    public void onInitializeClient() {
        System.out.println("Encrypter Mod v" + VERSION + " initialized!");

        // Load AES key
        ConfigHandler.loadConfig();
        EncryptionUtils.setKey(ConfigHandler.getSecretKey());

        // Register commands
        CommandHandler.register();

        // Register chat listener
        ChatListener.register();
    }

    // Prints mod info in chat (client-side)
    public static void printInfo() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player != null) {
            player.sendMessage(Text.literal("[EncrypterMod] Version: " + VERSION)
                    .formatted(Formatting.AQUA), false);
            player.sendMessage(Text.literal("Authors: sq3rrr(nL66ercatgirl67)").formatted(Formatting.AQUA), false);
            player.sendMessage(Text.literal("Description: Client-side AES chat encryptor.")
                    .formatted(Formatting.GREEN), false);
            player.sendMessage(Text.literal("Description: Share AES key with friends for secure messaging.")
                    .formatted(Formatting.GREEN), false);
            player.sendMessage(Text.literal("Commands: /encrypter encrypt <msg> /encypter decrypt <id>")
                    .formatted(Formatting.GREEN), false);


        }
    }

}
