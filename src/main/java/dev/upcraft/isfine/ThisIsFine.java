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
package dev.upcraft.isfine;

import dev.hephaestus.fiblib.FibLib;
import dev.hephaestus.fiblib.blocks.BlockFib;
import dev.upcraft.isfine.init.FineItems;
import io.github.glasspane.mesh.api.annotation.CalledByReflection;
import io.github.glasspane.mesh.api.logging.MeshLoggerFactory;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@CalledByReflection
public class ThisIsFine implements ModInitializer {

    private static final Logger logger = MeshLoggerFactory.createLogger("ThisIsFine");
    public static final String MODID = "this_is_fine";

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public static boolean shouldHideFireFor(@Nullable PlayerEntity player) {
        return player != null && player.getEquippedStack(EquipmentSlot.HEAD).getItem() == FineItems.SHADES;
    }

    @Override
    public void onInitialize() {
        // @formatter:off
        String spacer = "--------------------------";
        logger.info("\n" +
                "{}\n" +
                "FROM: <Minecraft>\n" +
                "SENT: {}\n" +
                "TO: <YOU>\n" +
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
                "{}", spacer, Instant.now(), spacer);
        // @formatter:on
        registerFib(Blocks.FIRE);
        registerFib(Blocks.SOUL_FIRE);
    }

    private static void registerFib(Block toHide) {
        toHide.getStateManager().getStates().forEach(ThisIsFine::registerFib);
    }

    private static void registerFib(BlockState toHide) {
        FibLib.Blocks.register(new BlockFib(toHide, Blocks.AIR.getDefaultState(), ThisIsFine::shouldHideFireFor));
    }
}
