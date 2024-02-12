package com.notdiamond.diamonds.core;

import com.notdiamond.diamonds.DiamondS;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class Functions {
    public static ArrayList<DiamondSFunction> FunctionList = new ArrayList();
    public static void RegisterFunction(String FunctionName,boolean Status){
        DiamondSFunction New = new DiamondSFunction(FunctionName);
        New.SetStatus(Status);
        Functions.FunctionList.add(New);
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
                    if(NewStatus == true){
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
