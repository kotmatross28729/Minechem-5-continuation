package minechem.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.reference.Resources;
import minechem.tileentity.prefab.MinechemTileEntityElectric;
import minechem.utils.MinechemUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public abstract class GuiTabStateControl extends GuiTab
{
    protected enum TabState
    {
        jammed(MinechemUtil.getLocalString("tab.tooltip.jammed"), 0xAA0000, Resources.Icon.JAMMED),
        noBottles(MinechemUtil.getLocalString("tab.tooltip.nobottles"), 0xAA0000, Resources.Icon.NO_BOTTLES),
        powered(MinechemUtil.getLocalString("tab.tooltip.powered"), 0x00CC00, Resources.Icon.ENERGY),
        unpowered(MinechemUtil.getLocalString("tab.tooltip.unpowered"), 0xAA0000, Resources.Icon.NO_ENERGY),
        norecipe(MinechemUtil.getLocalString("tab.tooltip.norecipe"), 0xAA0000, Resources.Icon.NO_RECIPE);

        public String tooltip;
        public int color;

        @SideOnly(Side.CLIENT)
        public IIcon icon;
        @SideOnly(Side.CLIENT)
        public ResourceLocation resource;

        private TabState(String tooltip, int color, ResourceLocation resource)
        {
            this.tooltip = tooltip;
            this.color = color;
            this.resource = resource;
        }
    }

    int headerColour = 0xe1c92f;
    int subheaderColour = 0xaaafb8;
    int textColour = 0x000000;

    protected TabState state;
    protected MinechemTileEntityElectric tileEntity;

    public GuiTabStateControl(Gui gui)
    {
        super(gui);
        this.state = TabState.unpowered;
        this.minWidth = 16 + 9;
        this.minHeight = 16 + 10;
        this.maxHeight = this.minHeight + 75;
        this.maxWidth = this.minWidth + 80;
    }

    @Override
    public void draw(int x, int y)
    {
        drawBackground(x, y);
        drawIcon(x + 3, y + 5);
        if (!isFullyOpened())
        {
            return;
        }

        // State
        fontRenderer.drawStringWithShadow(MinechemUtil.getLocalString(this.state.tooltip), x + 22, y + 10, headerColour);

        // Amount of power stored.
        fontRenderer.drawStringWithShadow(MinechemUtil.getLocalString("tab.title.stored") + ":", x + 5, y + 30, subheaderColour);
        String print = String.valueOf(tileEntity.getEnergyStored()) + " RF ("
                + String.valueOf(tileEntity.getPowerRemainingScaled(100D)) + "%)";
        fontRenderer.drawString(print, x + 5, y + 40, textColour);

        fontRenderer.drawStringWithShadow(MinechemUtil.getLocalString("tab.title.activationEnergy"), x + 5, y + 60, subheaderColour);
        fontRenderer.drawString(String.valueOf(tileEntity.getEnergyNeeded()) + " RF", x + 5, y + 70, textColour);
    }

    @Override
    public String getTooltip()
    {
        if (!isFullyOpened())
        {
            return this.state.tooltip;
        }
        return null;
    }

    @Override
    public ResourceLocation getIcon()
    {
        return this.state.resource;
    }
}
