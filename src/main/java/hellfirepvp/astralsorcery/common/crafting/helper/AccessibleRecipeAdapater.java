/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.crafting.helper;

import hellfirepvp.astralsorcery.common.crafting.IAccessibleRecipe;
import hellfirepvp.astralsorcery.common.crafting.ItemHandle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: AccessibleRecipeAdapater
 * Created by HellFirePvP
 * Date: 06.10.2016 / 14:26
 */
public class AccessibleRecipeAdapater implements IAccessibleRecipe {

    private final IRecipe parent;
    private final AbstractCacheableRecipe abstractRecipe;

    public AccessibleRecipeAdapater(IRecipe parent, AbstractCacheableRecipe abstractRecipe) {
        this.parent = parent;
        this.abstractRecipe = abstractRecipe;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public NonNullList<ItemStack> getExpectedStackForRender(int row, int column) {
        ItemHandle handle = abstractRecipe.getExpectedStack(row, column);
        if(handle == null) return NonNullList.create();
        return refactorSubItems(handle.getApplicableItemsForRender());
    }

    @Nullable
    public ItemHandle getExpectedStackHandle(int row, int column) {
        return abstractRecipe.getExpectedStack(row, column);
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public NonNullList<ItemStack> getExpectedStackForRender(ShapedRecipeSlot slot) {
        ItemHandle handle = abstractRecipe.getExpectedStack(slot);
        if(handle == null) return NonNullList.create();
        return refactorSubItems(handle.getApplicableItemsForRender());
    }

    @Nullable
    public ItemHandle getExpectedStackHandle(ShapedRecipeSlot slot) {
        return abstractRecipe.getExpectedStack(slot);
    }

    @SideOnly(Side.CLIENT)
    private NonNullList<ItemStack> refactorSubItems(NonNullList<ItemStack> applicableItems) {
        NonNullList<ItemStack> out = NonNullList.create();
        for (ItemStack oreDictIn : applicableItems) {
            if(oreDictIn.getItemDamage() == OreDictionary.WILDCARD_VALUE && !oreDictIn.isItemStackDamageable()) {
                oreDictIn.getItem().getSubItems(oreDictIn.getItem(), CreativeTabs.BUILDING_BLOCKS, out);
            } else {
                out.add(oreDictIn);
            }
        }
        return out;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return parent.matches(inv, worldIn);
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return parent.getCraftingResult(inv);
    }

    @Override
    public int getRecipeSize() {
        return parent.getRecipeSize();
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return parent.getRecipeOutput();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return parent.getRemainingItems(inv);
    }

}
