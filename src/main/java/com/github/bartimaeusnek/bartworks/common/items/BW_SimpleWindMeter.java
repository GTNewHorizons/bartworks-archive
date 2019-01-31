/*
 * Copyright (c) 2019 bartimaeusnek
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

package com.github.bartimaeusnek.bartworks.common.items;

import com.github.bartimaeusnek.bartworks.MainMod;
import com.github.bartimaeusnek.bartworks.util.ChatColorHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.WorldData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public class BW_SimpleWindMeter extends Item {

    public BW_SimpleWindMeter() {
        this.maxStackSize = 1;
        this.setMaxDamage(15);
        this.setCreativeTab(MainMod.BWT);
        this.hasSubtypes = false;
        this.setUnlocalizedName("BW_SimpleWindMeter");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(MainMod.MOD_ID + ":BW_SimpleWindMeter");
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean p_77624_4_) {
        super.addInformation(itemStack, entityPlayer, list, p_77624_4_);
        list.add("A simple Windmeter to choose a place for the Windmill.");
        list.add("Uses left: " + (this.getMaxDamage() - this.getDamage(itemStack)) + "/" + this.getMaxDamage());
        list.add("Added by" + ChatColorHelper.DARKGREEN + " BartWorks");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (entityPlayer.worldObj.isRemote || world == null || WorldData.get(world) == null || WorldData.get(world).windSim == null)
            return itemStack;

        float windStrength = (float) WorldData.get(world).windSim.getWindAt(entityPlayer.posY);
        String windS = windStrength < 1f ? "non existant" : windStrength < 10f ? "pretty low" : windStrength < 20f ? "common" : windStrength < 30f ? "rather strong" : windStrength < 50f ? "very strong" : "too strong";
        entityPlayer.addChatMessage(new ChatComponentText("The wind here seems to be " + windS + "."));
        itemStack.damageItem(1, entityPlayer);
        return itemStack;
    }

}
