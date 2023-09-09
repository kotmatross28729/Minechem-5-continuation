package minechem;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import minechem.computercraft.MinechemCCItemsRegistration;
import minechem.fluid.FluidChemicalDispenser;
import minechem.fluid.MinechemBucketReceiver;
import minechem.fluid.reaction.ChemicalFluidReactionHandler;
import minechem.gui.GuiHandler;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.polytool.PolytoolEventHandler;
import minechem.item.polytool.types.PolytoolTypeIron;
import minechem.minetweaker.Chemicals;
import minechem.minetweaker.Decomposer;
import minechem.minetweaker.Fuels;
import minechem.minetweaker.Synthesiser;
import minechem.network.MessageHandler;
import minechem.potion.PharmacologyEffectRegistry;
import minechem.potion.PotionCoatingRecipe;
import minechem.potion.PotionCoatingSubscribe;
import minechem.potion.PotionEnchantmentCoated;
import minechem.potion.PotionInjector;
import minechem.potion.PotionSpikingRecipe;
import minechem.proxy.CommonProxy;
import minechem.reference.MetaData;
import minechem.reference.Reference;
import minechem.render.EffectsRenderer;
import minechem.tileentity.decomposer.DecomposerRecipeHandler;
import minechem.utils.LogHelper;
import minechem.utils.MinechemFuelHandler;
import minechem.utils.MinechemUtil;
import minechem.utils.Recipe;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION_FULL, useMetadata = false, guiFactory = "minechem.gui.GuiFactory", acceptedMinecraftVersions = "[1.7.10,)", dependencies = "required-after:Forge@[10.13.3.1360,)")
public class Minechem
{
    public static boolean isCoFHAAPILoaded;

    // Instancing
    @Instance(value = Reference.ID)
    public static Minechem INSTANCE;

    // Public extra data about our mod that Forge uses in the mods listing page for more information.
    @Mod.Metadata(Reference.ID)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "minechem.proxy.ClientProxy", serverSide = "minechem.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Register instance.
        INSTANCE = this;

        try
        {
            Class.forName("cofh.api.energy.IEnergyHandler");
            isCoFHAAPILoaded = true;
        }catch(Exception e)
        {
            isCoFHAAPILoaded = false;
        }

        // Load configuration.
        LogHelper.debug("Loading configuration...");
        Settings.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new Settings());

        LogHelper.debug("Registering Packets...");
        MessageHandler.init();

        LogHelper.debug("Setting up ModMetaData");
        metadata = MetaData.init(metadata);

        // Register items and blocks.
        LogHelper.debug("Registering Items...");
        MinechemItemsRegistration.init();

        LogHelper.debug("Registering Blocks...");
        MinechemBlocksGeneration.registerBlocks();

        LogHelper.debug("Registering Elements & Molecules...");
        ElementEnum.init();
        MoleculeEnum.init();

        LogHelper.debug("Registering Blueprints...");
        MinechemBlueprint.registerBlueprints();

        GameRegistry.registerFuelHandler(new MinechemFuelHandler());

        FMLInterModComms.sendMessage("OpenBlocks", "donateUrl", "http://jakimfett.com/patreon/");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.debug("Registering OreDict Compatability...");
        MinechemItemsRegistration.registerToOreDictionary();

        LogHelper.debug("Registering Chemical Effects...");
        MinecraftForge.EVENT_BUS.register(new PotionCoatingSubscribe());

        LogHelper.debug("Registering Polytool Event Handler...");
        MinecraftForge.EVENT_BUS.register(new PolytoolEventHandler());

        LogHelper.debug("Registering Proxy Hooks...");
        PROXY.registerHooks();

        LogHelper.debug("Activating Potion Injector...");
        PotionInjector.inject();

        LogHelper.debug("Matching Pharmacology Effects to Chemicals...");
        CraftingManager.getInstance().getRecipeList().add(new PotionCoatingRecipe());

        LogHelper.debug("Registering FoodSpiking Recipes...");
        CraftingManager.getInstance().getRecipeList().add(new PotionSpikingRecipe());

        LogHelper.debug("Registering Ore Generation...");
        GameRegistry.registerWorldGenerator(new MinechemGeneration(), 0);

        LogHelper.debug("Registering Fluid Containers...");
        MinechemItemsRegistration.registerFluidContainers();

        LogHelper.debug("Registering GUI and Container handlers...");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        LogHelper.debug("Register Tick Events for chemical effects tracking...");
        PROXY.registerTickHandlers();

        LogHelper.debug("Registering ClientProxy Rendering Hooks...");
        PROXY.registerRenderers();

        LogHelper.debug("Registering Fluid Reactions...");
        FluidChemicalDispenser.init();
        ChemicalFluidReactionHandler.initReaction();

        LogHelper.debug("Registering Fuel Values...");
        MinechemItemsRegistration.registerFuelValues();

        if (Loader.isModLoaded("MineTweaker3"))
        {
            LogHelper.debug("Loading MineTweaker Classes...");
            MineTweakerAPI.registerClass(Chemicals.class);
            MineTweakerAPI.registerClass(Decomposer.class);
            MineTweakerAPI.registerClass(Synthesiser.class);
            MineTweakerAPI.registerClass(Fuels.class);
        }

        if (Loader.isModLoaded("ComputerCraft"))
        {
            MinechemCCItemsRegistration.init();
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinechemUtil.populateBlacklists();

        LogHelper.debug("Registering Recipes...");
        MinechemRecipes.getInstance().RegisterRecipes();
        MinechemRecipes.getInstance().registerFluidRecipes();
        MinechemBucketHandler.getInstance().registerBucketRecipes();

        LogHelper.debug("Adding blueprints to dungeon loot...");
        MinechemItemsRegistration.addDungeonLoot();

        LogHelper.debug("Adding effects to molecules...");
        PharmacologyEffectRegistry.init();

        LogHelper.debug("Activating Chemical Effect Layering (Coatings)...");
        PotionEnchantmentCoated.registerCoatings();

        LogHelper.debug("Registering Mod Ores for PolyTool...");
        PolytoolTypeIron.getOres();

        LogHelper.debug("Overriding bucket dispenser...");
        MinechemBucketReceiver.init();

        LogHelper.info("Minechem has loaded");
    }

    @SubscribeEvent
    public void onPreRender(RenderGameOverlayEvent.Pre e)
    {
        EffectsRenderer.renderEffects();
    }

    @EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        LogHelper.debug("Registering Mod Recipes...");
        MinechemRecipes.getInstance().RegisterModRecipes();

        Long start = System.currentTimeMillis();
        LogHelper.info("Registering other Mod Recipes...");
        MinechemRecipes.getInstance().registerOreDictOres();
        Recipe.init();
        DecomposerRecipeHandler.recursiveRecipes();
        LogHelper.info((System.currentTimeMillis() - start) + "ms spent registering Recipes");
    }
}
