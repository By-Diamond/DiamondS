package com.notdiamond.diamonds.commands;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.core.HUD;
import com.notdiamond.diamonds.functions.Macro.HarpBot;
import com.notdiamond.diamonds.functions.Render.AntiInvisible;
import com.notdiamond.diamonds.functions.Dungeon.GhostBlock;
import com.notdiamond.diamonds.functions.Macro.AutoFish;
import com.notdiamond.diamonds.functions.Macro.PlayerFinder.PlayerFinder;
import com.notdiamond.diamonds.functions.Macro.WormCleaner;
import com.notdiamond.diamonds.functions.Player.AngleLock;
import com.notdiamond.diamonds.functions.Chat.ChatClass;
import com.notdiamond.diamonds.functions.Debug;
import com.notdiamond.diamonds.functions.Player.WardrobeHelper;
import com.notdiamond.diamonds.utils.DText;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.Timer;

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
        if(theFunction.contentEquals("reload")){
            DiamondS.SendMessage("§b§l正在重新加载 Config");
            Config.LoadConfig();
            DiamondS.SendMessage("§a§lConfig 重新加载完成");
            return;
        }

        if(theFunction.contentEquals("update")){
            DiamondS.SendMessage("§8正在检查更新中");
            if(DiamondS.CheckUpdate()){
                DiamondS.SendMessage("§a您使用的是最新版本的 §b§lDiamondS");
                DiamondS.SendMessage("§e§o好耶!!!");
            }else{
                DiamondS.SendMessage("§c§lemm...我发现了新版的 §b§lDiamondS");
                DiamondS.SendMessage("§e§l你可以去更新一下吗? "+"§d§o嘤嘤嘤 TvT");
            }
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
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bMsgCopy §r- 自动复制玩家消息" + "\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("carryhelper")){
            if(theSetting.contentEquals("clear")){
                DiamondS.TradeList.clear();
                DiamondS.SendMessage("§a清除§l Trade List §r§a成功");
                return;
            }
            if(theSetting.contentEquals("autowarp")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs CarryHelper AutoWarp <状态(true/false)>");
                    return;
                }
                boolean NewStatus = Boolean.parseBoolean(args[2]);
                ChatClass.CarryHelper_IsAutoWarp = NewStatus;
                if(NewStatus){
                    DiamondS.SendMessage("§a设置§l AutoWarp §r§a开启 成功");
                }else{
                    DiamondS.SendMessage("§c设置§l AutoWarp §r§c关闭 成功");
                }
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("automsg")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs CarryHelper AutoMsg <状态(true/false)>");
                    return;
                }
                boolean NewStatus = Boolean.parseBoolean(args[2]);
                ChatClass.CarryHelper_IsAutoMessage = NewStatus;
                if(NewStatus){
                    DiamondS.SendMessage("§a设置§l AutoMsg §r§a开启成功");
                }else{
                    DiamondS.SendMessage("§c设置§l AutoMsg §r§c关闭 成功");
                }
                Config.saveConfig();
                return;
            }
            if(theSetting.contentEquals("msg")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs CarryHelper Msg <文本内容>");
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
                DiamondS.SendMessage("§a设置§l Msg §r§a为 ：\n§b"+Content);
                Config.saveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bclear §r- 清空Trade List" + "\n" +
                            "§bAutoWarp <状态(true/false)> §r- 玩家进入自动/p warp §9§l" + ChatClass.CarryHelper_IsAutoWarp+"\n" +
                            "§bAutoMsg <状态(true/false)> §r- 玩家进入自动发送消息 §9§l" + ChatClass.CarryHelper_IsAutoMessage+"\n" +
                            "§bMsg <文本内容> §r- 设置AutoMsg消息内容 §9§l" +ChatClass.CarryHelper_AutoMessage+ "\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
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
                if(Integer.parseInt(args[2]) <= 0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs WardrobeHelper delay <数值/ms>");
                    return;
                }
                WardrobeHelper.Delay = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l Delay §r§a为 §r" + args[2]+ "§r§ams");
                Config.saveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bclear §r- 重置功能进程 §9§l" + "\n" +
                            "§bdelay <数值(单位/ms)> §r- 设置点击延迟 §9§l" + WardrobeHelper.Delay+"ms\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("nickname")){
            if(theSetting.contentEquals("name")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs NickName Name <名称>");
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
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bName <名称> §r- 设置匿名名称(支持颜色，例如§a&a§1&1§b&b§r) §r§l" + ChatClass.NickName_Name+"\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("hud")){
            if(theSetting.contentEquals("settxtpos")){
                if(args.length < 4){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs HUD SetTxtPos <整数 (X轴位置)> <整数 (Y轴位置)>");
                    return;
                }
                HUD.X = Integer.parseInt(args[2]);
                HUD.Y = Integer.parseInt(args[3]);

                DiamondS.SendMessage("§a设置§l TextPosition §r§a为 X:§b§l"+ HUD.X+"§r§a Y:§b§l"+ HUD.Y);
                Config.saveConfig();
                return;
            }

            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bSetTxtPos <整数 (X轴位置)> <整数 (Y轴位置)> §r- 设置HUD位置 §9§l["+HUD.X + ","+HUD.Y+"]\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
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
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bSetRot §r- 快速设置Yaw和Pitch为玩家当前朝向 §9§l" + "\n" +
                            "§bYaw <数值[-180,180)(单位 °)> §r- 设置水平视角角度 §9§l" +AngleLock.Yaw +"\n" +
                            "§bPitch <数值[-90,90](单位 °)> §r- 设置垂直视角角度 §9§l" + AngleLock.Pitch+"\n" +
                            "§bAutoBreak <状态(true/false)> §r- 是否自动破坏 §9§l" + AngleLock.AutoBreak+"\n" +
                            "§bLockYaw <状态(true/false)> §r- 是否锁定水平视角角度 §9§l" + AngleLock.LockYaw+"\n" +
                            "§bLockPitch <状态(true/false)> §r- 是否锁定垂直视角角度 §9§l" +AngleLock.LockPitch+"\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("wormcleaner")){
            if(theSetting.contentEquals("test")){
                if(!Functions.GetStatus("WormCleaner")){
                    DiamondS.SendMessage("§c请先打开§l WormCleaner");
                }else{
                    if(!WormCleaner.DuringClearer){
                        WormCleaner.PlayerRotation[0] = mc.thePlayer.rotationYaw;
                        WormCleaner.PlayerRotation[1] = mc.thePlayer.rotationPitch;
                        WormCleaner.RodSlot = mc.thePlayer.inventory.currentItem;
                        WormCleaner.tick=0;
                        WormCleaner.CurrectTime = 0;
                        mc.thePlayer.rotationYaw = WormCleaner.TargetRotation[0];
                        mc.thePlayer.rotationPitch = WormCleaner.TargetRotation[1];
                        if(WormCleaner.Quantity > 0){
                            WormCleaner.DuringClearer = true;
                            DiamondS.SendMessage("§a开始测试§l WormCleaner");
                        }else{
                            DiamondS.SendMessage("§c附近没有可清理的Worm");
                        }
                    }else{
                        WormCleaner.DuringClearer = false;
                        WormCleaner.tick=0;
                        WormCleaner.CurrectTime = 0;
                        mc.thePlayer.inventory.currentItem = WormCleaner.RodSlot;
                        mc.thePlayer.rotationYaw = WormCleaner.PlayerRotation[0];
                        mc.thePlayer.rotationPitch = WormCleaner.PlayerRotation[1];
                        DiamondS.SendMessage("§c已结束当前§l WormCleaner §r§c进程");
                    }
                }

                return;
            }
            if(theSetting.contentEquals("settoolslot")){
                WormCleaner.ToolSlot = mc.thePlayer.inventory.currentItem;
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
                if(Integer.parseInt(args[2]) <= 0){
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
                if(Integer.parseInt(args[2]) <= 0){
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
                if(Integer.parseInt(args[2]) <= 0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs WormCleaner SetDelay <整数 (延迟/ms)>");
                    return;
                }

                WormCleaner.Delay = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l Delay §r§a为:§b§l "+ WormCleaner.Delay);
                WormCleaner.SaveConfig();
                return;
            }
           Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                   "§bTest §r- 立即测试 WormCleaner 当前配置" + "\n" +
                           "§bSetToolSlot §r- 设置清虫工具为当前持有物品Slot §9§l" + WormCleaner.ToolSlot+"\n" +
                           "§bSetRot §r- 设置玩家当前朝向为Worm位置 §9§l[Yaw " +WormCleaner.TargetRotation[0] +",Pitch "+WormCleaner.TargetRotation[1] +"]\n" +
                           "§bSetTxtPos <整数(X轴位置)> <整数(Y轴位置)> §r- 设置文字位置 §9§l[" +WormCleaner.TextPosition[0]+","+WormCleaner.TextPosition[1]+ "]\n" +
                           "§bSetTimes <整数(次数)> §r- 设置工具使用次数 §9§l" + WormCleaner.Times+"\n" +
                           "§bSetMaxQty <整数(数量)> §r- 设置允许的最大worm数量 §9§l" + WormCleaner.MaxQuantity+"\n" +
                           "§bSetDelay <整数(单位/ms)>§r- 设置右击工具间隔时间 §9§l" +WormCleaner.Delay+ "\n\n" +
                           "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
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
            if(theSetting.contentEquals("colorful")){
                if(args.length < 4){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs PlayerFinder Colorful <状态(true/false)>");
                    return;
                }
                PlayerFinder.TextPosition[0] = Integer.parseInt(args[2]);
                PlayerFinder.TextPosition[1] = Integer.parseInt(args[3]);

                DiamondS.SendMessage("§a设置§l Colorful §r§a为 "+ PlayerFinder.colorful);
                PlayerFinder.SaveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bSetTxtPos <整数(X轴位置)> <整数(Y轴位置)> §r- 设置文字位置 §9§l["+PlayerFinder.TextPosition[0] + ","+PlayerFinder.TextPosition[1]+"]\n" +
                            "§bColorful <状态(true/false)> §r- 按照距离给文字上色 §9§l"+PlayerFinder.colorful +"\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("ghostblock")){
            if(theSetting.contentEquals("itemname")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs GhostBlock ItemName <文本(物品名称关键词)>");
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
                GhostBlock.ItemName = NewName;
                DiamondS.SendMessage("§a设置§l ItemName §r§a为：§r"+NewName);
                GhostBlock.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("type")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs GhostBlock Type <整数(1/2/3/4)>");
                    return;
                }
                int NewType = Integer.parseInt(args[2]);
                if(NewType != 1 && NewType != 2 && NewType != 3 && NewType != 4){
                    NewType = 1;
                }
                GhostBlock.type = NewType;
                DiamondS.SendMessage("§a设置§l Type §r§a为：§r" + NewType);
                GhostBlock.SaveConfig();
                return;
            }

            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                        "§bType <整数(1/2/3/4)> §r- 功能模式(1/2/3/4) §9§l"+GhostBlock.type+"\n" +
                                "§a  - 1: 按下按键后创建GhostBlock(按键在 §b[游戏 > 选项 > 控制] §a中修改)\n" +
                                "§a  - 2: 玩家蹲下时创建GhostBlock\n" +
                                "§a  - 3: 手持指定物品§b右§a键创建GhostBlock(可以修改§bItemName§a更换物品)\n" +
                                "§a  - 4: 手持指定物品§b左§a键创建GhostBlock(可以修改§bItemName§a更换物品)\n" +
                            "§bItemName <文本(物品名称关键词)> §r- 设置物品名称(可用部分关键词代替) §9§l"+ GhostBlock.ItemName+"\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("antiinvisible")){
            if(theSetting.contentEquals("showfel")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AntiInvisible ShowFel <状态(true/false)>");
                    return;
                }
                AntiInvisible.ShowFel = Boolean.parseBoolean(args[2].toLowerCase());
                DiamondS.SendMessage("§a设置§l ShowFel §r§a为：§r"+ AntiInvisible.ShowFel);
                AntiInvisible.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("showplayer")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AntiInvisible ShowPlayer <状态(true/false)>");
                    return;
                }
                AntiInvisible.ShowPlayer = Boolean.parseBoolean(args[2].toLowerCase());
                DiamondS.SendMessage("§a设置§l ShowPlayer §r§a为：§r"+ AntiInvisible.ShowPlayer);
                AntiInvisible.SaveConfig();
                return;
            }

            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bShowFel <状态(true/false)> §r- 是否显示隐藏的Fel §9§l" +AntiInvisible.ShowFel+"\n"+
                            "§bShowPlayer <状态(true/false)> §r- 是否显示隐藏的玩家 §9§l"+AntiInvisible.ShowPlayer+"\n\n"+
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("autofish")){
            if(theSetting.contentEquals("settxtpos")){
                if(args.length < 4){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish SetTxtPos <整数 (X轴位置)> <整数 (Y轴位置)>");
                    return;
                }
                AutoFish.TextPosition[0] = Integer.parseInt(args[2]);
                AutoFish.TextPosition[1] = Integer.parseInt(args[3]);

                DiamondS.SendMessage("§a设置§l TextPosition §r§a为 X:§b§l"+ AutoFish.TextPosition[0]+"§r§a Y:§b§l"+ AutoFish.TextPosition[1]);
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("maxtime")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish MaxTime <延迟(单位/s)>");
                    return;
                }
                if(Integer.parseInt(args[2]) <= 0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish MaxTime <延迟(单位/s)>");
                    return;
                }
                AutoFish.MaxTime = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l MaxTime §r§a为 §r" + args[2]+ "§r§as");
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("waittime")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish WaitTime <延迟(单位/s)>");
                    return;
                }
                if(Integer.parseInt(args[2]) <= 0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish WaitTime <延迟(单位/s)>");
                    return;
                }
                AutoFish.MaxWaitTime = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l WaitTime §r§a为 §b" + args[2]+ "§r§as");
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("setmovedu")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish SetMoveDu <持续时间(单位/ms)>");
                    return;
                }
                if(Integer.parseInt(args[2]) < 10){
                    DiamondS.SendMessage("§c指令错误，持续时间至少为 §l10ms");
                    return;
                }
                AutoFish.AutoMoveDU = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l AutoMoveDuration §r§a为 §b" + AutoFish.AutoMoveDU+ "§r§ams");
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("setmovetime")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish SetMoveTime <周期时间(单位/ms)>");
                    return;
                }
                if(Integer.parseInt(args[2]) < 4000){
                    DiamondS.SendMessage("§c指令错误，周期时间至少为 §l4000ms");
                    return;
                }
                AutoFish.AntiMacroTime = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l MoveTime §r§a为 §b" + args[2]+ "§r§ams");
                AutoFish.SaveConfig();
                AutoFish.switched();
                return;
            }
            if(theSetting.contentEquals("automove")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish AutoMove <状态(true/false)>");
                    return;
                }
                AutoFish.AutoMove = Boolean.parseBoolean(args[2]);
                DiamondS.SendMessage("§a设置§l AutoMove §r§a为 §b" + AutoFish.AutoMove);
                AutoFish.SaveConfig();
                AutoFish.antimacro.cancel();
                AutoFish.antimacro = new Timer();
                AutoFish.switched();
                return;
            }
            if(theSetting.contentEquals("amlronly")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish AMLROnly <状态(true/false)>");
                    return;
                }
                AutoFish.AutoMoveLROnly = Boolean.parseBoolean(args[2]);
                DiamondS.SendMessage("§a设置§l AutoMoveRightLeftOnly §r§a为 §b" + AutoFish.AutoMoveLROnly);
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("alwayssneak")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish AlwaysSneak <状态(true/false)>");
                    return;
                }
                AutoFish.AlwaysSneak = Boolean.parseBoolean(args[2]);
                DiamondS.SendMessage("§a设置§l AlwaysSneak §r§a为 §b" + AutoFish.AlwaysSneak);
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("alwaysw")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish AlwaysW <状态(true/false)>");
                    return;
                }
                AutoFish.AlwaysW = Boolean.parseBoolean(args[2]);
                DiamondS.SendMessage("§a设置§l AlwaysW §r§a为 §b" + AutoFish.AlwaysW);
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("rethrowdelay")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish RethrowDelay <延迟(单位/ms)>");
                    return;
                }
                if(Integer.parseInt(args[2]) <= 0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish RethrowDelay <延迟(单位/ms)>");
                    return;
                }
                AutoFish.RethrowDelay = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l RethrowDelay §r§a为 §b" + args[2]+ "§r§ams");
                AutoFish.SaveConfig();
                return;
            }
            if(theSetting.contentEquals("pulldelay")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish PullDelay <延迟(单位/ms)>");
                    return;
                }
                if(Integer.parseInt(args[2]) <= 0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs AutoFish PullDelay <延迟(单位/ms)>");
                    return;
                }
                AutoFish.PickDelay = Integer.parseInt(args[2]);
                DiamondS.SendMessage("§a设置§l PullDelay §r§a为 §b" + args[2]+ "§r§ams");
                AutoFish.SaveConfig();
                return;
            }
            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bSetTxtPos <整数 (X轴位置)> <整数 (Y轴位置)> §r- 设置文字位置  §9§l["+AutoFish.TextPosition[0]+","+AutoFish.TextPosition[1]+"]\n"+
                            "§8----------------------------------------\n"+
                            "§bMaxTime <延迟(单位/s)> §r- 设置超时时间 §9§l" +AutoFish.MaxTime+"\n"+
                            "§bWaitTime <延迟(单位/s)> §r- 设置重抛等待时间 §9§l"+AutoFish.MaxWaitTime +"\n"+
                            "§bRethrowDelay <延迟(单位/ms)> §r- 设置重抛延迟时间 §9§l" +AutoFish.RethrowDelay+"\n"+
                            "§bPullDelay <延迟(单位/ms)> §r- 设置收起鱼竿延迟时间 §9§l" +AutoFish.PickDelay+"\n"+
                            "§8----------------------------------------\n"+
                            "§bAutoMove <状态(true/false)> §r- 设置是否自动移动 §9§l" +AutoFish.AutoMove+"\n"+
                            "§bSetMoveDu <持续时间(单位/ms)> §r- 设置自动移动(行走)持续时间 §9§l" +AutoFish.AutoMoveDU+"\n"+
                            "§bSetMoveTime <周期时间(单位/ms)> §r- 设置自动移动周期时间 §9§l" +AutoFish.AntiMacroTime+"\n"+
                            "§bAMLROnly <状态(true/false)> §r- 设置自动移动(行走)是否只左右行走 §9§l" +AutoFish.AutoMoveLROnly+"\n"+
                            "§8----------------------------------------\n"+
                            "§bAlwaysSneak <状态(true/false)> §r- 设置是否强制潜行 §9§l" +AutoFish.AlwaysSneak+"\n"+
                            "§bAlwaysW <状态(true/false)> §r- 设置是否强制前进 §9§l" +AutoFish.AlwaysW+ "\n\n" +
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        if(theFunction.contentEquals("harpbot")){
            if(theSetting.contentEquals("delay")){
                if(args.length < 3){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs HarpBot Delay <数值(单位/ms)>");
                    return;
                }
                if(Integer.valueOf(args[2]) <=0){
                    DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fs HarpBot Delay <数值(单位/ms)>");
                    return;
                }
                HarpBot.delay = Integer.valueOf(args[2]);
                DiamondS.SendMessage("§a设置§l Delay §r§a为：§r§b"+ HarpBot.delay +"§ams");
                HarpBot.SaveConfig();
                return;
            }


            Message_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText(
                    "§bDelay <数值(单位/ms)> §r- 设置点击延迟 §9§l"+HarpBot.delay+"\n" +
                            "§7(每首歌需要的延迟可能不同，请自行调试)\n" +
                            "§7开发者使用的延迟:\n" +
//                            "§71.Hymn to the Joy 160ms\n" +
//                            "§72~8 60ms\n" +
//                            "§79~10 65ms\n" +
//                            "§711.La Vie en Rose 60ms\n" +
                            "§712.Through the Campfire 60ms\n" +
                            "§7其他的 150ms" + "\n\n"+
                            "§a用法为：§l/fs <功能名称> <功能设置项> <值1(可选)> <值2(可选)> ...")));
            Message.setChatStyle(Message_Style);
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS > §c未找到该设置项 ").appendSibling(Message));
            return;
        }
        DiamondS.SendMessage("§c该功能无自定义设置 或 功能不存在");
    }
}
