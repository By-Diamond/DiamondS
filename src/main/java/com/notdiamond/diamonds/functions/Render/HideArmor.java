package com.notdiamond.diamonds.functions.Render;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HideArmor {
//    public static boolean Reload = false;

    @SubscribeEvent
    public void HideArmor(EntityEvent event) {
        if (Functions.GetStatus("HideArmor")) {
            if (event.entity instanceof EntityPlayer) {
                if(event.entity.getName().contentEquals(Minecraft.getMinecraft().thePlayer.getName())){
                    return;
                }
                event.entity.setCurrentItemOrArmor(1, null);
                event.entity.setCurrentItemOrArmor(2, null);
                event.entity.setCurrentItemOrArmor(3, null);
                event.entity.setCurrentItemOrArmor(4, null);
//                event.entity.setInvisible(false);
            }
        }
    }
    @SubscribeEvent
    public void AutoOFF(WorldEvent.Unload event) {
        if(Functions.GetStatus("HideArmor")){
            Functions.SetStatus("HideArmor",false);
            DiamondS.SendMessage("§c检测到更换世界，已自动关闭 §lHideArmor");
//            HideArmor.Reload = true;
        }
    }
//    @SubscribeEvent
//    public void AutoON(RenderWorldLastEvent event) {
//        if(!Functions.GetStatus("HideArmor") && HideArmor.Reload){
//            Functions.SetStatus("HideArmor",true);
//            HideArmor.Reload = false;
//        }
//    }
}
