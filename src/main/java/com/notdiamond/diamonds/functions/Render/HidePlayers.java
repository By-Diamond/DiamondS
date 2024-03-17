package com.notdiamond.diamonds.functions.Render;

import com.notdiamond.diamonds.core.Function.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HidePlayers {
    public static boolean Reload = false;
    @SubscribeEvent
    public void HidePlayers(EntityEvent event) {
        if(Functions.GetStatus("HidePlayers")){
            if (event.entity instanceof EntityPlayer){
                if(event.entity.getName().contentEquals(Minecraft.getMinecraft().thePlayer.getName())){
                    return;
                }
                event.entity.setCurrentItemOrArmor(0,null);
                event.entity.setCurrentItemOrArmor(1,null);
                event.entity.setCurrentItemOrArmor(2,null);
                event.entity.setCurrentItemOrArmor(3,null);
                event.entity.setCurrentItemOrArmor(4,null);
                event.entity.setInvisible(true);
            }
        }
    }

    @SubscribeEvent
    public void AutoOFF(WorldEvent.Unload event) {
        if(Functions.GetStatus("HidePlayers")){
            HidePlayers.Reload = true;
            Functions.SetStatusWithout("HidePlayers",false);
        }
    }
    @SubscribeEvent
    public void AutoON(RenderWorldLastEvent event) {
        if(!Functions.GetStatus("HidePlayers") && HidePlayers.Reload){
            HidePlayers.Reload = false;
            Functions.SetStatusWithout("HidePlayers",true);
        }
    }
}