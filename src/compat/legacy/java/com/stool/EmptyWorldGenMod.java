package com.stool;

import com.stool.world.EmptyWorldChunkGenerator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EmptyWorldGenMod implements ModInitializer {
    public static final String MOD_ID = "emptyworld";
    public static final Identifier EMPTY_WORLD_CHUNK_GENERATOR_ID = new Identifier(MOD_ID, "empty_world");

    @Override
    public void onInitialize() {
        Registry.register(Registry.CHUNK_GENERATOR, EMPTY_WORLD_CHUNK_GENERATOR_ID, EmptyWorldChunkGenerator.CODEC);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            var overworld = server.getOverworld();
            if (overworld == null) {
                return;
            }

            ChunkGenerator generator = overworld.getChunkManager().getChunkGenerator();
            if (!(generator instanceof EmptyWorldChunkGenerator)) {
                return;
            }

            BlockPos spawnTop = new BlockPos(0, 64, 0);
            BlockPos bedrockPos = spawnTop.down();

            overworld.setBlockState(bedrockPos, Blocks.BEDROCK.getDefaultState());
            overworld.setSpawnPos(spawnTop, 0.0F);
        });
    }
}
