package com.github.bartimaeusnek.bartworks.common.loaders.recipes;

import gregtech.api.enums.TierEU;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.github.bartimaeusnek.bartworks.common.loaders.BioItemList;
import com.github.bartimaeusnek.bartworks.util.BW_Util;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Utility;

public class LaserEngraver implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.addLaserEngraverRecipe(
                new ItemStack(Items.emerald),
                GT_Utility.getIntegratedCircuit(17),
                BioItemList.getPlasmidCell(null),
                100,
                (int) TierEU.RECIPE_LV);

    }
}
