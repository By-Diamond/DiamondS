package com.notdiamond.diamonds.commands;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.core.HUD;
import com.notdiamond.diamonds.functions.Macro.PlayerFinder.PlayerFinder;
import com.notdiamond.diamonds.functions.Macro.WormCleaner;
import com.notdiamond.diamonds.functions.Player.AngleLock;
import com.notdiamond.diamonds.functions.Chat.ChatClass;
import com.notdiamond.diamonds.functions.Debug;
import com.notdiamond.diamonds.functions.Player.WardrobeHelper;
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

import java.io.IOException;

import static com.notdiamond.diamonds.DiamondS.mc;

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
        try {
            SetFunctions(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void SetFunctions(String args[]) throws IOException {
        if(args.length < 1){
            DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...");
            return;
        }
        String theFunction = args[0].toLowerCase().replaceAll(" ", "");
        String theSetting ="";
        if(args.length >= 2){
            theSetting = args[1].toLowerCase().replaceAll(" ", "");
        }
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
                    return;
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
                    return;
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
                    return;
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
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bclear - 清空Trade List\n§bAutoWarp - 玩家进入自动/p warp\n§bAutoMsg - 玩家进入自动发送消息\n§bMsg - 设置AutoMsg消息内容\n\n§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
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
            if(theSetting.contentEquals("delay")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs WardrobeHelper delay <数值/ms>");
                    return;
                }
                WardrobeHelper.Delay = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l Delay §r§a为 §r" + args[2]+ "§r§ams");
                Config.saveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bclear - 重置功能进程\n§bdelay - 设置点击延迟(单位 ms)\n\n§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("nickname")){
            if(theSetting.contentEquals("name")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs NickName <名称>");
                    return;
                }
                String NewName = args[2];
                for (int i=2;i<=args.length-1;i++){
                    if(i==2){
                        NewName=args[2];
                    }else{
                        NewName+=" "+args[i];
                    }
                }
                NewName = DText.AddColor(NewName);
                ChatClass.NickName_Name = NewName;
                DiamondS.SendMessage("§a设置§l NickName §r§a为：§r"+NewName);
                Config.saveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bName - 设置匿名名称(支持颜色，例如§a&a§1&1§b)\n\n§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("hud")){
            if(args.length < 3){
                DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs HUD <功能设置项(X/Y)> <数值(位置)>");
                return;
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

            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bX - 设置HUD X轴位置\n§bY - 设置HUD Y轴位置 \n\n§a用法为：§l/fs HUD <功能设置项(X/Y)> <数值(位置)>")));
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
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bMsgCopy - 自动复制玩家消息\n\n§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("anglelock")){
            if(theSetting.contentEquals("setrot")){
                AngleLock.Yaw = (int) mc.thePlayer.rotationYaw;
                AngleLock.Pitch = (int) mc.thePlayer.rotationPitch;
                DiamondS.SendMessage("§a设置§l Yaw §r§a为 §b§l"+ AngleLock.Yaw+"§r§a°");
                DiamondS.SendMessage("§a设置§l Pitch §r§a为 §b§l"+ AngleLock.Pitch+"§r§a°");
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("yaw")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs MacroHelper Yaw <数值[-180,180)>");
                    return;
                }
                int Value = Integer.parseInt(args[2]);
                if(Value >= 180 || Value < -180){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs MacroHelper Yaw <数值[-180,180)>");
                    return;
                }
                AngleLock.Yaw = Value;
                DiamondS.SendMessage("§a设置§l Yaw §r§a为 §b§l"+args[2]+"§r§a°");
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("pitch")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs MacroHelper Pitch <数值[-90,90]>");
                    return;
                }
                int Value = Integer.parseInt(args[2]);
                if(Value > 90 || Value < -90){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs MacroHelper Pitch <数值[-90,90]>");
                    return;
                }
                AngleLock.Pitch = Value;
                DiamondS.SendMessage("§a设置§l Pitch §r§a为 §b§l"+args[2]+"§r§a°");
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("autobreak")){
                boolean NewStatus = Boolean.parseBoolean(args[2]);
                AngleLock.AutoBreak = NewStatus;
                if(NewStatus){
                    DiamondS.SendMessage("§a设置§l AutoBreak §r§a开启成功");
                }else{
                    DiamondS.SendMessage("§a设置§l AutoBreak §r§c关闭§a成功");
                }
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("lockpitch")){
                boolean NewStatus = Boolean.parseBoolean(args[2]);
                AngleLock.LockPitch = NewStatus;
                if(NewStatus){
                    DiamondS.SendMessage("§a设置§l LockPitch §r§a开启成功");
                }else{
                    DiamondS.SendMessage("§a设置§l LockPitch §r§c关闭§a成功");
                }
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("lockyaw")){
                boolean NewStatus = Boolean.parseBoolean(args[2]);
                AngleLock.LockYaw = NewStatus;
                if(NewStatus){
                    DiamondS.SendMessage("§a设置§l LockYaw §r§a开启成功");
                }else{
                    DiamondS.SendMessage("§a设置§l LockYaw §r§c关闭§a成功");
                }
                Config.saveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bSetRot - 快速设置Yaw和Pitch为玩家当前朝向\n§bYaw - 设置水平视角角度[-180,180)(单位 °)\n§bPitch - 设置垂直视角角度[-90,90](单位 °)\n§bAutoBreak - 是否自动破坏(true/false)\n§bLockYaw - 是否锁定水平视角角度(true/false)\n§bLockPitch - 是否锁定垂直视角角度(true/false) \n\n§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("wormcleaner")){
            if(theSetting.contentEquals("settoolslot")){
                WormCleaner.ToolSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
                DiamondS.SendMessage("§a设置§l ToolSlot §r§a为 §b§l"+ WormCleaner.ToolSlot);
                WormCleaner.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("setrot")){
                WormCleaner.TargetRotation[0] = mc.thePlayer.rotationYaw;
                WormCleaner.TargetRotation[1] = mc.thePlayer.rotationPitch;
                DiamondS.SendMessage("§a设置§l 目标Yaw §r§a为 §b§l"+ WormCleaner.TargetRotation[0]+"§r§a°");
                DiamondS.SendMessage("§a设置§l 目标Pitch §r§a为 §b§l"+ WormCleaner.TargetRotation[1]+"§r§a°");
                WormCleaner.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("settxtpos")){
                if(args.length < 4){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs WormCleaner SetTxtPos <整数 (X轴位置)> <整数 (Y轴位置)>");
                    return;
                }
                WormCleaner.TextPosition[0] = Integer.parseInt(args[2]);
                WormCleaner.TextPosition[1] = Integer.parseInt(args[3]);

                DiamondS.SendMessage("§a设置§l TextPosition §r§a为 X:§b§l"+ WormCleaner.TextPosition[0]+"§r§a Y:§b§l"+ WormCleaner.TextPosition[1]);
                WormCleaner.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("settimes")){
                if(args.length < 3 || Integer.parseInt(args[2]) <0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs WormCleaner SetTimes <整数 (次数)>");
                    return;
                }
                WormCleaner.Times = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l Times §r§a为:§b§l "+ WormCleaner.Times);
                WormCleaner.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("setmaxqty")){
                if(args.length < 3 || Integer.parseInt(args[2]) <0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs WormCleaner SetMaxQty <整数 (Worm数量)>");
                    return;
                }
                WormCleaner.MaxQuantity = Integer.parseInt(args[2]);

                DiamondS.SendMessage("§a设置§l MaxQuantity §r§a为:§b§l "+ WormCleaner.MaxQuantity);
                WormCleaner.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("setdelay")){
                if(args.length < 3 || Integer.parseInt(args[2]) <0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs WormCleaner SetDelay <整数 (延迟/ms)>");
                    return;
                }
                WormCleaner.Delay = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l Delay §r§a为:§b§l "+ WormCleaner.Delay);
                WormCleaner.SaveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bSetToolSlot - 设置清虫工具为当前持有物品Slot\n§bSetRot - 设置玩家当前朝向为Worm位置\n§bSetTxtPos - 设置文字位置\n§bSetTimes - 设置工具使用次数\n§bSetMaxQty - 设置允许的最大worm数量\n§bSetDelay - 设置右击工具间隔时间(单位 ms) \n\n§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("playerfinder")){
            if(theSetting.contentEquals("settxtpos")){
                if(args.length < 4){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs PlayerFinder SetTxtPos <整数 (X轴位置)> <整数 (Y轴位置)>");
                    return;
                }
                PlayerFinder.TextPosition[0] = Integer.parseInt(args[2]);
                PlayerFinder.TextPosition[1] = Integer.parseInt(args[3]);

                DiamondS.SendMessage("§a设置§l TextPosition §r§a为 X:§b§l"+ PlayerFinder.TextPosition[0]+"§r§a Y:§b§l"+ PlayerFinder.TextPosition[1]);
                PlayerFinder.SaveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("§bSetTxtPos - 设置文字位置\n\n§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        DiamondS.SendMessage("§c该功能无自定义设置 或 功能不存在");
    }
}
