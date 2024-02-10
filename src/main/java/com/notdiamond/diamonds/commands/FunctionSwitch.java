package com.notdiamond.diamonds.commands;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class FunctionSwitch extends CommandBase {

    @Override
    public String getCommandName() {
        return "fc";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fc <功能名称> 切换功能状态";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }


    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length <= 0){
            DiamondS.SendMessage("§c指令错误，正确的用法为：§l/fc <功能名称>");
            return;
        }
        String theFunction = args[0].toLowerCase();
        if(Functions.GetStatus(theFunction)){
            Functions.SetStatus(theFunction, false);
        }else{
            Functions.SetStatus(theFunction, true);
        }
        //附加↓
        if(theFunction.contentEquals("carryhelper")){DiamondS.TradeList.clear();}
        if(theFunction.contentEquals("adclear") && Functions.GetStatus("Debug.MsgCopy")){
            Functions.SetStatus("ADClear",false);
            DiamondS.SendMessage("§c检测到打开§lDebug.MsgCopy§r§c，已自动关闭 §lADClear");
        }
        //附加↑
        return;
    }
}
