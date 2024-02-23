package com.notdiamond.diamonds.core;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.functions.Render.HidePlayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.awt.*;
import java.util.ArrayList;

public class Functions extends Component {
    public static ArrayList<DiamondSFunction> FunctionList = new ArrayList();
    public static ArrayList<DiamondSFunctionType> FunctionTypes = new ArrayList();

    public static void RegisterFunctionType(String FunctionTypeName){
        DiamondSFunctionType New = new DiamondSFunctionType(FunctionTypeName);
        Functions.FunctionTypes.add(New);
    }

    public static void RegisterFunction(String FunctionName, String FunctionType, boolean Status){
        DiamondSFunction New = new DiamondSFunction(FunctionName);
        New.SetStatus(Status);
        Functions.FunctionList.add(New);
        for(int i = 0; i <= Functions.FunctionTypes.size()-1;i++){
            if(Functions.FunctionTypes.get(i).GetName().toLowerCase().contentEquals(FunctionType.toLowerCase())){
                Functions.FunctionTypes.get(i).RegisterFunction(FunctionName);
            }
        }
    }


    public static void FunctionSwitch(String theFunction){
        theFunction = theFunction.toLowerCase();
        if(theFunction.contentEquals("hidearmor") && Functions.GetStatus("HidePlayers")){
            Functions.SetStatus("hideplayers",false);
            HidePlayers.Reload = false;
            DiamondS.SendMessage("§c检测到打开§lHideArmor§r§c，已自动关闭 §lHidePlayers");
        }
        if(theFunction.contentEquals("hideplayers") && Functions.GetStatus("HideArmor")){
            Functions.SetStatus("hidearmor",false);
            DiamondS.SendMessage("§c检测到打开§lHideArmor§r§c，已自动关闭 §lHidePlayers");
        }

        Functions.SetStatus(theFunction, !Functions.GetStatus(theFunction));

        //附加↓
        if(theFunction.contentEquals("carryhelper")){DiamondS.TradeList.clear();}
        if(theFunction.contentEquals("adclear") && Functions.GetStatus("Debug.MsgCopy")){
            Functions.SetStatus("ADClear",false);
            DiamondS.SendMessage("§c检测到打开§lDebug.MsgCopy§r§c，已自动关闭 §lADClear");
        }
        if(theFunction.contentEquals("sneak")){
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(),false);
        }
        if(theFunction.contentEquals("sprint")){
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(),false);
        }
        if(theFunction.contentEquals("autojump")){
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(),false);
        }
        //附加↑
        Config.saveConfig();
    }

    public static boolean GetStatus(String FunctionName){
        for(int i = 0;i<=Functions.FunctionList.size()-1;i++){
            if(Functions.FunctionList.get(i).Name.toLowerCase().contentEquals(FunctionName.toLowerCase())){
                return Functions.FunctionList.get(i).Status;
            }
        }
        return false;
    }
    public static boolean SetStatus(String FunctionName,boolean NewStatus){

        for(int i = 0;i<=Functions.FunctionList.size()-1;i++){
            if(Functions.FunctionList.get(i).Name.toLowerCase().contentEquals(FunctionName.toLowerCase())){
                Functions.FunctionList.get(i).SetStatus(NewStatus);
                if(Minecraft.getMinecraft().thePlayer != null){
                    if(FunctionName.toLowerCase().contentEquals("hideplayers") && HidePlayers.Reload){
                        return true;
                    }
                    if(NewStatus){
                        DiamondS.SendMessage("§a§l" + Functions.FunctionList.get(i).Name + " §r§a已开启");
                    }else{
                        DiamondS.SendMessage("§c§l" + Functions.FunctionList.get(i).Name + " §r§c已关闭");
                    }
                }
                return true;
            }
        }
        return false;
    }

}
