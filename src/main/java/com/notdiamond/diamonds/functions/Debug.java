package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.utils.DSystem;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Debug {
    @SubscribeEvent
    public void MsgCopy(ClientChatReceivedEvent event){
        if(Functions.GetStatus("Debug.MsgCopy")){
            String msg = event.message.getUnformattedText();
            if(msg.contains("§6") && msg.contains("❤ ") && msg.contains("✎")){
                return;
            }
            DSystem.copyToClipboard(event.message.getUnformattedText());
        }
    }
}
