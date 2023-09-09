package minechem.gui;

import cpw.mods.fml.client.FMLClientHandler;
import minechem.reference.Resources;
import minechem.utils.LogHelper;
import minechem.utils.SessionVars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class GuiTab
{
    public FontRenderer tabFontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
    public SoundHandler tabSoundManager = FMLClientHandler.instance().getClient().getSoundHandler();

    private boolean open;

    protected Gui myGui;
    public boolean leftSide;
    protected int overlayColor = 0xffffff;

    public int currentX = 0;
    public int currentY = 0;

    protected int limitWidth = 128;
    protected int maxWidth = 124;
    protected int minWidth = 22;
    protected int currentWidth = minWidth;

    protected int maxHeight = 22;
    protected int minHeight = 22;
    protected int currentHeight = minHeight;
    protected FontRenderer fontRenderer;
    protected TextureManager renderEngine;

    public GuiTab(Gui gui)
    {
        myGui = gui;
        fontRenderer = Minecraft.getMinecraft().fontRenderer;
        renderEngine = Minecraft.getMinecraft().renderEngine;
    }

    public abstract void draw(int x, int y);

    public void drawTab(int x, int y)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        currentX = x;
        currentY = y;

        draw(x, y);

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    protected void drawBackground(int x, int y)
    {

        float colorR = (overlayColor >> 16 & 255) / 255.0F;
        float colorG = (overlayColor >> 8 & 255) / 255.0F;
        float colorB = (overlayColor & 255) / 255.0F;

        GL11.glColor4f(colorR, colorG, colorB, 1.0F);

        if (leftSide)
        {

            Minecraft.getMinecraft().renderEngine.bindTexture(Resources.Tab.LEFT);

            myGui.drawTexturedModalRect(x - currentWidth, y + 4, 0, 256 - currentHeight + 4, 4, currentHeight - 4);
            myGui.drawTexturedModalRect(x - currentWidth + 4, y, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
            myGui.drawTexturedModalRect(x - currentWidth, y, 0, 0, 4, 4);
            myGui.drawTexturedModalRect(x - currentWidth + 4, y + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);
        } else
        {

            Minecraft.getMinecraft().renderEngine.bindTexture(Resources.Tab.RIGHT);

            myGui.drawTexturedModalRect(x, y, 0, 256 - currentHeight, 4, currentHeight);
            myGui.drawTexturedModalRect(x + 4, y, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
            myGui.drawTexturedModalRect(x, y, 0, 0, 4, 4);
            myGui.drawTexturedModalRect(x + 4, y + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }

    protected void drawIcon(int x, int y)
    {
        ResourceLocation resource = this.getIcon();
        if (myGui instanceof GuiContainerTabbed)
        {
            ((GuiContainerTabbed) myGui).drawTexture(x, y, resource);
        } else
        {
            LogHelper.debug("Failed to draw tab icons on a Minechem gui that was not GuiContainerTabbed.");
        }
    }

    public int getHeight()
    {
        return currentHeight;
    }

    public int getWidth()
    {
        return currentWidth;
    }

    public abstract ResourceLocation getIcon();

    public abstract String getTooltip();

    public boolean intersectsWith(int mouseX, int mouseY, int shiftX, int shiftY)
    {

        if (leftSide)
        {
            if (mouseX <= shiftX && mouseX >= shiftX - currentWidth && mouseY >= shiftY && mouseY <= shiftY + currentHeight)
            {
                return true;
            }
        } else if (mouseX >= shiftX && mouseX <= shiftX + currentWidth && mouseY >= shiftY && mouseY <= shiftY + currentHeight)
        {
            return true;
        }
        return false;
    }

    protected boolean isFullyOpened()
    {

        return currentWidth >= maxWidth;
    }

    public boolean isOpen()
    {

        return open;
    }

    public boolean isVisible()
    {

        return true;
    }

    public void setFullyOpen()
    {

        open = true;
        currentWidth = maxWidth;
        currentHeight = maxHeight;
    }

    public void toggleOpen()
    {

        if (open)
        {
            open = false;
            SessionVars.setOpenedTab(null);
        } else
        {
            open = true;
            SessionVars.setOpenedTab(this.getClass());
        }
    }

    public void update()
    {

        if (open && currentWidth < maxWidth)
        {
            currentWidth += 8;
        } else if (!open && currentWidth > minWidth)
        {
            currentWidth -= 8;
        }

        if (currentWidth > maxWidth)
        {
            currentWidth = maxWidth;
        } else if (currentWidth < minWidth)
        {
            currentWidth = minWidth;
        }

        if (open && currentHeight < maxHeight)
        {
            currentHeight += 8;
        } else if (!open && currentHeight > minHeight)
        {
            currentHeight -= 8;
        }

        if (currentHeight > maxHeight)
        {
            currentHeight = maxHeight;
        } else if (currentHeight < minHeight)
        {
            currentHeight = minHeight;
        }
    }
}
