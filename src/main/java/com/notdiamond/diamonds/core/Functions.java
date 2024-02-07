package com.notdiamond.diamonds.core;

import com.notdiamond.diamonds.DiamondS;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;

public class Functions {
    public static void RegisterFunction(String FunctionName,boolean Status){
        DiamondSFunction New = new DiamondSFunction(FunctionName);
        New.SetStatus(Status);
        DiamondS.FunctionList.add(New);
    }

    public static boolean GetStatus(String FunctionName){
        for(int i = 0;i<=DiamondS.FunctionList.size()-1;i++){
            if(DiamondS.FunctionList.get(i).Name.toLowerCase().contentEquals(FunctionName.toLowerCase())){
                return DiamondS.FunctionList.get(i).Status;
            }
        }
        return false;
    }
    public static boolean SetStatus(String FunctionName,boolean NewStatus){

        for(int i = 0;i<=DiamondS.FunctionList.size()-1;i++){
            if(DiamondS.FunctionList.get(i).Name.toLowerCase().contentEquals(FunctionName.toLowerCase())){
                DiamondS.FunctionList.get(i).SetStatus(NewStatus);
                if(NewStatus == true){
                    DiamondS.SendMessage("§a§l" + DiamondS.FunctionList.get(i).Name + " §r§a已开启");
                }else{
                    DiamondS.SendMessage("§c§l" + DiamondS.FunctionList.get(i).Name + " §r§c已关闭");
                }
                return true;
            }
        }
        return false;
    }

}
