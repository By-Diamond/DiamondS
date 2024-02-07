package com.notdiamond.diamonds.commands;

import com.google.common.collect.Lists;
import com.notdiamond.diamonds.DiamondS;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class menu extends CommandBase {

    private final ArrayList<String> aliases = Lists.newArrayList(DiamondS.MODID, "function", "menu", "help");

    @Override
    public String getCommandName() {
        return "menu";
    }

    @Override
    public List<String> getCommandAliases()
    {
        return aliases;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/menu 查看DiamondS功能菜单";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        //sender instanceof EntityPlayer
        //DiamondS.SendMessage(Arrays.toString(args));
        IChatComponent ALL = null;
        IChatComponent Message = null;
        String Color = "";
        String Command = "";
        String HoverText = "";
        ChatStyle Menu_Style = new ChatStyle();
        Menu_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§b点击刷新 §lDiamondS §r§b功能列表")));
        Menu_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/menu"));
        ALL = new ChatComponentText("§b§l----- DiamondS 功能 -----\n").setChatStyle(Menu_Style);

        for (int i =0;i<=DiamondS.FunctionList.size()-1;i++){
            if(DiamondS.FunctionList.get(i).GetStatus()){
                Color = "§a§l";
                Command="/fc " + DiamondS.FunctionList.get(i).GetName();
                HoverText = "§e§l点击切换功能状态";
            }else{
                Color = "§c";
                Command="/fc " + DiamondS.FunctionList.get(i).GetName();
                HoverText = "§e§l点击切换功能状态";
            }
            Message = new ChatComponentText(" "+Color+DiamondS.FunctionList.get(i).GetName()+"\n");
            ChatStyle Message_Style = new ChatStyle();
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(HoverText)));
            Message_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,Command));
            Message.setChatStyle(Message_Style);
            ALL.appendSibling(Message);
        }
        ALL.appendSibling(new ChatComponentText("§b§l-----------------------")).setChatStyle(Menu_Style);
        Minecraft.getMinecraft().thePlayer.addChatMessage(ALL);
        return;
    }
}