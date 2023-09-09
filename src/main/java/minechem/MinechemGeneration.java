package minechem;

import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import minechem.utils.LogHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class MinechemGeneration implements IWorldGenerator
{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (Settings.generateOre)
        {
            if (world.provider.isSurfaceWorld())
            {

                for (int k = 0; k <= Settings.UraniumOreDensity; k++)
                {
                    int firstBlockXCoord = (16 * chunkX) + random.nextInt(16);
                    int firstBlockYCoord = random.nextInt(50);
                    int firstBlockZCoord = (16 * chunkZ) + random.nextInt(16);
                    int oreCount = random.nextInt(Settings.UraniumOreClusterSize + 10);

                    (new WorldGenMinable(MinechemBlocksGeneration.uranium, oreCount)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
                    LogHelper.debug("Minechem generated Uranium generated at:");
                    LogHelper.debug("X :" + firstBlockXCoord);
                    LogHelper.debug("Y :" + firstBlockYCoord);
                    LogHelper.debug("Z :" + firstBlockZCoord);
                }
            }

        }
    }
}
