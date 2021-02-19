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
package dev.upcraft.isfine.item;

import dev.upcraft.isfine.FinePersistentState;
import dev.upcraft.isfine.ThisIsFine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class ShadesItem extends ArmorItem {

    private static final int FIRE_RESISTANCE_DURATION = 1200; // 1 minute
    private static final int FIRE_RESISTANCE_COOLDOWN = 4800; // +4 minutes, so total 5 minutes

    public ShadesItem(Settings settings) {
        super(Material.INSTANCE, EquipmentSlot.HEAD, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && slot == EquipmentSlot.HEAD.getEntitySlotId()) {
            if (entity instanceof PlayerEntity) { // *should* always be true, but other mods might change that
                PlayerEntity player = (PlayerEntity) entity;
                long currentTime = world.getTime();
                FinePersistentState saved = FinePersistentState.get((ServerWorld) world);
                long lastTime = saved.getLastUpdate(player);
                long diff = currentTime - lastTime;
                if (lastTime == -1 || diff > (FIRE_RESISTANCE_DURATION + FIRE_RESISTANCE_COOLDOWN)) {
                    saved.setUpdated(player, currentTime);
                    if(lastTime == -1) {
                        diff = 0;
                    }
                }
                if(diff <= (FIRE_RESISTANCE_DURATION - 30)) {
                    // apply the fire resistance in short bursts, so that players cannot cheat by swapping the shades for some other helmet
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 30, 0, false, false, false));
                }
                else if(!player.getItemCooldownManager().isCoolingDown(this)) {
                    player.getItemCooldownManager().set(this, FIRE_RESISTANCE_COOLDOWN);
                }
            }
        }
    }

    private enum Material implements ArmorMaterial {
        INSTANCE;

        private final String name = ThisIsFine.id("shades").toString().replace(':', '.');

        @Override
        public int getDurability(EquipmentSlot slot) {
            return 0;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 9;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }
}
