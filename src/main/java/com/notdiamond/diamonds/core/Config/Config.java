package com.notdiamond.diamonds.core.Config;


import com.notdiamond.diamonds.core.Function.Functions;
import com.notdiamond.diamonds.functions.Dungeon.CarryHelper;
import com.notdiamond.diamonds.functions.Macro.HarpBot;
import com.notdiamond.diamonds.functions.Render.AntiInvisible;
import com.notdiamond.diamonds.functions.Dungeon.GhostBlock;
import com.notdiamond.diamonds.functions.Macro.AutoFish;
import com.notdiamond.diamonds.functions.Macro.PlayerFinder.PlayerFinder;
import com.notdiamond.diamonds.functions.Macro.WormCleaner;
import com.notdiamond.diamonds.functions.Player.AngleLock;
import com.notdiamond.diamonds.functions.Chat.ChatClass;
import com.notdiamond.diamonds.functions.Player.WardrobeHelper;
import com.notdiamond.diamonds.functions.Render.HUD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
        public static Properties prop = new Properties();
        public static String fileName = "";

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
                for(int i = 0; i<= Functions.FunctionList.size()-1; i++){
                    String Name = Functions.FunctionList.get(i).Name;
                    if(Name.contentEquals("HarpBot") || Name.contentEquals("Rat") || Name.contentEquals("AutoFish") ||Name.contentEquals("HidePlayers") || Name.contentEquals("HideArmor") || Name.contentEquals("AngleLock")){
                        Functions.SetStatusWithout(Name,false);
                    }else{
                        String Status = Config.prop.getProperty("Functions."+Name);
                        if(Status != null && !(Status.contentEquals(""))){
                            Functions.SetStatusWithout(Name,Boolean.parseBoolean(Status));
                        }
                    }
                }
                HUD.X = Integer.parseInt(Config.prop.getProperty("HUD.X", "5"));
                HUD.Y = Integer.parseInt(Config.prop.getProperty("HUD.Y", "10"));
                ChatClass.NickName_Name = Config.prop.getProperty("NickName.Name", "§b[You]");
                WardrobeHelper.Delay = Integer.parseInt(Config.prop.getProperty("WardrobeHelper.Delay", "500"));
                AngleLock.Pitch = Integer.parseInt(Config.prop.getProperty("AngleLock.Pitch", "0"));
                AngleLock.Yaw = Integer.parseInt(Config.prop.getProperty("AngleLock.Yaw", "90"));
                AngleLock.AutoBreak = Boolean.parseBoolean(Config.prop.getProperty("AngleLock.AutoBreak","false"));
                AngleLock.LockPitch = Boolean.parseBoolean(Config.prop.getProperty("AngleLock.LockPitch","true"));
                AngleLock.LockYaw = Boolean.parseBoolean(Config.prop.getProperty("AngleLock.LockYaw","true"));
                CarryHelper.LoadConfig();
                WormCleaner.LoadConfig();
                PlayerFinder.LoadConfig();
                GhostBlock.LoadConfig();
                AntiInvisible.LoadConfig();
                AutoFish.LoadConfig();
                HarpBot.LoadConfig();
            }
    }

        public static void saveConfig() {
            try {
                for(int i = 0;i<=Functions.FunctionList.size()-1;i++){
                    Config.prop.setProperty("Functions."+Functions.FunctionList.get(i).Name, String.valueOf(Functions.GetStatus(Functions.FunctionList.get(i).Name)));
                }
                Config.prop.setProperty("HUD.X", String.valueOf(HUD.X));
                Config.prop.setProperty("HUD.Y", String.valueOf(HUD.Y));
                Config.prop.setProperty("NickName.Name", ChatClass.NickName_Name);
                Config.prop.setProperty("WardrobeHelper.Delay", String.valueOf(WardrobeHelper.Delay));
                Config.prop.setProperty("AngleLock.Pitch", String.valueOf(AngleLock.Pitch));
                Config.prop.setProperty("AngleLock.Yaw", String.valueOf(AngleLock.Yaw));
                Config.prop.setProperty("AngleLock.AutoBreak", String.valueOf(AngleLock.AutoBreak));
                Config.prop.setProperty("AngleLock.LockYaw", String.valueOf(AngleLock.LockYaw));
                Config.prop.setProperty("AngleLock.LockPitch", String.valueOf(AngleLock.LockPitch));
                CarryHelper.SaveConfig();
                WormCleaner.SaveConfig();
                PlayerFinder.SaveConfig();
                GhostBlock.SaveConfig();
                AntiInvisible.SaveConfig();
                AutoFish.SaveConfig();
                HarpBot.SaveConfig();
                FileOutputStream fos = new FileOutputStream(Config.fileName);
                Config.prop.store(fos, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
