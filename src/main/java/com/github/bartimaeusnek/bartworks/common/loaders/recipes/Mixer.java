package com.github.bartimaeusnek.bartworks.common.loaders.recipes;

import com.github.bartimaeusnek.bartworks.common.tileentities.multis.GT_TileEntity_THTR;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class Mixer implements Runnable{
    @Override
    public void run() {
        GT_Values.RA.addMixerRecipe(
            WerkstoffLoader.Thorium232.get(OrePrefixes.dust, 10),
            Materials.Uranium235.getDust(1),
            GT_Utility.getIntegratedCircuit(2),
            null,
            null,
            null,
            new ItemStack(GT_TileEntity_THTR.THTRMaterials.aTHTR_Materials),
            400,
            30);
    }
}
