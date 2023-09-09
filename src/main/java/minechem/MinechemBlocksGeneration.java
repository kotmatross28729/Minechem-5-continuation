package minechem;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.block.BlockUraniumOre;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.tileentity.blueprintprojector.BlueprintProjectorBlock;
import minechem.tileentity.blueprintprojector.BlueprintProjectorTileEntity;
import minechem.tileentity.decomposer.DecomposerBlock;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.leadedchest.LeadedChestBlock;
import minechem.tileentity.leadedchest.LeadedChestTileEntity;
import minechem.tileentity.microscope.MicroscopeBlock;
import minechem.tileentity.microscope.MicroscopeTileEntity;
import minechem.tileentity.multiblock.fission.FissionTileEntity;
import minechem.tileentity.multiblock.fusion.FusionBlock;
import minechem.tileentity.multiblock.fusion.FusionItemBlock;
import minechem.tileentity.multiblock.fusion.FusionTileEntity;
import minechem.tileentity.multiblock.ghostblock.GhostBlock;
import minechem.tileentity.multiblock.ghostblock.GhostBlockItem;
import minechem.tileentity.multiblock.ghostblock.GhostBlockTileEntity;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.tileentity.synthesis.SynthesisBlock;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MinechemBlocksGeneration
{
    public static Block decomposer;
    public static Block microscope;
    public static Block synthesis;
    public static Block ghostBlock;
    public static Block blueprintProjector;
    public static Block fusion;
    public static Block printer;
    public static Block leadChest;

    public static Block uranium;
    public static Material materialGhost = new MaterialTransparent(MapColor.airColor);

    public static void registerBlocks()
    {
        // Decomposer
        decomposer = new DecomposerBlock();
        GameRegistry.registerBlock(decomposer, decomposer.getUnlocalizedName());
        GameRegistry.registerTileEntity(DecomposerTileEntity.class, decomposer.getUnlocalizedName());

        // Microscope.
        microscope = new MicroscopeBlock();
        GameRegistry.registerBlock(microscope, microscope.getUnlocalizedName());
        GameRegistry.registerTileEntity(MicroscopeTileEntity.class, microscope.getUnlocalizedName());

        // Chemical Synthesis Machine.
        synthesis = new SynthesisBlock();
        GameRegistry.registerBlock(synthesis, synthesis.getUnlocalizedName());
        GameRegistry.registerTileEntity(SynthesisTileEntity.class, synthesis.getUnlocalizedName());

        // Fusion Reactor.
        fusion = new FusionBlock();
        GameRegistry.registerBlock(fusion, FusionItemBlock.class, fusion.getUnlocalizedName());
        GameRegistry.registerTileEntity(FusionTileEntity.class, fusion.getUnlocalizedName());

        // Ghost Block.
        ghostBlock = new GhostBlock();
        GameRegistry.registerBlock(ghostBlock, GhostBlockItem.class, ghostBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(GhostBlockTileEntity.class, ghostBlock.getUnlocalizedName());

        // Blueprint Projector.
        blueprintProjector = new BlueprintProjectorBlock();
        GameRegistry.registerBlock(blueprintProjector, blueprintProjector.getUnlocalizedName());
        GameRegistry.registerTileEntity(BlueprintProjectorTileEntity.class, blueprintProjector.getUnlocalizedName());

        // Uranium Ore (World Gen).
        uranium = new BlockUraniumOre();
        GameRegistry.registerBlock(uranium, uranium.getUnlocalizedName());
        OreDictionary.registerOre("oreUranium", new ItemStack(uranium));

        // Leaded Chest (for storing radioactive isotopes).
        leadChest = new LeadedChestBlock();
        GameRegistry.registerBlock(leadChest, leadChest.getUnlocalizedName());
        GameRegistry.registerTileEntity(LeadedChestTileEntity.class, leadChest.getUnlocalizedName());

        // Fission Reactor.
        GameRegistry.registerTileEntity(FissionTileEntity.class, "fissionReactor");

        // Tile Entity Proxy.
        GameRegistry.registerTileEntity(TileEntityProxy.class, "minchem.tileEntityProxy");

        GameRegistry.registerTileEntity(RadiationFluidTileEntity.class, "minechem.tileEntityRadiationFluid");
    }
}
