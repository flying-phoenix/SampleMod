package com.tan90.examplemod.world.gen;

import com.tan90.examplemod.init.ModBlocks;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGeneratorAsh implements IWorldGenerator
{
    private final WorldGenMinable ashGen = new WorldGenMinable(ModBlocks.ashBlock, 32);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.dimensionId)
        {
            case 0:
                if (random.nextInt(10) == 0)
                {
                    int x = chunkX * 16;
                    int z = chunkZ * 16;
                    int y = world.getHeightValue(x, z);
                    x += random.nextInt(16);
                    z += random.nextInt(16);
                    y += random.nextInt(30);

                    for (int i = 0; i < 10; i++)
                    {
                        if (i % 2 == 0)
                        {
                            world.setBlock(x + 1, y + i, z, ModBlocks.ashBlock, 0, 2);
                            world.setBlock(x - 1, y + i, z, ModBlocks.ashBlock, 0, 2);
                            world.setBlock(x, y + i, z + 1, ModBlocks.ashBlock, 0, 2);
                            world.setBlock(x, y + i, z - 1, ModBlocks.ashBlock, 0, 2);
                        }
                    }

                    ashGen.generate(world, random, x, 10 + random.nextInt(40), z);
                }
                break;

            default:
        }
    }
}
