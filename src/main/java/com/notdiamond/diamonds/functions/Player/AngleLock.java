package com.notdiamond.diamonds.functions.Player;

import com.notdiamond.diamonds.core.Function.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AngleLock {
    public static int Pitch  = 0;
    public static int Yaw  = 90;
    public static boolean AutoBreak = false;
    public static boolean LockYaw = true;
    public static boolean LockPitch = true;

    @SubscribeEvent
    public void MacroHelper(TickEvent.ClientTickEvent event){
        if (Functions.GetStatus("AngleLock")){
            if(Minecraft.getMinecraft().thePlayer != null){
                if(LockYaw){Minecraft.getMinecraft().thePlayer.rotationYaw = Yaw;}
                if(LockPitch){Minecraft.getMinecraft().thePlayer.rotationPitch = Pitch;}
                if(AutoBreak && !Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown()) {KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(),true);}
            }
        }
    }
}
