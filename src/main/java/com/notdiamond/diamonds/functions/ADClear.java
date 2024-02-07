package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.core.Functions;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ADClear {
    @SubscribeEvent
    public void ADClear(ClientChatReceivedEvent event) {
        if(Functions.GetStatus("ADClear")){
            String msg = event.message.getUnformattedText();
            String player = "";
            IChatComponent ALL = null;
            if(msg.contains("You earned") && msg.contains("from playing SkyBlock!") == true) {
                event.setCanceled(true);
                return;
            }
            if(msg.toLowerCase().contains("lowball") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("/ah") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("visit") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("donate") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("buy") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("sell") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("joined the lobby!") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.contains("REWARD!") == true && msg.contains("in their Bedrock Chest!") == true) {
                event.setCanceled(true);
                return;
            }

            if(msg.contains("the Hype Diamond") == true) {
                event.setCanceled(true);
                return;
            }
        }
    }
}
