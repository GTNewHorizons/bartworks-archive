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

package com.github.bartimaeusnek.bartworks.common.tileentities.tiered;

import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.GT_ModHandler;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Loader;

public class GT_MetaTileEntity_CompressedSteamHatch extends GT_MetaTileEntity_Hatch_Input {
    public GT_MetaTileEntity_CompressedSteamHatch(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 0);
        this.mDescriptionArray[1] = "Capacity: 100000000L";

    }

    public GT_MetaTileEntity_CompressedSteamHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public int getCapacity() {
        return 100000000;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        if( GT_ModHandler.isWater(aFluid)) return true;
        else
            if (GT_ModHandler.isSteam(aFluid)) return true;
            else
                if(GT_Utility.areFluidsEqual(aFluid,FluidRegistry.getFluidStack("ic2superheatedsteam",1)))
                    return true;
                else
            if (Loader.isModLoaded("GoodGenerator")) {
                return GT_Utility.areFluidsEqual(aFluid,FluidRegistry.getFluidStack("supercriticalsteam", 1));
            }
            else
                return false;

    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_CompressedSteamHatch(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
    }
}

