/*
 * Copyright (c) 2018-2020 bartimaeusnek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.bartimaeusnek.bartworks.common.tileentities.multis.mega;

import com.github.bartimaeusnek.bartworks.API.BorosilicateGlass;
import com.github.bartimaeusnek.bartworks.API.LoaderReference;
import com.github.bartimaeusnek.bartworks.common.configs.ConfigHandler;
import com.github.bartimaeusnek.bartworks.util.BW_Tooltip_Reference;
import com.github.bartimaeusnek.bartworks.util.BW_Util;
import com.github.bartimaeusnek.bartworks.util.MegaUtils;
import com.github.bartimaeusnek.bartworks.util.Pair;
import com.github.bartimaeusnek.crossmod.tectech.TecTechEnabledMulti;
import com.github.bartimaeusnek.crossmod.tectech.helper.TecTechUtils;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import cpw.mods.fml.common.Optional;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeChemicalReactor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static com.github.bartimaeusnek.bartworks.util.RecipeFinderForParallel.getMultiOutput;
import static com.github.bartimaeusnek.bartworks.util.RecipeFinderForParallel.handleParallelRecipe;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdderOptional;

@Optional.Interface(iface = "com.github.bartimaeusnek.crossmod.tectech.TecTechEnabledMulti", modid = "tectech", striprefs = true)
public class GT_TileEntity_MegaChemicalReactor extends GT_MetaTileEntity_LargeChemicalReactor implements TecTechEnabledMulti {
    private final ArrayList<?> TTTunnels = new ArrayList<>();
    private final ArrayList<?> TTMultiAmp = new ArrayList<>();

    private byte glasTier;

    public GT_TileEntity_MegaChemicalReactor(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_TileEntity_MegaChemicalReactor(String aName) {
        super(aName);
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Chemical Reactor")
                .addInfo("Controller block for the Chemical Reactor")
                .addInfo("What molecule do you want to synthesize")
                .addInfo("Or you want to replace something in this molecule")
                .addInfo("The structure is too complex!")
                .addInfo("Follow the Structure Lib hologram projector to build the main structure.")
                .addSeparator()
                .beginStructureBlock(5, 5,  9,  false)
                .addController("Front center")
                .addStructureInfo("The glass tier limits the Energy Input tier")
                .addEnergyHatch("Hint block ", 3)
                .addMaintenanceHatch("Hint block ",2)
                .addInputHatch("Hint block ",1)
                .addInputBus("Hint block ",1)
                .addOutputBus("Hint block ",1)
                .addOutputHatch("Hint block ",1)
                .toolTipFinisher(BW_Tooltip_Reference.ADDED_BY_BARTIMAEUSNEK_VIA_BARTWORKS.get());
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_MegaChemicalReactor(this.mName);
    }

    @Override
    public boolean addEnergyInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (LoaderReference.tectech) {
            return TecTechUtils.addEnergyInputToMachineList(this, aTileEntity, aBaseCasingIndex);
        }
        return super.addEnergyInputToMachineList(aTileEntity, aBaseCasingIndex);
    }

    @Override
    public boolean drainEnergyInput(long aEU) {
        if (LoaderReference.tectech) {
            return TecTechUtils.drainEnergyMEBFTecTech(this, aEU);
        }
        return MegaUtils.drainEnergyMegaVanilla(this, aEU);
    }

    @Override
    public long getMaxInputVoltage() {
        if (LoaderReference.tectech) {
            return TecTechUtils.getMaxInputVoltage(this);
        }
        return super.getMaxInputVoltage();
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        ItemStack[] tInputs = this.getStoredInputs().toArray(new ItemStack[0]);
        FluidStack[] tInputFluids = this.getStoredFluids().toArray(new FluidStack[0]);
        ArrayList<ItemStack> outputItems = new ArrayList<>();
        ArrayList<FluidStack> outputFluids = new ArrayList<>();

        long nominalV = LoaderReference.tectech ? TecTechUtils.getnominalVoltageTT(this) : BW_Util.getnominalVoltage(this);

        byte tTier = (byte) Math.max(1, Math.min(GT_Utility.getTier(nominalV), V.length - 1));

        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sMultiblockChemicalRecipes.findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tInputFluids, tInputs);
        boolean found_Recipe = false;
        int processed = 0;

        if (tRecipe != null) {
            found_Recipe = true;
            long tMaxPara = Math.min(ConfigHandler.megaMachinesMax, nominalV / tRecipe.mEUt);
            int tCurrentPara = handleParallelRecipe(tRecipe, tInputFluids, tInputs, (int) tMaxPara);
            if (tCurrentPara <= 0) {
                return false;
            }
            processed = tCurrentPara;
            Pair<ArrayList<FluidStack>, ArrayList<ItemStack>> Outputs = getMultiOutput(tRecipe, tCurrentPara);
            outputFluids = Outputs.getKey();
            outputItems = Outputs.getValue();
        }

        if (found_Recipe) {
            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * processed;
            if (actualEUT > Integer.MAX_VALUE) {
                byte divider = 0;
                while (actualEUT > Integer.MAX_VALUE) {
                    actualEUT = actualEUT / 2;
                    divider++;
                }
                calculatePerfectOverclockedNessMulti((int) actualEUT, tRecipe.mDuration * (divider * 2), 1, nominalV);
            } else {
                calculatePerfectOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV);
            }
            //In case recipe is too OP for that machine
            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) {
                return false;
            }
            if (this.mEUt > 0) {
                this.mEUt = (-this.mEUt);
            }
            this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
            this.mOutputItems = new ItemStack[outputItems.size()];
            this.mOutputItems = outputItems.toArray(this.mOutputItems);
            this.mOutputFluids = new FluidStack[outputFluids.size()];
            this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);
            this.updateSlots();
            return true;
        }
        return false;
    }

    @Override
    public void construct(ItemStack aStack, boolean aHintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN,aStack,aHintsOnly,2,2,0);
    }

    // -------------- TEC TECH COMPAT ----------------

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        glasTier = 0;
        if (LoaderReference.tectech) {
            this.getTecTechEnergyMultis().clear();
            this.getTecTechEnergyTunnels().clear();
        }

        if(!checkPiece(STRUCTURE_PIECE_MAIN,2,2,0))
            return false;

        if(mMaintenanceHatches.size() != 1)
            return false;

        if (this.glasTier < 8 && !this.mEnergyHatches.isEmpty())
            for (GT_MetaTileEntity_Hatch_Energy hatchEnergy : this.mEnergyHatches)
                if (this.glasTier < hatchEnergy.mTier)
                    return false;


        return true;
    }

    private static final int CASING_INDEX = 176;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<GT_TileEntity_MegaChemicalReactor> STRUCTURE_DEFINITION = StructureDefinition.<GT_TileEntity_MegaChemicalReactor>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(new String[][]{
                    {"ttttt", "dptpd", "dptpd", "dptpd", "dptpd", "dptpd", "dptpd", "dptpd","ttttt"},
                    {"tgggt", " ggg ", " ggg ", " ggg ", " ggg ", " ggg ", " ggg ", " ggg ", "teeet"},
                    {"tg~gt", " gcg ", " gcg ", " gcg ", " gcg ", " gcg ", " gcg ", " gcg ", "teret"},
                    {"tgggt", " ggg ", " ggg ", " ggg ", " ggg ", " ggg ", " ggg ", " ggg ", "teeet"},
                    {"ttttt", "dptpd", "dptpd", "dptpd", "dptpd", "dptpd", "dptpd", "dptpd","ttttt"},
            }))
            .addElement('p', ofBlock(GregTech_API.sBlockCasings8, 1))
            .addElement('t', ofBlock(GregTech_API.sBlockCasings8, 0))
            .addElement('d', ofChain(
                    ofHatchAdder(GT_TileEntity_MegaChemicalReactor::addInputToMachineList, CASING_INDEX, 1),
                    ofHatchAdder(GT_TileEntity_MegaChemicalReactor::addOutputToMachineList, CASING_INDEX, 1),
                    ofBlock(GregTech_API.sBlockCasings8, 0)
            ))
            .addElement('r', ofHatchAdder(GT_TileEntity_MegaChemicalReactor::addMaintenanceToMachineList, CASING_INDEX, 2))
            .addElement('e', ofHatchAdderOptional(GT_TileEntity_MegaChemicalReactor::addEnergyInputToMachineList, CASING_INDEX, 3, GregTech_API.sBlockCasings8, 0))
            .addElement('c', ofBlock(GregTech_API.sBlockCasings4, 7))
            .addElement('g', BorosilicateGlass.ofBoroGlass((byte) 0, (byte) 1, Byte.MAX_VALUE, (te, t) -> te.glasTier = t, te -> te.glasTier))
            .build();


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public IStructureDefinition<GT_MetaTileEntity_LargeChemicalReactor> getStructureDefinition() {
        return (IStructureDefinition) STRUCTURE_DEFINITION;
    }

    @Override
    public String[] getInfoData() {
        return LoaderReference.tectech ? this.getInfoDataArray(this) : super.getInfoData();
    }

    @Override
    @Optional.Method(modid = "tectech")
    public List<GT_MetaTileEntity_Hatch_Energy> getVanillaEnergyHatches() {
        return this.mEnergyHatches;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Optional.Method(modid = "tectech")
    public List getTecTechEnergyTunnels() {
        return TTTunnels;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Optional.Method(modid = "tectech")
    public List getTecTechEnergyMultis() {
        return TTMultiAmp;
    }
}
