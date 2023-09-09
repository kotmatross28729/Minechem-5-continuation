package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeChromium extends PolytoolUpgradeType
{
    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
        if (!world.isRemote && rand.nextInt(10) < power)
        {
            if (block == Blocks.wool)
            {
                world.setBlockToAir(x, y, z);

                world.spawnEntityInWorld(new EntityItem(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), new ItemStack(Blocks.wool, 1, rand.nextInt(15))));
            }
        }

    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Cr;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {
        return "Chance to change color of wool when mined";
    }

}
