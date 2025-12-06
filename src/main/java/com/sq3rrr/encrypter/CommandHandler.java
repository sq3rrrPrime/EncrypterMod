package com.sq3rrr.encrypter;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandHandler {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("encrypter")
                            .then(literal("info")
                                    .executes(ctx -> {
                                        EncrypterMod.printInfo();
                                        return 1;
                                    })
                            )
                            .then(literal("encrypt")
                                    .then(argument("message", StringArgumentType.greedyString())
                                            .executes(ctx -> {
                                                String msg = StringArgumentType.getString(ctx, "message");
                                                ChatHandler.sendEncryptedMessage(msg);
                                                return 1;
                                            })
                                    )
                            )
                            .then(literal("decrypt")
                                    .then(argument("id", StringArgumentType.string())
                                            .executes(ctx -> {
                                                String id = StringArgumentType.getString(ctx, "id");
                                                ChatHandler.decryptMessage(id);
                                                return 1;
                                            })
                                    )
                            )
                            // --- Auto-decrypt toggle command ---
                            .then(literal("toggleAutoDecrypt")
                                    .executes(ctx -> {
                                        boolean current = ChatHandler.isAutoDecryptEnabled();
                                        ChatHandler.setAutoDecrypt(!current); // toggle

                                        // Send feedback directly to the client player
                                        if (MinecraftClient.getInstance().player != null) {
                                            MinecraftClient.getInstance().player.sendMessage(
                                                    Text.literal("[EncrypterMod] Auto-decrypt is now " + (!current ? "ON" : "OFF")),
                                                    false
                                            );
                                        }
                                        return 1;
                                    })
                            )

            );
        });
    }
}
