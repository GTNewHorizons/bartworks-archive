package com.github.bartimaeusnek.bartworks.common.loaders.recipes;

import gregtech.api.enums.TierEU;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.github.bartimaeusnek.bartworks.common.loaders.BioItemList;
import com.github.bartimaeusnek.bartworks.util.BW_Util;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Utility;

import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

public class LaserEngraver implements Runnable {

    @Override
    public void run() {

        GT_Values.RA.stdBuilder()
            .itemInputs(
                new ItemStack(Items.emerald),
                GT_Utility.getIntegratedCircuit(17)
            )
            .noItemOutputs()
            .noFluidInputs()
            .noFluidOutputs()
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(sLaserEngraverRecipes);


    }
}
