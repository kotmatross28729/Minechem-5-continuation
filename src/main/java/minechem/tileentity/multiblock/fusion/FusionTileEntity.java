package minechem.tileentity.multiblock.fusion;

import cpw.mods.fml.common.network.NetworkRegistry;
import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.item.blueprint.BlueprintFusion;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.network.MessageHandler;
import minechem.network.message.FusionUpdateMessage;
import minechem.tileentity.multiblock.MultiBlockTileEntity;
import minechem.utils.MinechemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class FusionTileEntity extends MultiBlockTileEntity implements ISidedInventory
{
    public static boolean canProcess = false;
    public static int fusedResult = 0;
    public static int inputLeft = 0;
    public static int inputRight = 1;
    public static int output = 2;

    public FusionTileEntity()
    {
        super(Settings.maxFusionStorage);
        this.inventory = new ItemStack[getSizeInventory()];
        setBlueprint(new BlueprintFusion());
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side)
    {
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side)
    {
        return false;
    }

    private void fuseInputs()
    {
        if (inventory[output] == null)
        {
            inventory[output] = new ItemStack(MinechemItemsRegistration.element, 1, fusedResult);
        } else if (inventory[output].getItemDamage() == fusedResult)
        {
            inventory[output].stackSize++;
        } else
        {
            canProcess = false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        switch(side/2)
        {
            case 0:
                return new int[]{output};
            case 1:
                return new int[]{inputLeft};
            case 2:
                return new int[]{inputRight};
        }
        return new int[0];
    }

    @Override
    public String getInventoryName()
    {
        return "container.minechemFusion";
    }

    @Override
    public int getSizeInventory()
    {
        return 4;
    }

    public boolean inputsCanBeFused()
    {
        if (inventory[inputLeft] != null && inventory[inputRight] != null)
        {
            if (inventory[inputLeft].getItem() instanceof ElementItem && inventory[inputRight].getItem() instanceof ElementItem)
            {
                int left = inventory[inputLeft].getItemDamage();
                int right = inventory[inputRight].getItemDamage();
                fusedResult = left + right;
                return (left > 0 && right > 0 && ElementEnum.getByID(fusedResult) != null);
            }
        }
        return false;

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        if (slot == inputLeft || slot == inputRight)
        {
            if (itemstack.getItem() instanceof ElementItem)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return completeStructure;
    }

    @Override
    public void openInventory()
    {}

    @Override
    public void closeInventory()
    {}

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        fusedResult = nbtTagCompound.getInteger("fusedResult");
        canProcess = nbtTagCompound.getBoolean("canProcess");
        inventory = new ItemStack[getSizeInventory()];
        MinechemUtil.readTagListToItemStackArray(nbtTagCompound.getTagList("inventory", Constants.NBT.TAG_COMPOUND), inventory);
    }

    private void removeInputs()
    {
        decrStackSize(inputLeft, 1);
        decrStackSize(inputRight, 1);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        this.inventory[slot] = itemstack;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!completeStructure)
        {
            return;
        }
        if (!worldObj.isRemote)
        {
            if (!canProcess)
            {
                if (this.getEnergyNeeded() < this.getEnergyStored() && inputsCanBeFused() && canOutput())
                {
                    canProcess = true;
                }
            }
            if (canProcess && this.useEnergy(this.getEnergyNeeded()))
            {
                fuseInputs();
                removeInputs();
                canProcess = false;
            } else
            {
                fusedResult = 0;
            }
            FusionUpdateMessage message = new FusionUpdateMessage(this);
            MessageHandler.INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, Settings.UpdateRadius));
        }
    }

    public boolean canOutput()
    {
        if (inventory[output] == null)
        {
            return true;
        } else if (inventory[output].getItemDamage() == fusedResult)
        {
            return inventory[output].stackSize < 64;
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("fusedResult", fusedResult);
        nbtTagCompound.setBoolean("canProcess", canProcess);
        NBTTagList inventoryTagList = MinechemUtil.writeItemStackArrayToTagList(inventory);
        nbtTagCompound.setTag("inventory", inventoryTagList);
    }

    @Override
    public int getEnergyNeeded()
    {
        if (inventory[inputLeft] != null && inventory[inputRight] != null && this.inputsCanBeFused())
        {
            return (inventory[inputLeft].getItemDamage() + inventory[inputRight].getItemDamage()) * Settings.fusionMultiplier;
        }
        return 0;
    }
}
