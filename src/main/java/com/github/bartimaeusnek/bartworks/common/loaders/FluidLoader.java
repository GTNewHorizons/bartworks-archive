/*
 * Copyright (c) 2018-2020 bartimaeusnek Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions: The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.github.bartimaeusnek.bartworks.common.loaders;

import static gregtech.api.enums.Mods.Gendustry;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import java.awt.*;
import java.util.Arrays;

import gregtech.api.enums.TierEU;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.github.bartimaeusnek.bartworks.API.SideReference;
import com.github.bartimaeusnek.bartworks.client.renderer.RendererGlasBlock;
import com.github.bartimaeusnek.bartworks.client.renderer.RendererSwitchingColorFluid;
import com.github.bartimaeusnek.bartworks.common.blocks.BioFluidBlock;
import com.github.bartimaeusnek.bartworks.common.tileentities.classic.BWTileEntityDimIDBridge;
import com.github.bartimaeusnek.bartworks.util.BioCulture;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.objects.GT_Fluid;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_MetaGenerated_Item_98;

public class FluidLoader {

    public static IIcon autogenIIcon;
    public static Fluid ff;
    public static int renderID;
    public static Block bioFluidBlock;
    public static Fluid[] BioLabFluidMaterials;
    public static ItemStack[] BioLabFluidCells;

    // OilProcessing chain
    public static Fluid fulvicAcid, heatedfulvicAcid, Kerogen;

    public static void run() {
        FluidLoader.renderID = RenderingRegistry.getNextAvailableRenderId();
        short[] rgb = new short[3];
        Arrays.fill(rgb, (short) 255);
        FluidLoader.ff = new GT_Fluid("BWfakeFluid", "molten.autogenerated", rgb);
        FluidLoader.fulvicAcid = FluidLoader.createAndRegisterFluid("Fulvic Acid", new Color(20, 20, 20));
        FluidLoader.heatedfulvicAcid = FluidLoader
                .createAndRegisterFluid("Heated Fulvic Acid", new Color(40, 20, 20), 720);
        FluidLoader.Kerogen = FluidLoader.createAndRegisterFluid("Kerogen", new Color(85, 85, 85));
        FluidLoader.BioLabFluidMaterials = new Fluid[] {
                new GT_Fluid("FluorecentdDNA", "molten.autogenerated", new short[] { 125, 50, 170, 0 }),
                new GT_Fluid("EnzymesSollution", "molten.autogenerated", new short[] { 240, 200, 125, 0 }),
                new GT_Fluid("Penicillin", "molten.autogenerated", new short[] { 255, 255, 255, 0 }),
                new GT_Fluid("Polymerase", "molten.autogenerated", new short[] { 110, 180, 110, 0 }), };

        FluidLoader.BioLabFluidCells = new ItemStack[FluidLoader.BioLabFluidMaterials.length];
        for (int i = 0; i < FluidLoader.BioLabFluidMaterials.length; i++) {
            FluidRegistry.registerFluid(FluidLoader.BioLabFluidMaterials[i]);
        }

        GT_MetaGenerated_Item_98.FluidCell[] fluidCells = new GT_MetaGenerated_Item_98.FluidCell[] {
                GT_MetaGenerated_Item_98.FluidCell.FLUORESCENT_DNA, GT_MetaGenerated_Item_98.FluidCell.ENZYME_SOLUTION,
                GT_MetaGenerated_Item_98.FluidCell.PENICILLIN, GT_MetaGenerated_Item_98.FluidCell.POLYMERASE, };
        for (int i = 0; i < fluidCells.length; i++) {
            FluidLoader.BioLabFluidCells[i] = fluidCells[i].get();
        }

        FluidStack dnaFluid = Gendustry.isModLoaded() ? FluidRegistry.getFluidStack("liquiddna", 100)
                : Materials.Biomass.getFluid(100L);
        for (BioCulture B : BioCulture.BIO_CULTURE_ARRAY_LIST) {
            if (B.isBreedable()) {
                B.setFluid(
                        new GT_Fluid(
                                B.getName().replaceAll(" ", "").toLowerCase() + "fluid",
                                "molten.autogenerated",
                                new short[] { (short) B.getColor().getRed(), (short) B.getColor().getBlue(),
                                        (short) B.getColor().getGreen() }));
                FluidRegistry.registerFluid(B.getFluid());
                GT_LanguageManager
                        .addStringLocalization(B.getFluid().getUnlocalizedName(), B.getLocalisedName() + " Fluid");

                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(10)).noItemOutputs()
                        .fluidInputs(new FluidStack(B.getFluid(), 1000)).fluidOutputs(dnaFluid).duration(25 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(sCentrifugeRecipes);

            }
        }

        FluidLoader.bioFluidBlock = new BioFluidBlock();
        GameRegistry.registerBlock(FluidLoader.bioFluidBlock, "coloredFluidBlock");
        GameRegistry.registerTileEntity(BWTileEntityDimIDBridge.class, "bwTEDimIDBridge");
        if (SideReference.Side.Client) {
            RenderingRegistry.registerBlockHandler(RendererSwitchingColorFluid.instance);
            RenderingRegistry.registerBlockHandler(RendererGlasBlock.instance);
        }
    }

    public static Fluid createAndRegisterFluid(String Name, Color color) {
        Fluid f = new GT_Fluid(
                Name,
                "molten.autogenerated",
                new short[] { (short) color.getRed(), (short) color.getGreen(), (short) color.getBlue(),
                        (short) color.getAlpha() });
        GT_LanguageManager.addStringLocalization(f.getUnlocalizedName(), Name);
        FluidRegistry.registerFluid(f);
        return f;
    }

    public static Fluid createAndRegisterFluid(String Name, Color color, int temperature) {
        Fluid f = new GT_Fluid(
                Name,
                "molten.autogenerated",
                new short[] { (short) color.getRed(), (short) color.getGreen(), (short) color.getBlue(),
                        (short) color.getAlpha() });
        GT_LanguageManager.addStringLocalization(f.getUnlocalizedName(), Name);
        f.setTemperature(temperature);
        FluidRegistry.registerFluid(f);
        return f;
    }
}
