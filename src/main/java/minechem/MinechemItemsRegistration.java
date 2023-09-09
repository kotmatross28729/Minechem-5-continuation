package minechem;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.fluid.FluidHelper;
import minechem.item.ItemAtomicManipulator;
import minechem.item.OpticalMicroscopeLens;
import minechem.item.blueprint.ItemBlueprint;
import minechem.item.chemistjournal.ChemistJournalItem;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.item.polytool.PolytoolItem;
import minechem.reference.Reference;
import minechem.utils.MinechemFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class MinechemItemsRegistration
{
    public static ElementItem element;
    public static MoleculeItem molecule;
    public static OpticalMicroscopeLens lens;
    public static ItemAtomicManipulator atomicManipulator;
    public static ItemBlueprint blueprint;
    public static ChemistJournalItem journal;
    public static ItemStack convexLens;
    public static ItemStack concaveLens;
    public static ItemStack projectorLens;
    public static ItemStack microscopeLens;
    public static ItemStack minechempills;
    public static Item polytool;
    public static ItemStack emptyTube;

    public static void init()
    {
        element = new ElementItem();
        GameRegistry.registerItem(element, Reference.ID + "Element");

        molecule = new MoleculeItem();
        GameRegistry.registerItem(molecule, Reference.ID + "Molecule");

        lens = new OpticalMicroscopeLens();
        concaveLens = new ItemStack(lens, 1, 0);
        convexLens = new ItemStack(lens, 1, 1);
        microscopeLens = new ItemStack(lens, 1, 2);
        projectorLens = new ItemStack(lens, 1, 3);
        GameRegistry.registerItem(lens, Reference.ID + "OpticalMicroscopeLens");

        atomicManipulator = new ItemAtomicManipulator();
        GameRegistry.registerItem(atomicManipulator, Reference.ID + "AtomicManipulator");

        blueprint = new ItemBlueprint();
        GameRegistry.registerItem(blueprint, Reference.ID + "Blueprint");

        journal = new ChemistJournalItem();
        GameRegistry.registerItem(journal, Reference.ID + "Journal");

        polytool = new PolytoolItem();
        GameRegistry.registerItem(polytool, Reference.ID + "Polytool");

        emptyTube = new ItemStack(MinechemItemsRegistration.element, 1, 0);
    }

    public static void registerFluidContainers()
    {
        for (ElementEnum element : ElementEnum.elements.values())
        {
            if (element != null)
            {
                ItemStack tube = new ItemStack(MinechemItemsRegistration.element, 1, element.atomicNumber());
                FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidHelper.elements.get(element), 125), tube, emptyTube);
            }
        }

        for (MoleculeEnum molecule : MoleculeEnum.molecules.values())
        {
            if (molecule != null)
            {
                ItemStack tube = new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id());
                FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, 125);
                if (!molecule.name().equals("water"))
                {
                    fluidStack = new FluidStack(FluidHelper.molecules.get(molecule), 125);
                }
                FluidContainerRegistry.registerFluidContainer(fluidStack, tube, emptyTube);
            }
        }
    }

    public static void registerToOreDictionary()
    {
        for (ElementEnum element : ElementEnum.elements.values())
        {
            OreDictionary.registerOre("element_" + element.name(), new ItemStack(MinechemItemsRegistration.element, 1, element.atomicNumber()));
        }
        for (MoleculeEnum molecule : MoleculeEnum.molecules.values())
        {
            OreDictionary.registerOre("molecule_" + molecule.name(), new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id()));
        }
        OreDictionary.registerOre("dustSaltpeter", new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.potassiumNitrate.id()));
        OreDictionary.registerOre("dustSalt", new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.salt.id()));
        OreDictionary.registerOre("quicksilver", new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.Hg.atomicNumber()));
    }

    public static void addDungeonLoot()
    {
        ChestGenHooks ChestProvider = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
        ItemStack A = new ItemStack(blueprint, 1, 0);
        ItemStack B = new ItemStack(blueprint, 1, 1);
        ChestProvider.addItem(new WeightedRandomChestContent(A, 10, 80, 1));
        ChestProvider.addItem(new WeightedRandomChestContent(B, 10, 80, 1));
    }

    public static void registerFuelValues()
    {
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.C.atomicNumber()), 200);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.H.atomicNumber()), 100);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.S.atomicNumber()), 300);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.P.atomicNumber()), 250);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.cellulose.id()), 65);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.meoh.id()), 500);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.ethanol.id()), 1100);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.aalc.id()), 800);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.propane.id()), 1400);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.toluene.id()), 2200);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.tnt.id()), 4000);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.isoprene.id()), 1800);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.butene.id()), 1600);
        MinechemFuelHandler.addFuel(new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.memethacrylate.id()), 5700);

    }

}
