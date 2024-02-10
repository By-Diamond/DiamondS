package com.notdiamond.diamonds.commands;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class FunctionSettings extends CommandBase {
    @Override
    public String getCommandName() {
        return "fs";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...   功能设置";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }


    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 2){
            DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...");
            return;
        }
        String theFunction = args[0].toLowerCase();
        String theSetting = args[1].toLowerCase();
        IChatComponent Message = new ChatComponentText("[查看可选设置项]");
        ChatStyle Message_Style = new ChatStyle();
        Message_Style.setColor(EnumChatFormatting.GREEN);
        Message_Style.setBold(true);
        //记住要用小写
        if(theFunction.contentEquals("carryhelper")){
            if(theSetting.contentEquals("clear")){
                DiamondS.TradeList.clear();
                DiamondS.SendMessage("§a清除§lTrade List§r§a成功");
                return;
            }

            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bclear - 清空Trade List")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("wardrobehelper")){
            if(theSetting.contentEquals("clear")){
                DiamondS.tempint =0;
                DiamondS.SendMessage("§a重置 §lWardrobeHelper §r§a功能进程成功");
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bclear - 重置功能进程")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        //
        DiamondS.SendMessage("§c该功能无自定义设置 或 功能不存在");
        return;
    }
}
