package com.notdiamond.diamonds.commands;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.core.HUD;
import com.notdiamond.diamonds.functions.ChatClass;
import com.notdiamond.diamonds.functions.Debug;
import com.notdiamond.diamonds.utils.DText;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

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
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length < 2){
            DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...");
            return;
        }
        String theFunction = args[0].toLowerCase().replaceAll(" ", "");
        String theSetting = args[1].toLowerCase().replaceAll(" ", "");
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
            if(theSetting.contentEquals("autowarp")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs CarryHelper AutoWarp <true/false>");
                }
                boolean NewStatus = Boolean.parseBoolean(args[2]);
                ChatClass.CarryHelper_IsAutoWarp = NewStatus;
                if(NewStatus){
                    DiamondS.SendMessage("§a设置§l AutoWarp §r§a开启成功");
                }else{
                    DiamondS.SendMessage("§a设置§l AutoWarp §r§c关闭§a成功");
                }
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("automsg")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs CarryHelper AutoMsg <true/false>");
                }
                boolean NewStatus = Boolean.parseBoolean(args[2]);
                ChatClass.CarryHelper_IsAutoMessage = NewStatus;
                if(NewStatus){
                    DiamondS.SendMessage("§a设置§l AutoMsg §r§a开启成功");
                }else{
                    DiamondS.SendMessage("§a设置§l AutoMsg §r§c关闭§a成功");
                }
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("msg")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs CarryHelper Msg <内容>");
                }
                String Content = "";
                for (int i=2;i<=args.length-1;i++){
                    if(i==2){
                        Content=args[2];
                    }else{
                        Content+=" "+args[i];
                    }
                }
                ChatClass.CarryHelper_AutoMessage=Content;
                DiamondS.SendMessage("§a设置§l Msg §r§a为：\n§b"+Content);
                Config.saveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bclear - 清空Trade List\n§bAutoWarp - 玩家进入自动/p warp\n§bAutoMsg - 玩家进入自动发送消息\n§bMsg - 设置AutoMsg消息内容")));
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
        if(theFunction.contentEquals("nickname")){
            if(theSetting.contentEquals("name")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs NickName <名称>");
                }
                String NewName = DText.AddColor(args[2]);
                ChatClass.NickName_Name = NewName;
                DiamondS.SendMessage("§a设置§l NickName §r§a为：§r"+NewName);
                Config.saveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bName - 设置匿名名称(支持颜色，例如&a&1)")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("hud")){
            if(args.length < 3){
                DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs HUD <功能设置项(X/Y)> <数值(位置)>");
            }
            if(theSetting.contentEquals("x")){
                HUD.X = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l HUD §r§aX轴位置到 §b§l"+args[2]);
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("y")){
                HUD.Y = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l HUD §r§aY轴位置到 §b§l"+args[2]);
                Config.saveConfig();
                return;
            }

            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bX - 设置HUD X轴位置\n§bY - 设置HUD Y轴位置")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("debug")){
            if(theSetting.contentEquals("msgcopy")){
                Functions.RegisterFunction("Debug.MsgCopy","Chat",true);
                MinecraftForge.EVENT_BUS.register(new Debug());
                DiamondS.SendMessage("§a打开 §lDebug.MsgCopy §r§a功能成功");
                if(Functions.GetStatus("ADClear")){
                    Functions.SetStatus("ADClear",false);
                    DiamondS.SendMessage("§c检测到打开§lDebug.MsgCopy§r§c，已自动关闭 §lADClear");
                }
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bMsgCopy - 自动复制玩家消息")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        //
        DiamondS.SendMessage("§c该功能无自定义设置 或 功能不存在");
    }
}
