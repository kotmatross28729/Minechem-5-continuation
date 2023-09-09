package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeZirconium extends PolytoolUpgradeType
{
    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
        if (block == Blocks.dirt)
        {
            if (rand.nextInt(8192) < 1 + power)
            {
                world.spawnEntityInWorld(new EntityItem(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), new ItemStack(Items.diamond, 1, 0)));
            }
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Zn;
    }


    @Override
    public String getDescription()
    {
        return "Makes fake diamonds when mining dirt";
    }

}
