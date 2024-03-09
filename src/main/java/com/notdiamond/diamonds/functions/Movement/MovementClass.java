package com.notdiamond.diamonds.functions.Movement;

import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.core.KeyLoader;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;

import static com.notdiamond.diamonds.DiamondS.mc;

public class MovementClass{
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Functions.GetStatus("Sneak")) {
            if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
            }
        }
        if (Functions.GetStatus("Sprint")) {
            if (!mc.gameSettings.keyBindSprint.isKeyDown()) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
            }
        }
        if (Functions.GetStatus("AutoJump")) {
            if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
            }
        }
    }
    @SubscribeEvent
    public void OnKeyPressed(InputEvent.KeyInputEvent event){
        if(KeyLoader.Sprint.isKeyDown()){
            Functions.FunctionSwitch("Sprint");
        }
    }
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.ElementType.TEXT){
            ScaledResolution scaled = new ScaledResolution(mc);
            if(Functions.GetStatus("Sprint")&& mc.fontRendererObj != null){
                mc.fontRendererObj.drawStringWithShadow("§6Sprint §aOn", 2, scaled.getScaledHeight()-10, 0xFFFFFF);
            }
            if(Functions.GetStatus("Sneak") && mc.fontRendererObj != null){
                if(Functions.GetStatus("Sprint")){
                    mc.fontRendererObj.drawStringWithShadow("§6Sneak §aOn", 2, scaled.getScaledHeight() - 20, 0xFFFFFF);
                }else{
                    mc.fontRendererObj.drawStringWithShadow("§6Sneak §aOn", 2, scaled.getScaledHeight() - 10, 0xFFFFFF);
                }
            }
        }

    }
}
