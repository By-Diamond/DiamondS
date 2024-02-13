package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.utils.DText;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatClass {

    public static boolean CarryHelper_IsAutoWarp = false;
    public static boolean CarryHelper_IsAutoMessage = false;
    public static String CarryHelper_AutoMessage = "Pay at sand";
    public static String NickName_Name = "§b[You]";

    @SubscribeEvent
    public void ChatFunctions(ClientChatReceivedEvent event) {
        String msg = event.message.getUnformattedText();
        msg = DText.RemoveColor(msg);
        String player;
        IChatComponent ALL;

        if(DiamondS.PLAYERNAME.replaceAll(" ", "").contentEquals("")){
            if(Minecraft.getMinecraft().thePlayer != null){
                DiamondS.PLAYERNAME = Minecraft.getMinecraft().thePlayer.getName();
            }
        }
        if(msg.contains("❤") && msg.contains("✎")){return;}
        if(msg.startsWith("公会") ||  msg.startsWith("Guild")){
            if(msg.contains("NotDiamond") && msg.toLowerCase().contains("diamondstest")){
                if(Minecraft.getMinecraft().thePlayer != null){
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/msg NotDiamond DiamondS [Version " +DiamondS.VERSION+"]");
                }
                event.setCanceled(true);
            }
            return;
        }
        if(msg.startsWith("To ")){
            if(msg.contains("NotDiamond") && msg.contains("DiamondS [Version ")){
                event.setCanceled(true);
            }
            return;
        }
        if(msg.startsWith("From ") || msg.startsWith("[SkyBlock]") || msg.startsWith("[NPC]") || msg.startsWith("[Bazaar]") || msg.startsWith("组队") || msg.startsWith("Co-op")){return;}
        if(msg.startsWith("Party") && !(msg.startsWith("Party Finder > "))){return;}


        if(Functions.GetStatus("WardrobeHelper")){
            if (msg.contains("You can't use this menu while in combat!") && DiamondS.tempint > 0){
                event.setCanceled(true);
                DiamondS.SendMessage("§c§lWardwrobeHelper §r§c启动失败，原因是：§a你正在战斗中§c，请重试");
                DiamondS.tempint =0;
                return;
            }
        }
        if(Functions.GetStatus("CarryHelper")){
            if(msg.contains("Party Finder > ") && msg.contains(" joined the dungeon group!")){
                Minecraft.getMinecraft().thePlayer.playSound("random.levelup",1,1);
                if(CarryHelper_IsAutoWarp){
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/p warp");
                }
                if(CarryHelper_IsAutoMessage){
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc "+CarryHelper_AutoMessage);
                }
            }
            if(msg.contains("entered The Catacombs, Floor ") || msg.contains(" entered MM Catacombs, Floor ")) {
                DiamondS.TradeList.clear();
                DiamondS.SendMessage("§a§l你已进入游戏，已清空Trade List");
                return;
            }

            if(msg.contains("Trade completed with ") && msg.contains("!")) {
                if (msg.contains("] ")) {
                    player = DText.getSubString(msg, "] ", "!");
                } else {
                    player = DText.getSubString(msg, "Trade completed with ", "!");
                }
                player.replace(" ", "");

                boolean AlreadyTrade = false;
                String ALLTraded = "";
                for (int i=0;i <= DiamondS.TradeList.size() - 1;i++){
                    if(DiamondS.TradeList.get(i).contains(player)){
                        AlreadyTrade = true;
                    }
                    if(i <= 0){
                        ALLTraded = DiamondS.TradeList.get(i);
                    } else {
                        ALLTraded += "\n§a"+DiamondS.TradeList.get(i);
                    }
                }

                if(!AlreadyTrade){
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
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(event.message.createCopy());
                    ALL = new ChatComponentText("§b§lDiamondS > §a§l所有人均已完成 Trade，可以进入 Dungeon");
                    ChatStyle Trade_Style = new ChatStyle();
                    Trade_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(" §a"+ALLTraded+"\n§b点击清空 Trade List ")));
                    Trade_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/fs carryhelper clear"));
                    ALL.setChatStyle(Trade_Style);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                }
                return;
            }
        }

        if(msg.toLowerCase().contains(DiamondS.PLAYERNAME.toLowerCase())){
            if(Functions.GetStatus("NickName")){
                event.setCanceled(true);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DText.Replace(msg, DiamondS.PLAYERNAME, NickName_Name+"§r")));
            }
            return;
        }

        if(Functions.GetStatus("ADClear")){
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
                if(msg.contains("first") && msg.contains("say")) {
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
                if(msg.contains("donation")  || msg.contains("donate")) {
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("visit")){
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("buy")){
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("sell")){
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("discord")){
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("lowbal")){
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("bid")){
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("map")){
                    event.setCanceled(true);
                    return;
                }


            }else{
                if(msg.contains("REWARD!") && msg.contains("in their") && msg.contains("Chest")) {
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("joined the lobby!") || msg.contains("进入了大厅！")) {
                    event.setCanceled(true);
                    return;
                }
                if(msg.contains("the Hype Diamond") || msg.contains("人气钻石已达上限")) {
                    event.setCanceled(true);
                    return;
                }
                if(msg.toLowerCase().contains("earned") && msg.toLowerCase().contains("from playing")) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
        if(Functions.GetStatus("PartyHelper")){
            if((msg.startsWith("Party Finder > "))
                    || msg.endsWith("has left the party.")
                    || msg.endsWith("离开了组队。")
                    || msg.endsWith(" to your ignore list.")
                    || msg.endsWith("添加至屏蔽列表。"))
            {
                player="";
                ALL = null;
                IChatComponent Kick = new ChatComponentText("[Kick]");
                ChatStyle Kick_Style = new ChatStyle();
                Kick_Style.setColor(EnumChatFormatting.RED);
                Kick_Style.setBold(true);
                Kick_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("踢出组队")));

                IChatComponent Invite = new ChatComponentText("[Invite]");
                ChatStyle Invite_Style = new ChatStyle();
                Invite_Style.setColor(EnumChatFormatting.GREEN);
                Invite_Style.setBold(true);
                Invite_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("邀请进入组队")));

                IChatComponent Ignore = new ChatComponentText("[Ignore]");
                ChatStyle Ignore_Style = new ChatStyle();
                Ignore_Style.setColor(EnumChatFormatting.WHITE);
                Ignore_Style.setBold(true);
                Ignore_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("将玩家拉黑")));

                IChatComponent RmIgnore = new ChatComponentText("[Ignore Remove]");
                ChatStyle RmIgnore_Style = new ChatStyle();
                RmIgnore_Style.setColor(EnumChatFormatting.WHITE);
                RmIgnore_Style.setBold(true);
                RmIgnore_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("将玩家解除拉黑")));

                IChatComponent PV = new ChatComponentText("[PV]");
                ChatStyle PV_Style = new ChatStyle();
                PV_Style.setColor(EnumChatFormatting.GOLD);
                PV_Style.setBold(true);
                PV_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("查看玩家档案(需要安装 NEU Mod)")));

                IChatComponent Blank = new ChatComponentText("  ");

                if(msg.contains("Party Finder > ") && msg.contains(" joined the dungeon group!")){
                    player = DText.getSubString(msg,"Party Finder > "," joined the dungeon group!");
                    player.replaceAll(" ","");

                    if(player.contentEquals(Minecraft.getMinecraft().thePlayer.getName()) || player.toLowerCase().contentEquals("you")){
                        return;
                    }

                    ALL = event.message.createCopy();


                    PV_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/pv " + player));
                    Kick_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/p kick " + player));
                    Ignore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore add " + player));
                    PV.setChatStyle(PV_Style);
                    Kick.setChatStyle(Kick_Style);
                    Ignore.setChatStyle(Ignore_Style);

                    ALL.appendSibling(Blank);
                    ALL.appendSibling(PV);
                    ALL.appendSibling(Blank);
                    ALL.appendSibling(Kick);
                    ALL.appendSibling(Blank);
                    ALL.appendSibling(Ignore);
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                    if(Functions.GetStatus("CarryHelper")){
                        Minecraft.getMinecraft().thePlayer.playSound("random.levelup",1,1);
                    }
                    return;
                }
                if(msg.contains("has left the party.")){
                    if (msg.contains("] ")){
                        player = DText.getSubString(msg,"] ","has left the party.");
                    }else{
                        player = DText.getSubString(msg,"","has left the party.");
                    }
                    player.replace(" ","");

                    if(player.contentEquals(Minecraft.getMinecraft().thePlayer.getName())){
                        return;
                    }

                    ALL = event.message.createCopy();

                    Invite_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/p " + player));
                    Ignore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore add " + player));
                    Invite.setChatStyle(Invite_Style);
                    Ignore.setChatStyle(Ignore_Style);

                    ALL.appendSibling(Blank);
                    ALL.appendSibling(Invite);
                    ALL.appendSibling(Blank);
                    ALL.appendSibling(Ignore);
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                    return;
                }
                if(msg.contains("离开了组队。")){
                    if (msg.contains("] ") == true){
                        player = DText.getSubString(msg,"] ","离开了组队。");
                    }else{
                        player = DText.getSubString(msg,"","离开了组队。");
                    }
                    player.replace(" ","");

                    if(player.contentEquals(Minecraft.getMinecraft().thePlayer.getName()) || player.contentEquals("你")){
                        return;
                    }

                    ALL = event.message.createCopy();

                    Invite_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/p " + player));
                    Ignore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore add " + player));
                    Invite.setChatStyle(Invite_Style);
                    Ignore.setChatStyle(Ignore_Style);

                    ALL.appendSibling(Blank);
                    ALL.appendSibling(Invite);
                    ALL.appendSibling(Blank);
                    ALL.appendSibling(Ignore);
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                    return;
                }
                if(msg.contains("Added ") && msg.contains(" to your ignore list.")){
                    player = DText.getSubString(msg,"Added "," to your ignore list.");
                    player.replace(" ","");

                    ALL = event.message.createCopy();

                    RmIgnore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore remove " + player));
                    RmIgnore.setChatStyle(RmIgnore_Style);

                    ALL.appendSibling(Blank);
                    ALL.appendSibling(RmIgnore);
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);

                    return;
                }
                if(msg.startsWith("已将") && msg.endsWith("添加至屏蔽列表。")){
                    player = DText.getSubString(msg,"已将","添加至屏蔽列表。");
                    player.replace(" ","");

                    ALL = event.message.createCopy();

                    RmIgnore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore remove " + player));
                    RmIgnore.setChatStyle(RmIgnore_Style);

                    ALL.appendSibling(Blank);
                    ALL.appendSibling(RmIgnore);
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);

                    return;
                }
            }
        }
        if(Functions.GetStatus("IgnoreShow")){
            if(msg.toLowerCase().contains(DiamondS.PLAYERNAME.toLowerCase())){return;}
            String tempmsg = event.message.getUnformattedText();
            if(tempmsg.contains(" is holding §8[") || tempmsg.contains(" is friends with a §8[") || tempmsg.contains(" is wearing §8[")){
                event.setCanceled(true);
                return;
            }
        }
    }

}
