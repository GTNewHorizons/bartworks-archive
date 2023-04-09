package com.github.bartimaeusnek.bartworks.common.loaders.recipes;

import com.github.bartimaeusnek.bartworks.common.configs.ConfigHandler;
import com.github.bartimaeusnek.bartworks.common.loaders.ItemRegistry;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;
import com.github.bartimaeusnek.bartworks.util.BW_Util;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static com.github.bartimaeusnek.bartworks.common.configs.ConfigHandler.newStuff;

public class FluidSolidifier implements Runnable{
    @Override
    public void run() {
        FluidStack LuVMaterialFluid = ConfigHandler.newStuff ? WerkstoffLoader.LuVTierMaterial.getMolten(1152)
            : Materials.Chrome.getMolten(1152);

        GT_Values.RA.addFluidSolidifierRecipe(
            new ItemStack(Blocks.lapis_block),
            Materials.Iron.getMolten(1296L),
            new ItemStack(ItemRegistry.BW_BLOCKS[0], 1, 0),
            100,
            BW_Util.getMachineVoltageFromTier(3));

        if(newStuff){
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.Titanium.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 1),
                800,
                BW_Util.getMachineVoltageFromTier(3));
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.TungstenSteel.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 2),
                800,
                BW_Util.getMachineVoltageFromTier(4));

            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                LuVMaterialFluid,
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 3),
                800,
                BW_Util.getMachineVoltageFromTier(5));
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.Iridium.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 4),
                800,
                BW_Util.getMachineVoltageFromTier(6));
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.Osmium.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 5),
                800,
                BW_Util.getMachineVoltageFromTier(7));
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.Neutronium.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 13),
                800,
                BW_Util.getMachineVoltageFromTier(8));
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.CosmicNeutronium.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 14),
                800,
                BW_Util.getMachineVoltageFromTier(9));
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.Infinity.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 15),
                800,
                BW_Util.getMachineVoltageFromTier(10));
            GT_Values.RA.addFluidSolidifierRecipe(
                new ItemStack(ItemRegistry.bw_glasses[0], 1, 0),
                Materials.TranscendentMetal.getMolten(1152),
                new ItemStack(ItemRegistry.bw_glasses[1], 1, 0),
                800,
                BW_Util.getMachineVoltageFromTier(11));
        }
    }
}
