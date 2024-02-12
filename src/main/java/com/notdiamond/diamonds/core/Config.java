package com.notdiamond.diamonds.core;


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
                    if(Functions.FunctionList.get(i).Name.contentEquals("HidePlayers")){
                        Functions.SetStatus("HidePlayers",false);
                    }else{
                        String Status = Config.prop.getProperty("Functions."+Functions.FunctionList.get(i).Name);
                        if(Status != null && !(Status.contentEquals(""))){
                            Functions.SetStatus(Functions.FunctionList.get(i).Name,Boolean.parseBoolean(Status));
                        }
                    }
                }
            }
    }

        public static void saveConfig() {
            try {
                for(int i = 0;i<=Functions.FunctionList.size()-1;i++){
                    Config.prop.setProperty("Functions."+Functions.FunctionList.get(i).Name, String.valueOf(Functions.GetStatus(Functions.FunctionList.get(i).Name)));
                }
                FileOutputStream fos = new FileOutputStream(Config.fileName);
                Config.prop.store(fos, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
