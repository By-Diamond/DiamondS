package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.utils.text;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PartyHelper {
    @SubscribeEvent
    public void PartyHelper(ClientChatReceivedEvent event) {
        if(Functions.GetStatus("PartyHelper")){
            String msg = event.message.getUnformattedText();
            String player = "";
            IChatComponent ALL = null;

            IChatComponent Kick = new ChatComponentText(" [Kick]");
            ChatStyle Kick_Style = new ChatStyle();
            Kick_Style.setColor(EnumChatFormatting.RED);
            Kick_Style.setBold(true);
            Kick_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("踢出组队")));

            IChatComponent Invite = new ChatComponentText(" [Invite]");
            ChatStyle Invite_Style = new ChatStyle();
            Invite_Style.setColor(EnumChatFormatting.GREEN);
            Invite_Style.setBold(true);
            Invite_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("邀请进入组队")));

            IChatComponent Ignore = new ChatComponentText(" [Ignore]");
            ChatStyle Ignore_Style = new ChatStyle();
            Ignore_Style.setColor(EnumChatFormatting.WHITE);
            Ignore_Style.setBold(true);
            Ignore_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("将玩家拉黑")));

            IChatComponent RmIgnore = new ChatComponentText(" [Ignore Remove]");
            ChatStyle RmIgnore_Style = new ChatStyle();
            RmIgnore_Style.setColor(EnumChatFormatting.WHITE);
            RmIgnore_Style.setBold(true);
            RmIgnore_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("将玩家解除拉黑")));

            IChatComponent PV = new ChatComponentText(" [PV]");
            ChatStyle PV_Style = new ChatStyle();
            PV_Style.setColor(EnumChatFormatting.GOLD);
            PV_Style.setBold(true);
            PV_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("查看玩家档案(需要安装 NEU Mod)")));


            if(msg.contains("Party Finder > ") == true && msg.contains(" joined the dungeon group!") == true){
                player = text.getSubString(msg,"Party Finder > "," joined the dungeon group!");
                player.replace(" ","");

                ALL = event.message.createCopy();

                PV_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/pv " + player));
                Kick_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/p kick " + player));
                Ignore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore add " + player));
                PV.setChatStyle(PV_Style);
                Kick.setChatStyle(Kick_Style);
                Ignore.setChatStyle(Ignore_Style);

                ALL.appendSibling(PV);
                ALL.appendSibling(Kick);
                ALL.appendSibling(Ignore);
                event.setCanceled(true);
                Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                return;
            }

            if(msg.contains("has left the party.") == true){
                if (msg.contains("] ") == true){
                    player = text.getSubString(msg,"] ","has left the party.");
                }else{
                    player = text.getSubString(msg,"","has left the party.");
                }
                player.replace(" ","");

                ALL = event.message.createCopy();

                Invite_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/p " + player));
                Ignore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore add " + player));
                Invite.setChatStyle(Invite_Style);
                Ignore.setChatStyle(Ignore_Style);

                ALL.appendSibling(Invite);
                ALL.appendSibling(Ignore);
                event.setCanceled(true);
                Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
                return;
            }

            if(msg.contains("Added ") && msg.contains(" to your ignore list.") == true){
                player = text.getSubString(msg,"Added "," to your ignore list.");
                player.replace(" ","");

                ALL = event.message.createCopy();

                RmIgnore_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ignore remove " + player));
                RmIgnore.setChatStyle(RmIgnore_Style);

                ALL.appendSibling(RmIgnore);
                event.setCanceled(true);
                Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);

                return;
            }
        }
    }
}
