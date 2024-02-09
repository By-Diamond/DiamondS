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
            if(msg.startsWith("公会 > ") || msg.startsWith("组队 > ") || msg.startsWith("Guild > ") || msg.startsWith("Party > ") || msg.startsWith("Co-op > ")==true || msg.startsWith("Co-op > ")==true){
                return;
            }
            if(msg.contains("free") && msg.contains("coin")) {
                event.setCanceled(true);
                return;
            }
            if(msg.contains("You earned") && msg.contains("from playing SkyBlock!")) {
                event.setCanceled(true);
                return;
            }
            if(msg.toLowerCase().contains("lowball")) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("/ah") || msg.toLowerCase().contains("my ah")) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("discord")) {
                event.setCanceled(true);
                return;
            }
            if(msg.toLowerCase().contains("guild") && msg.toLowerCase().contains("join")) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("visit")) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("donate")) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("buy")) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("sell")) {
                event.setCanceled(true);
                return;
            }

            if(msg.toLowerCase().contains("joined the lobby!") || msg.toLowerCase().contains("进入了大厅！")) {
                event.setCanceled(true);
                return;
            }

            if(msg.contains("REWARD!") && msg.contains("in their Bedrock Chest!")) {
                event.setCanceled(true);
                return;
            }

            if(msg.contains("the Hype Diamond") || msg.contains("人气钻石已达上限")) {
                event.setCanceled(true);
                return;
            }
        }
    }
}
