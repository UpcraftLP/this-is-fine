/*
 * this-is-fine
 * Copyright (C) 2021-2021 UpcraftLP
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dev.upcraft.isfine.client;

import dev.upcraft.isfine.ThisIsFine;
import dev.upcraft.isfine.init.FineItems;
import io.github.glasspane.mesh.api.annotation.CalledByReflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.minecraft.client.MinecraftClient;

import java.time.Instant;

@Environment(EnvType.CLIENT)
@CalledByReflection
public class ClientIsFine implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // @formatter:off
        String spacer = "--------------------------";
        ThisIsFine.getLogger().info("\n" +
                "{}\n" +
                "FROM: <Minecraft>\n" +
                "SENT: {}\n" +
                "TO: <{}@{}>\n" +
                "SUBJECT: Fire!\n" +
                "\n" +
                "\n" +
                "Dear Sir/Madam,\n" +
                "\n" +
                "Fire! Fire! Help me!\n" +
                "123 Carenden Road\n" +
                "\n" +
                "Looking forward to hearing from you\n" +
                "All the best, Maurice Moss\n" +
                "\n" +
                "{}", spacer, MinecraftClient.getInstance().getSession().getUsername(), MinecraftClient.getInstance().getSession().getUuid(), Instant.now(), spacer);
        // @formatter:on
        ArmorRenderingRegistry.registerSimpleTexture(ThisIsFine.id("shades"), FineItems.SHADES);
    }
}
