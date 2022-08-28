package com.github.bartimaeusnek.bartworks.neiHandler;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Recipe;
import gregtech.nei.GT_NEI_DefaultHandler;

public class BW_NEI_HTGRHandler extends GT_NEI_DefaultHandler {
    public BW_NEI_HTGRHandler(GT_Recipe.GT_Recipe_Map aRecipeMap) {
        super(aRecipeMap);
        if(!NEI_BW_Config.sIsAdded)
        {
            FMLInterModComms.sendRuntimeMessage(
                GT_Values.GT,
                "NEIPlugins",
                "register-crafting-handler",
                "bartworks@" + this.getRecipeName() + "@" + this.getOverlayIdentifier());
            GuiCraftingRecipe.craftinghandlers.add(this);
            GuiUsageRecipe.usagehandlers.add(this);
        }
    }
}
