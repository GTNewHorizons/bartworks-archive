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

import com.github.bartimaeusnek.bartworks.API.LoaderReference;
import com.github.bartimaeusnek.bartworks.common.configs.ConfigHandler;
import com.github.bartimaeusnek.bartworks.util.BW_Util;
import com.github.bartimaeusnek.bartworks.util.MegaUtils;
import com.github.bartimaeusnek.bartworks.util.Pair;
import com.github.bartimaeusnek.crossmod.tectech.TecTechEnabledMulti;
import com.github.bartimaeusnek.crossmod.tectech.helper.TecTechUtils;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import cpw.mods.fml.common.Optional;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static com.github.bartimaeusnek.bartworks.util.RecipeFinderForParallel.getMultiOutput;
import static com.github.bartimaeusnek.bartworks.util.RecipeFinderForParallel.handleParallelRecipe;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.util.GT_StructureUtility.*;

@Optional.Interface(iface = "com.github.bartimaeusnek.crossmod.tectech.TecTechEnabledMulti", modid = "tectech", striprefs = true)
public class GT_TileEntity_MegaVacuumFreezer extends GT_MetaTileEntity_EnhancedMultiBlockBase<GT_TileEntity_MegaVacuumFreezer> implements TecTechEnabledMulti {

    public GT_TileEntity_MegaVacuumFreezer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_TileEntity_MegaVacuumFreezer(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_MegaVacuumFreezer(this.mName);
    }

    @SuppressWarnings("rawtypes")
    public ArrayList TTTunnels = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    public ArrayList TTMultiAmp = new ArrayList<>();
    private int mCasing = 0;

    private static final int CASING_INDEX = 17;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<GT_TileEntity_MegaVacuumFreezer> STRUCTURE_DEFINITION = StructureDefinition.<GT_TileEntity_MegaVacuumFreezer>builder()
        .addShape(STRUCTURE_PIECE_MAIN, transpose(new String[][]{
            {"ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccc~ccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "c-------------c", "ccccccccccccccc"},
            {"ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc", "ccccccccccccccc"}
        }))
        .addElement('c', ofChain(
            ofHatchAdder(GT_TileEntity_MegaVacuumFreezer::addEnergyInputToMachineList, CASING_INDEX, 1),
            ofHatchAdder(GT_TileEntity_MegaVacuumFreezer::addMaintenanceToMachineList, CASING_INDEX, 1),
            ofHatchAdder(GT_TileEntity_MegaVacuumFreezer::addOutputToMachineList, CASING_INDEX, 1),
            ofHatchAdder(GT_TileEntity_MegaVacuumFreezer::addInputToMachineList, CASING_INDEX, 1),
            onElementPass(x -> x.mCasing++, ofBlock(GregTech_API.sBlockCasings2, 1))
        ))
        .build();


    @Override
    public IStructureDefinition<GT_TileEntity_MegaVacuumFreezer> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void construct(ItemStack aStack, boolean aHintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, aStack, aHintsOnly, 7, 7, 0);
    }

    @Override
    public boolean addEnergyInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (LoaderReference.tectech)
            return TecTechUtils.addEnergyInputToMachineList(this, aTileEntity, aBaseCasingIndex);
        return super.addEnergyInputToMachineList(aTileEntity, aBaseCasingIndex);
    }

    @Override
    public boolean drainEnergyInput(long aEU) {
        if (LoaderReference.tectech)
            return TecTechUtils.drainEnergyMEBFTecTech(this, aEU);
        return MegaUtils.drainEnergyMegaVanilla(this, aEU);
    }

    @Override
    public long getMaxInputVoltage() {
        if (LoaderReference.tectech)
            return TecTechUtils.getMaxInputVoltage(this);
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

        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sVacuumRecipes.findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tInputFluids, tInputs);
        boolean found_Recipe = false;
        int processed = 0;

        if (tRecipe != null) {
            found_Recipe = true;
            long tMaxPara = Math.min(ConfigHandler.megaMachinesMax, nominalV / tRecipe.mEUt);
            int tCurrentPara = handleParallelRecipe(tRecipe, tInputFluids, tInputs, (int) tMaxPara);
            if (tCurrentPara <= 0) return false;
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
                BW_Util.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else
                BW_Util.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
            //In case recipe is too OP for that machine
            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1)
                return false;
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



    // -------------- TEC TECH COMPAT ----------------

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        if (LoaderReference.tectech) {
            this.getTecTechEnergyMultis().clear();
            this.getTecTechEnergyTunnels().clear();
        }
        this.mCasing = 0;
        if(!checkPiece(STRUCTURE_PIECE_MAIN, 7, 7, 0))
            return false;
        return
            this.mMaintenanceHatches.size() == 1 &&
                (LoaderReference.tectech ?
                    (!this.getTecTechEnergyMultis().isEmpty() || !this.getTecTechEnergyTunnels().isEmpty() || !this.mEnergyHatches.isEmpty()) :
                    !this.mEnergyHatches.isEmpty()) &&
                this.mCasing >= 900;
    }


    @Override
    public String[] getInfoData() {
        return LoaderReference.tectech ? this.getInfoDataArray(this) : super.getInfoData();
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Vacuum Freezer").
            addInfo("Controller Block for the Mega Vacuum Freezer").
            addInfo("Cools hot ingots and cells").
            addSeparator().
            beginStructureBlock(15, 15, 15, true).
            addController("Front center").
            addCasingInfo("Frost Proof Machine Casing", 900).
            addEnergyHatch("Any casing", 1).
            addMaintenanceHatch("Any casing", 1).
            addInputHatch("Any casing", 1).
            addOutputHatch("Any casing", 1).
            addInputBus("Any casing", 1).
            addOutputBus("Any casing", 1).
            toolTipFinisher("Bartworks");
        return tt;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack itemStack) {
        return 10000;
    }

    @Override
    public int getDamageToComponent(ItemStack itemStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack itemStack) {
        return false;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        ITexture[] rTexture;
        if (aSide == aFacing) {
            if (aActive) {
                rTexture = new ITexture[]{Textures.BlockIcons.casingTexturePages[0][17], TextureFactory.builder().addIcon(new IIconContainer[]{Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE}).extFacing().build(), TextureFactory.builder().addIcon(new IIconContainer[]{Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE_GLOW}).extFacing().glow().build()};
            } else {
                rTexture = new ITexture[]{Textures.BlockIcons.casingTexturePages[0][17], TextureFactory.builder().addIcon(new IIconContainer[]{Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER}).extFacing().build(), TextureFactory.builder().addIcon(new IIconContainer[]{Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_GLOW}).extFacing().glow().build()};
            }
        } else {
            rTexture = new ITexture[]{Textures.BlockIcons.casingTexturePages[0][17]};
        }

        return rTexture;
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
