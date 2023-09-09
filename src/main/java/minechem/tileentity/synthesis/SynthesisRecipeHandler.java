package minechem.tileentity.synthesis;

import java.util.ArrayList;
import java.util.Arrays;
import minechem.potion.PotionChemical;
import minechem.utils.Compare;
import minechem.utils.MapKey;
import minechem.utils.MinechemUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SynthesisRecipeHandler
{

    public static SynthesisRecipeHandler instance = new SynthesisRecipeHandler();

    public SynthesisRecipeHandler()
    {

    }

    public SynthesisRecipe getRecipeFromOutput(ItemStack output)
    {
        MapKey key = MapKey.getKey(output);
        if (key == null)
        {
            return null;
        }
        return SynthesisRecipe.recipes.get(key);
    }

    public SynthesisRecipe getRecipeFromInput(ItemStack[] input)
    {
        for (SynthesisRecipe recipe : SynthesisRecipe.recipes.values())
        {
            if (itemStacksMatchesRecipe(input, recipe))
            {
                return recipe;
            }
        }
        return null;
    }

    public boolean itemStacksMatchesRecipe(ItemStack[] stacks, SynthesisRecipe recipe)
    {
        return itemStacksMatchesRecipe(stacks, recipe, 1);
    }

    public boolean itemStacksMatchesRecipe(ItemStack[] stacks, SynthesisRecipe recipe, int factor)
    {
        if (recipe.isShaped())
        {
            return itemStacksMatchesShapedRecipe(stacks, recipe, factor);
        } else
        {
            return itemStacksMatchesShapelessRecipe(stacks, recipe, factor);
        }
    }

    private boolean itemStacksMatchesShapelessRecipe(ItemStack[] stacks, SynthesisRecipe recipe, int factor)
    {
        ArrayList<ItemStack> stacksList = new ArrayList<ItemStack>();
        ArrayList<ItemStack> shapelessRecipe = MinechemUtil.convertChemicalsIntoItemStacks(new ArrayList<PotionChemical>(Arrays.asList(recipe.getShapelessRecipe())));
        for (ItemStack itemstack : stacks)
        {
            if (itemstack != null)
            {
                stacksList.add(itemstack.copy());
            }
        }
        for (ItemStack itemstack : stacksList)
        {
            int ingredientSlot = getIngredientSlotThatMatchesStack(shapelessRecipe, itemstack, 1);
            if (ingredientSlot != -1)
            {
                shapelessRecipe.remove(ingredientSlot);
            } else
            {
                return false;
            }
        }
        return shapelessRecipe.size() == 0;
    }

    private boolean itemStacksMatchesShapedRecipe(ItemStack[] stacks, SynthesisRecipe recipe, int factor)
    {
        PotionChemical[] chemicals = recipe.getShapedRecipe();
        for (int i = 0; i < chemicals.length; i++)
        {
            if (stacks[i] == null && chemicals[i] == null)
            {
                continue;
            }
            if (stacks[i] == null || chemicals[i] == null)
            {
                return false;
            }
            if (!MinechemUtil.itemStackMatchesChemical(stacks[i], chemicals[i], factor) || stacks[i].stackSize != chemicals[i].amount)
            {
                return false;
            }
        }
        return true;
    }

    private int getIngredientSlotThatMatchesStack(ArrayList<ItemStack> ingredients, ItemStack itemstack, int factor)
    {
        for (int slot = 0; slot < ingredients.size(); slot++)
        {
            ItemStack ingredientStack = ingredients.get(slot);
            if (ingredientStack != null && Compare.stacksAreSameKind(itemstack, ingredientStack) && itemstack.stackSize == ingredientStack.stackSize)
            {
                return slot;
            }
        }
        return -1;
    }

    /**
     * Clears the crafting inventory.
     */
    public static boolean takeFromCraftingInventory(SynthesisRecipe recipe, final IInventory inv)
    {
        for (int slot = 0; slot < inv.getSizeInventory(); slot++)
        {
            inv.setInventorySlotContents(slot, null);
        }
        return true;
    }
}
