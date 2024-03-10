package com.notdiamond.diamonds.functions.Chat.ChannelChanger;

import com.notdiamond.diamonds.core.Functions;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.notdiamond.diamonds.DiamondS.mc;

public class ChannelChanger {
    public static boolean IsInChat = false;

    @SubscribeEvent
    public void OnKeyPressed(GuiOpenEvent event){
        if(Functions.GetStatus("ChannelChanger")){
            if(event.gui instanceof GuiChat && mc.thePlayer != null && mc.theWorld != null && !IsInChat){
                IsInChat = true;
                event.gui = new ChannelButtons();
            }
        }
    }
}
