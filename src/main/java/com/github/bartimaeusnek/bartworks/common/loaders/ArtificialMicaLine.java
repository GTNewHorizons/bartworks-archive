package com.github.bartimaeusnek.bartworks.common.loaders;

import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sBlastRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMixerRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeConstants.COIL_HEAT;

import gregtech.api.enums.TierEU;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.github.bartimaeusnek.bartworks.system.material.BW_GT_MaterialReference;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;

public class ArtificialMicaLine {

    public static void runArtificialMicaRecipe() {
        // Mg + O = MgO
        GT_Values.RA.addChemicalRecipe(
                Materials.Magnesium.getDust(1),
                null,
                Materials.Oxygen.getGas(1000),
                null,
                Materials.Magnesia.getDust(2),
                40,
                8);
        // Si + 6HF = H2SiF6 + 4H
        GT_Values.RA.addChemicalRecipe(
                Materials.Silicon.getDust(1),
                Materials.Empty.getCells(4),
                Materials.HydrofluoricAcid.getFluid(6000),
                WerkstoffLoader.HexafluorosilicicAcid.getFluidOrGas(1000),
                Materials.Hydrogen.getCells(4),
                400,
                120);
        // K + Cl = KCl
        GT_Values.RA.addChemicalRecipe(
                Materials.Potassium.getDust(1),
                GT_Utility.getIntegratedCircuit(2),
                Materials.Chlorine.getGas(1000),
                null,
                Materials.RockSalt.getDust(2),
                20,
                8);
        // 2KCl + H2SiF6 = 2HCl + K2SiF6
        GT_Values.RA.stdBuilder()
            .itemInputs(Materials.RockSalt.getDust(4))
            .itemOutputs(WerkstoffLoader.Potassiumfluorosilicate.get(OrePrefixes.dust, 9))
            .fluidInputs(WerkstoffLoader.HexafluorosilicicAcid.getFluidOrGas(1000))
            .fluidOutputs(Materials.HydrochloricAcid.getGas(2000))
            .duration(1*SECONDS)
            .eut(8)
            .addTo(sMixerRecipes);

        // 2K + CO2 + O = K2CO3
        GT_Values.RA.addChemicalRecipe(
                Materials.Potassium.getDust(2),
                Materials.CarbonDioxide.getCells(1),
                Materials.Oxygen.getGas(1000),
                null,
                WerkstoffLoader.PotassiumCarbonate.get(OrePrefixes.dust, 6),
                Materials.Empty.getCells(1),
                40,
                8);
        // K2O + CO2 = K2CO3
        GT_Values.RA.addChemicalRecipe(
                Materials.Potash.getDust(3),
                Materials.CarbonDioxide.getCells(1),
                null,
                null,
                WerkstoffLoader.PotassiumCarbonate.get(OrePrefixes.dust, 6),
                Materials.Empty.getCells(1),
                40,
                8);
        // 55Quartz Dust + 20K2SiF6 + 12Al2O3 + 4K2CO3 = 91Raw Fluorophlogopite Dust
        GT_Values.RA.stdBuilder()
            .itemInputs(
                Materials.QuartzSand.getDust(55),
                WerkstoffLoader.Potassiumfluorosilicate.get(OrePrefixes.dust, 20),
                WerkstoffLoader.Alumina.get(OrePrefixes.dust, 12),
                WerkstoffLoader.PotassiumCarbonate.get(OrePrefixes.dust, 4),
                GT_Utility.getIntegratedCircuit(4)
            )
            .itemOutputs(
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 64),
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 27)
            )
            .noFluidInputs()
            .noFluidOutputs()
            .duration(20*SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(sMixerRecipes);

        // 55Quartzite/Nether Quartz Dust + 20K2SiF6 + 57Al2O3 + 4K2CO3 = 136Raw Fluorophlogopite Dust
        GT_Values.RA.stdBuilder()
            .itemInputs(
                Materials.Quartzite.getDust(55),
                WerkstoffLoader.Potassiumfluorosilicate.get(OrePrefixes.dust, 20),
                WerkstoffLoader.Alumina.get(OrePrefixes.dust, 57),
                WerkstoffLoader.PotassiumCarbonate.get(OrePrefixes.dust, 4),
                GT_Utility.getIntegratedCircuit(4)
            )
            .itemOutputs(
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 64),
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 64),
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 8)
            )
            .fluidInputs()
            .fluidOutputs()
            .duration(30*SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(sMixerRecipes);

        GT_Values.RA.stdBuilder()
            .itemInputs(
                Materials.NetherQuartz.getDust(55),
                WerkstoffLoader.Potassiumfluorosilicate.get(OrePrefixes.dust, 20),
                WerkstoffLoader.Alumina.get(OrePrefixes.dust, 57),
                WerkstoffLoader.PotassiumCarbonate.get(OrePrefixes.dust, 4),
                GT_Utility.getIntegratedCircuit(4)
            )
            .itemOutputs(
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 64),
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 64),
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 8)
            )
            .noFluidInputs()
            .noFluidOutputs()
            .duration(30*SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(sMixerRecipes);

        // 62Certus Quartz Dust + 10K2SiF6 + 12Al2O3 + 7K2CO3 = 91Raw Fluorophlogopite Dust
        GT_Values.RA.stdBuilder()
            .itemInputs(
                Materials.CertusQuartz.getDust(62),
                WerkstoffLoader.Potassiumfluorosilicate.get(OrePrefixes.dust, 10),
                WerkstoffLoader.Alumina.get(OrePrefixes.dust, 12),
                WerkstoffLoader.PotassiumCarbonate.get(OrePrefixes.dust, 7),
                GT_Utility.getIntegratedCircuit(4)
            )
            .itemOutputs(
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 64),
                WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 27)
            )
            .noFluidInputs()
            .noFluidOutputs()
            .duration(30*SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(sMixerRecipes);

        // MgO(s) = MgO(l)
        GT_Values.RA.addFluidExtractionRecipe(
                Materials.Magnesia.getDust(1),
                null,
                Materials.Magnesia.getMolten(144),
                0,
                20,
                120);
        // 27Raw Fluorophlogopite Dust + 720MgO(l) = 4608Fluorophlogopite(l)
        GT_Values.RA.stdBuilder()
            .itemInputs(WerkstoffLoader.RawFluorophlogopite.get(OrePrefixes.dust, 27))
            .itemOutputs(Materials.Glass.getDust(1))
            .fluidInputs(BW_GT_MaterialReference.Magnesia.getMolten(720))
            .fluidOutputs(WerkstoffLoader.HotFluorophlogopite.getFluidOrGas(4608))
            .duration(30*SECONDS)
            .eut(TierEU.RECIPE_HV)
            .metadata(COIL_HEAT, 1700)
            .addTo(sBlastRecipes);

        // 144Fluorophlogopite(l) = Fluorophlogopite
        GT_Recipe.GT_Recipe_Map.sVacuumRecipes.addRecipe(
                new GT_Recipe(
                        false,
                        new ItemStack[] { ItemList.Shape_Mold_Plate.get(0) },
                        new ItemStack[] { WerkstoffLoader.Fluorophlogopite.get(OrePrefixes.plate, 1) },
                        null,
                        null,
                        new FluidStack[] { WerkstoffLoader.HotFluorophlogopite.getFluidOrGas(144) },
                        null,
                        10,
                        120,
                        0));
        // Fluorophlogopite = 4Insulator Foil
        if (NewHorizonsCoreMod.isModLoaded()) {
            GT_Values.RA.addBenderRecipe(
                    WerkstoffLoader.Fluorophlogopite.get(OrePrefixes.plate, 1),
                    GT_Utility.getIntegratedCircuit(1),
                    GT_ModHandler.getModItem(NewHorizonsCoreMod.ID, "item.MicaInsulatorFoil", 4),
                    10,
                    600);
        }
    }
}
