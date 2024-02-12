package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.core.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MovementClass{
    @SubscribeEvent
    public void Sprint(InputEvent.KeyInputEvent event){
        if(Functions.GetStatus("Sprint")){
            if(Minecraft.getMinecraft().thePlayer != null){
                Minecraft.getMinecraft().thePlayer.setSprinting(true);
            }
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(Functions.GetStatus("Sneak")){
            if(Minecraft.getMinecraft().thePlayer != null){
                if(!Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()){
                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(),true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.ElementType.TEXT){
            ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
            if(Functions.GetStatus("Sprint")&& Minecraft.getMinecraft().fontRendererObj != null){
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("§6Sprint §aOn", 0, scaled.getScaledHeight()-10, 0xFFFFFF);
            }
            if(Functions.GetStatus("Sneak") && Minecraft.getMinecraft().fontRendererObj != null){
                if(Functions.GetStatus("Sprint")){
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("§6Sneak §aOn", 0, scaled.getScaledHeight() - 20, 0xFFFFFF);
                }else{
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("§6Sneak §aOn", 0, scaled.getScaledHeight() - 10, 0xFFFFFF);
                }
            }
        }

    }
}
