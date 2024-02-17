package com.notdiamond.diamonds.core;


import com.notdiamond.diamonds.functions.AngleLock;
import com.notdiamond.diamonds.functions.ChatClass;
import com.notdiamond.diamonds.functions.WardrobeHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
        private static Properties prop = new Properties();
        private static String fileName = "";

        public static void SetConfig(String fileName) {
            Config.fileName = fileName;
            try {
                new File(fileName).createNewFile();
                Config.prop.load(new FileInputStream(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void LoadConfig() {
            if(prop.isEmpty()){
                saveConfig();
            }else{
                for(int i = 0;i<=Functions.FunctionList.size()-1;i++){
                    String Name = Functions.FunctionList.get(i).Name;
                    if(Name.contentEquals("HidePlayers") || Name.contentEquals("HideArmor") || Name.contentEquals("AngleLock")){
                        Functions.SetStatus(Name,false);
                    }else{
                        String Status = Config.prop.getProperty("Functions."+Name);
                        if(Status != null && !(Status.contentEquals(""))){
                            Functions.SetStatus(Name,Boolean.parseBoolean(Status));
                        }
                    }
                }
                HUD.X = Integer.parseInt(Config.prop.getProperty("HUD.X", "5"));
                HUD.Y = Integer.parseInt(Config.prop.getProperty("HUD.Y", "10"));
                ChatClass.CarryHelper_IsAutoWarp = Boolean.parseBoolean(Config.prop.getProperty("CarryHelper.AutoWarp", "false"));
                ChatClass.CarryHelper_IsAutoMessage = Boolean.parseBoolean(Config.prop.getProperty("CarryHelper.AutoMessage", "false"));
                ChatClass.CarryHelper_AutoMessage = Config.prop.getProperty("CarryHelper.Msg", "Pay at sand");
                ChatClass.NickName_Name = Config.prop.getProperty("NickName.Name", "§b[You]");
                WardrobeHelper.Delay = Integer.parseInt(Config.prop.getProperty("WardrobeHelper.Delay", "500"));
                AngleLock.Pitch = Integer.parseInt(Config.prop.getProperty("AngleLock.Pitch", "0"));
                AngleLock.Yaw = Integer.parseInt(Config.prop.getProperty("AngleLock.Yaw", "90"));
                AngleLock.AutoBreak = Boolean.parseBoolean(Config.prop.getProperty("AngleLock.AutoBreak","false"));
                AngleLock.LockPitch = Boolean.parseBoolean(Config.prop.getProperty("AngleLock.LockPitch","true"));
                AngleLock.LockYaw = Boolean.parseBoolean(Config.prop.getProperty("AngleLock.LockYaw","true"));

            }
    }

        public static void saveConfig() {
            try {
                for(int i = 0;i<=Functions.FunctionList.size()-1;i++){
                    Config.prop.setProperty("Functions."+Functions.FunctionList.get(i).Name, String.valueOf(Functions.GetStatus(Functions.FunctionList.get(i).Name)));
                }
                Config.prop.setProperty("HUD.X", String.valueOf(HUD.X));
                Config.prop.setProperty("HUD.Y", String.valueOf(HUD.Y));
                Config.prop.setProperty("CarryHelper.AutoWarp", String.valueOf(ChatClass.CarryHelper_IsAutoWarp));
                Config.prop.setProperty("CarryHelper.AutoMessage", String.valueOf(ChatClass.CarryHelper_IsAutoMessage));
                Config.prop.setProperty("CarryHelper.Msg", ChatClass.CarryHelper_AutoMessage);
                Config.prop.setProperty("NickName.Name", ChatClass.NickName_Name);
                Config.prop.setProperty("WardrobeHelper.Delay", String.valueOf(WardrobeHelper.Delay));
                Config.prop.setProperty("AngleLock.Pitch", String.valueOf(AngleLock.Pitch));
                Config.prop.setProperty("AngleLock.Yaw", String.valueOf(AngleLock.Yaw));
                Config.prop.setProperty("AngleLock.AutoBreak", String.valueOf(AngleLock.AutoBreak));
                Config.prop.setProperty("AngleLock.LockYaw", String.valueOf(AngleLock.LockYaw));
                Config.prop.setProperty("AngleLock.LockPitch", String.valueOf(AngleLock.LockPitch));


                FileOutputStream fos = new FileOutputStream(Config.fileName);
                Config.prop.store(fos, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
