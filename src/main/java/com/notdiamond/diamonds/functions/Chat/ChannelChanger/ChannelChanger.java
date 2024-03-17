package com.notdiamond.diamonds.functions.Chat.ChannelChanger;

import com.notdiamond.diamonds.core.Function.Functions;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.notdiamond.diamonds.DiamondS.mc;

public class ChannelChanger {
    public static boolean IsInChat = false;
    public static boolean Commanding = false;

    @SubscribeEvent
    public void OnKeyPressed(GuiOpenEvent event){
        if(Functions.GetStatus("ChannelChanger")){
            if(event.gui instanceof GuiChat && mc.thePlayer != null && mc.theWorld != null && !IsInChat){
                IsInChat = true;
                if(mc.gameSettings.keyBindCommand.isKeyDown()){
                    Commanding = true;
                }
                event.gui = new ChannelButtons();
            }
        }
    }
}
