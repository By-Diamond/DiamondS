package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.utils.DSystem;
import com.notdiamond.diamonds.utils.DText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Debug {
    @SubscribeEvent
    public void MsgCopy(ClientChatReceivedEvent event){
        if(Functions.GetStatus("Debug.MsgCopy")){
            String msg = event.message.getFormattedText();
            msg = DText.RemoveColor(msg);
            if(msg.contains("❤") && msg.contains("✎")){
                return;
            }
            DSystem.copyToClipboard(msg);
        }
    }
}
