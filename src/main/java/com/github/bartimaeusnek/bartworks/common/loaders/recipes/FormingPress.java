package com.github.bartimaeusnek.bartworks.common.loaders.recipes;

import com.github.bartimaeusnek.bartworks.common.tileentities.multis.GT_TileEntity_THTR;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import net.minecraft.item.ItemStack;

public class FormingPress implements Runnable{
    @Override
    public void run() {
        GT_Values.RA.addFormingPressRecipe(
            new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials),
            Materials.Graphite.getDust(64),
            new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 1),
            40,
            30);
        GT_Values.RA.addFormingPressRecipe(
            new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 1),
            Materials.Silicon.getDust(64),
            new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 2),
            40,
            30);
        GT_Values.RA.addFormingPressRecipe(
            new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 2),
            Materials.Graphite.getDust(64),
            new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 3),
            40,
            30);
    }
}
