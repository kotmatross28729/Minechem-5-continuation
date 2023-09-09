package minechem.tileentity.microscope;

import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiToggleSwitch;
import minechem.reference.Resources;

public class MicroscopeGuiSwitch extends GuiToggleSwitch
{

    public MicroscopeGuiSwitch(GuiContainerTabbed container)
    {
        super();
        this.container = container;
        this.width = 13;
        this.height = 13;
        this.numStates = 2;
        this.texture = Resources.Gui.MICROSCOPE;
        ToggleButton buttonSynthesis = new ToggleButton();
        buttonSynthesis.u = 176;
        buttonSynthesis.v = 124;
        buttonSynthesis.tooltip = "gui.title.synthesis";

        ToggleButton buttonDecomposer = new ToggleButton();
        buttonDecomposer.u = 189;
        buttonDecomposer.v = 124;
        buttonDecomposer.tooltip = "gui.title.decomposer";
        this.buttons.put(0, buttonSynthesis);
        this.buttons.put(1, buttonDecomposer);
    }
}
