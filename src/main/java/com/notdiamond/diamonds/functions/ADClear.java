package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.DiamondS;
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
            if(msg.startsWith("[NPC]") || msg.startsWith("[Bazaar]") || msg.startsWith("公会 > ") || msg.startsWith("组队 > ") || msg.startsWith("Guild > ") || msg.startsWith("Party > ") || msg.startsWith("Co-op > ")==true || msg.startsWith("Co-op > ")==true){
                return;
            }
            if(DiamondS.IsOnSkyBlock() && msg.startsWith("[")){
                msg = msg.toLowerCase();
                if(msg.contains("free")) {
                    if(msg.contains("carry") || msg.contains("coin") || msg.contains("money")){
                        event.setCanceled(true);
                        return;
                    }
                }
                if(msg.contains("bz") || msg.contains("bazzer")) {
                    if(msg.contains("flip")){
                        event.setCanceled(true);
                        return;
                    }
                }
                if(msg.contains("give")) {
                    if(msg.contains("away") || msg.contains("coin")  || msg.contains("money")){
                        event.setCanceled(true);
                        return;
                    }
                }
                if(msg.contains("you earned") && msg.contains("from playing skyblock!")) {
                    event.setCanceled(true);
                    return;
                }

                if(msg.contains("/ah") || msg.contains("my ah") || msg.contains("bidder")) {
                    event.setCanceled(true);
                    return;
                }

                if(msg.contains("guild") && msg.contains("join")) {
                    event.setCanceled(true);
                    return;
                }

                if(msg.contains("visit") || msg.contains("donate") || msg.contains("buy") || msg.contains("sell") || msg.contains("discord") || msg.contains("lowbal")) {
                    event.setCanceled(true);
                    return;
                }

                if(msg.contains("REWARD!") && msg.contains("in their") && msg.contains("Chest")) {
                    event.setCanceled(true);
                    return;
                }
            }else{
                if(msg.contains("joined the lobby!") || msg.contains("进入了大厅！")) {
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
}
