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

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import java.util.UUID;

public class FinePersistentState extends PersistentState {

    private static final String ID = ThisIsFine.id("persistent_state").toString().replace(':', '_');

    private final Object2LongMap<UUID> lastUpdated = new Object2LongArrayMap<>();

    public static FinePersistentState get(ServerWorld world) {
        return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(FinePersistentState::new, ID);
    }

    private FinePersistentState() {
        super(ID);
    }

    public long getLastUpdate(PlayerEntity player) {
        return lastUpdated.getOrDefault(player.getUuid(), -1);
    }

    public void setUpdated(PlayerEntity player, long time) {
        synchronized(lastUpdated) {
            lastUpdated.put(player.getUuid(), time);
        }
        this.markDirty();
    }

    @Override
    public void fromTag(CompoundTag tag) {
        ListTag values = tag.getList("values", NbtType.COMPOUND);
        synchronized(lastUpdated) {
            lastUpdated.clear();
            for (int i = 0; i < values.size(); i++) {
                CompoundTag nbt = values.getCompound(i);
                UUID id = nbt.getUuid("id");
                long value = nbt.getLong("last_updated");
                lastUpdated.put(id, value);
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        ListTag values = new ListTag();
        synchronized (lastUpdated) {
            lastUpdated.keySet().forEach(uuid -> {
                CompoundTag nbt = new CompoundTag();
                nbt.putUuid("id", uuid);
                nbt.putLong("last_updated", lastUpdated.getLong(uuid));
                values.add(nbt);
            });
        }
        tag.put("values", values);
        return tag;
    }
}
