package com.notdiamond.diamonds.functions.Chat;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Function.Functions;
import com.notdiamond.diamonds.utils.DText;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.notdiamond.diamonds.DiamondS.mc;

public class ChatClass {

    public static String NickName_Name = "§b[You]";

    @SubscribeEvent
    public void ChatFunctions(ClientChatReceivedEvent event) {
        String msg = event.message.getUnformattedText();
        msg = DText.RemoveColor(msg);
        String player;
        IChatComponent ALL;

        if(CheckMessage(msg, event)){
            return;
        }

        if(Functions.GetStatus("WardrobeHelper")){
            if (msg.contains("You can't use this menu while in combat!") && DiamondS.tempint > 0){
                event.setCanceled(true);
                DiamondS.SendMessage("§c§lWardwrobeHelper §r§c启动失败，原因是：§a你正在战斗中§c，请重试");
                DiamondS.tempint =0;
                return;
            }
        }
        if(msg.toLowerCase().contains(mc.thePlayer.getName().toLowerCase())){
            if(Functions.GetStatus("NickName")){
                event.setCanceled(true);
                mc.thePlayer.addChatMessage(new ChatComponentText(DText.Replace(event.message.getUnformattedText(), mc.thePlayer.getName(), NickName_Name)));
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
                    player = player.replaceAll(" ","");

                    if(player.contentEquals(mc.thePlayer.getName()) || player.toLowerCase().contentEquals("you")){
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
                    mc.thePlayer.addChatMessage(ALL);
                    return;
                }
                if(msg.contains("has left the party.")){
                    if (msg.contains("] ")){
                        player = DText.getSubString(msg,"] ","has left the party.");
                    }else{
                        player = DText.getSubString(msg,"","has left the party.");
                    }
                    player = player.replace(" ","");

                    if(player.contentEquals(mc.thePlayer.getName())){
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
                    mc.thePlayer.addChatMessage(ALL);
                    return;
                }
                if(msg.contains("离开了组队。")){
                    if (msg.contains("] ")){
                        player = DText.getSubString(msg,"] ","离开了组队。");
                    }else{
                        player = DText.getSubString(msg,"","离开了组队。");
                    }
                    player = player.replaceAll(" ","");

                    if(player.contentEquals(mc.thePlayer.getName()) || player.contentEquals("你")){
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
                    mc.thePlayer.addChatMessage(ALL);
                    return;
                }
                if(msg.contains("Added ") && msg.contains(" to your ignore list.")){
                    player = DText.getSubString(msg,"Added "," to your ignore list.");
                    player = player.replace(" ","");

                    ALL = event.message.createCopy();

                    RmIgnore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore remove " + player));
                    RmIgnore.setChatStyle(RmIgnore_Style);

                    ALL.appendSibling(Blank);
                    ALL.appendSibling(RmIgnore);
                    event.setCanceled(true);
                    mc.thePlayer.addChatMessage(ALL);

                    return;
                }
                if(msg.startsWith("已将") && msg.endsWith("添加至屏蔽列表。")){
                    player = DText.getSubString(msg,"已将","添加至屏蔽列表。");
                    player = player.replace(" ","");

                    ALL = event.message.createCopy();

                    RmIgnore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore remove " + player));
                    RmIgnore.setChatStyle(RmIgnore_Style);

                    ALL.appendSibling(Blank);
                    ALL.appendSibling(RmIgnore);
                    event.setCanceled(true);
                    mc.thePlayer.addChatMessage(ALL);

                    return;
                }
            }
        }
        if(Functions.GetStatus("IgnoreShow")){
            if(msg.toLowerCase().contains(mc.thePlayer.getName().toLowerCase())){return;}
            String tempmsg = event.message.getUnformattedText();
            if(tempmsg.contains(" is holding §8[") || tempmsg.contains(" is friends with a §8[") || tempmsg.contains(" is wearing §8[")){
                event.setCanceled(true);
            }
        }
    }

    public static boolean CheckMessage(String msg ,ClientChatReceivedEvent event){
        if(msg.contains("❤") && msg.contains("✎")){return true;}
        if(msg.startsWith("公会") ||  msg.startsWith("Guild")){
            if(msg.contains("NotDiamond") && msg.toLowerCase().contains("diamondstest")){
                if (mc.thePlayer != null) {
                    mc.thePlayer.sendChatMessage("/msg NotDiamond DiamondS [Version " +DiamondS.VERSION+"]");
                }
                event.setCanceled(true);
            }
            return true;
        }
        if(msg.startsWith("To ")){
            if(msg.contains("NotDiamond") && msg.contains("DiamondS [Version ")){
                event.setCanceled(true);
            }
            return true;
        }
        if(msg.startsWith("Officer >") || msg.startsWith("[Harp]") || msg.startsWith("[Auction]") || msg.startsWith("From ") || msg.startsWith("[SkyBlock]") || msg.startsWith("[NPC]") || msg.startsWith("[Bazaar]") || msg.startsWith("组队") || msg.startsWith("Co-op")){return true;}
        if(msg.startsWith("Party") && !(msg.startsWith("Party Finder > "))){return true;}

        return false;
    }
}
