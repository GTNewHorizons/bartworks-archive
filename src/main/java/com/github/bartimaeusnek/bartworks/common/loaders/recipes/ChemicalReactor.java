package com.github.bartimaeusnek.bartworks.common.loaders.recipes;

import gregtech.api.enums.TierEU;
import net.minecraftforge.fluids.FluidStack;

import com.github.bartimaeusnek.bartworks.common.loaders.FluidLoader;
import com.github.bartimaeusnek.bartworks.util.BW_Util;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Utility;

import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;
import static gregtech.api.util.GT_RecipeConstants.UniversalChemical;

public class ChemicalReactor implements Runnable {

    @Override
    public void run() {

        GT_Values.RA.stdBuilder()
            .itemInputs(
                GT_Utility.getIntegratedCircuit(10)
            )
            .noItemOutputs()
            .fluidInputs(
                new FluidStack(FluidLoader.heatedfulvicAcid, 1000)
            )
            .fluidOutputs(
                new FluidStack(FluidLoader.Kerogen, 1000)
            )
            .duration(3 * SECONDS + 15 * TICKS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

    }
}
