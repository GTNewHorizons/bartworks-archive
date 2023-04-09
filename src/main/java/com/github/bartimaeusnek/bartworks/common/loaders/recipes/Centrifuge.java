package com.github.bartimaeusnek.bartworks.common.loaders.recipes;

import com.github.bartimaeusnek.bartworks.common.tileentities.multis.GT_TileEntity_THTR;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;
import com.github.bartimaeusnek.bartworks.util.BW_Util;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static com.github.bartimaeusnek.bartworks.common.configs.ConfigHandler.newStuff;

public class Centrifuge implements Runnable{
    @Override
    public void run() {
        if (newStuff){
            GT_Values.RA.addCentrifugeRecipe(
                Materials.Thorium.getDust(1),
                GT_Values.NI,
                GT_Values.NF,
                GT_Values.NF,
                Materials.Thorium.getDust(1),
                Materials.Thorium.getDust(1),
                WerkstoffLoader.Thorium232.get(OrePrefixes.dust, 1),
                WerkstoffLoader.Thorium232.get(OrePrefixes.dust, 1),
                WerkstoffLoader.Thorium232.get(OrePrefixes.dust, 1),
                GT_Values.NI,
                new int[] { 800, 375, 22, 22, 5 },
                10000,
                BW_Util.getMachineVoltageFromTier(4));

            ItemStack[] pellets = new ItemStack[6];
            Arrays.fill(pellets, new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 64, 4));

            GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.addRecipe(
                false,
                new ItemStack[] { new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 3),
                    GT_Utility.getIntegratedCircuit(17) },
                pellets,
                null,
                null,
                null,
                null,
                48000,
                30,
                0);
            GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.addRecipe(
                false,
                new ItemStack[] { new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 5),
                    GT_Utility.getIntegratedCircuit(17) },
                new ItemStack[] { new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 64, 6) },
                null,
                null,
                null,
                null,
                48000,
                30,
                0);
            GT_Values.RA.addCentrifugeRecipe(
                new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials, 1, 6),
                GT_Values.NI,
                GT_Values.NF,
                GT_Values.NF,
                Materials.Lead.getDust(1),
                GT_Values.NI,
                GT_Values.NI,
                GT_Values.NI,
                GT_Values.NI,
                GT_Values.NI,
                new int[] { 300 },
                1200,
                30);

        }
    }
}
