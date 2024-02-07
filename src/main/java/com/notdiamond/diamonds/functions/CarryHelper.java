package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.utils.text;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CarryHelper {
    @SubscribeEvent
    public void Carryelper(ClientChatReceivedEvent event) {
        if (Functions.GetStatus("CarryHelper")){
            String msg = event.message.getUnformattedText();
            String player = "";
            IChatComponent ALL = null;

            if(msg.contains("entered The Catacombs, Floor ") || msg.contains(" entered MM Catacombs, Floor ") == true || msg.contains("!/clear") == true) {
                DiamondS.TradeList.clear();
                DiamondS.SendMessage("§a§l你已进入游戏，已清空Trade List");
                return;
            }

            if(msg.contains("Trade completed with ") && msg.contains("!") == true) {
                if (msg.contains("] ") == true) {
                    player = text.getSubString(msg, "] ", "!");
                } else {
                    player = text.getSubString(msg, "Trade completed with ", "!");
                }
                player.replace(" ", "");

                boolean AlreadyTrade = false;
                String ALLTraded = "";
                for (int i=0;i <= DiamondS.TradeList.size() - 1;i++){
                    if(DiamondS.TradeList.get(i).contains(player) == true){
                        AlreadyTrade = true;
                    }
                    if(i <= 0){
                        ALLTraded = DiamondS.TradeList.get(i);
                    } else {
                        ALLTraded = ALLTraded + "\n§a"+DiamondS.TradeList.get(i);
                    }
                }

                if(AlreadyTrade == false){
                    if(DiamondS.TradeList.size() <= 0){
                        DiamondS.TradeList.add(player);
                        ALLTraded = "§a"+ player;
                    }else{
                        DiamondS.TradeList.add(player);
                        ALLTraded = ALLTraded + "\n§a"+ player;
                    }
                }
                if(DiamondS.TradeList.size() < 4){
                    ALL = new ChatComponentText("§b§lDiamondS > §r 当前已有 §a"+DiamondS.TradeList.size()+" §r人已完成 Trade");
                    ChatStyle Trade_Style = new ChatStyle();
                    Trade_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§a"+ALLTraded+"\n§b点击查看队伍成员")));
                    Trade_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/p list"));
                    ALL.setChatStyle(Trade_Style);
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(event.message.createCopy());
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                }else{
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(event.message.createCopy());
                    ALL = new ChatComponentText("§b§lDiamondS > §a§l所有人均已完成 Trade，可以进入 Dungeon");
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                }
                return;
            }
        }
    }
}
