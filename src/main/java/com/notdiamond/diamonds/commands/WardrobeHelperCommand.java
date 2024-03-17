package com.notdiamond.diamonds.commands;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Function.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class WardrobeHelperCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "wb";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/wb <数量(1~18)> 切换套装";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args){
        if(Functions.GetStatus("WardrobeHelper")){
            if(args.length <= 0){
                DiamondS.SendMessage("§c指令错误，正确的用法为：§l/wb <数量(1~18)>");
                return;
            }
            int Slot = Integer.parseInt(args[0].toLowerCase());
            if(Slot<1 || Slot >18){
                DiamondS.SendMessage("§c指令错误，正确的用法为：§l/wb <数量(1~18)>");
                return;
            }
            if(DiamondS.tempint >0){
                IChatComponent Message = new ChatComponentText("§a§l[重置功能]");
                ChatStyle Message_Style = new ChatStyle();
                Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§a点击重置 §lWardrobeHelper §a功能状态")));
                Message_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/fs WardrobeHelper clear"));
                Message.setChatStyle(Message_Style);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §r§cWardrobeHelper 功能正在运行中或出现异常，请重置功能").appendSibling(Message));
                return;
            }else{
                DiamondS.tempint = Slot;
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/wardrobe");
                return;
            }
        }else{
            IChatComponent Message = new ChatComponentText("§a§l[打开功能]");
            ChatStyle Message_Style = new ChatStyle();
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§a点击切换 §lWardrobeHelper §a功能状态")));
            Message_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/fc WardrobeHelper"));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §r§c请先打开 WardrobeHelper 功能 ").appendSibling(Message));
            return;
        }
    }
}
