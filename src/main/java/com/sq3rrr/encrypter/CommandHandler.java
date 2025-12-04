package com.sq3rrr.encrypter;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

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
            );
        });
    }
}
