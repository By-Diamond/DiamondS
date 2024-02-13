package com.notdiamond.diamonds;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.notdiamond.diamonds.commands.FunctionSettings;
import com.notdiamond.diamonds.commands.FunctionSwitch;
import com.notdiamond.diamonds.commands.WardrobeHelperCommand;
import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.core.HUD;
import com.notdiamond.diamonds.core.KeyLoader;
import com.notdiamond.diamonds.functions.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import com.notdiamond.diamonds.commands.menu;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

@Mod(
        modid = DiamondS.MODID,
        name = DiamondS.MODNAME,
        version = DiamondS.VERSION,
        acceptedMinecraftVersions = "[1.8.9]",
        clientSideOnly = true
)
public class DiamondS extends Component {
    public static final String MODID = "diamonds";
    public static final String VERSION = "1.3.0";
    public static final String MODNAME = "DiamondS";
    public static String PLAYERNAME = "";
    public static ArrayList<String> TradeList = new ArrayList();
    public static int tempint;

    @EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        if(this.CheckUpdata()){
            System.out.println("DiamondS >> Mod 已开始加载");

            new KeyLoader();
            ClientRegistry.registerKeyBinding(KeyLoader.HUDUP);
            ClientRegistry.registerKeyBinding(KeyLoader.HUDDown);
            ClientRegistry.registerKeyBinding(KeyLoader.HUDEnter);
            ClientRegistry.registerKeyBinding(KeyLoader.HUDBack);

            MinecraftForge.EVENT_BUS.register(new ChatClass());
            MinecraftForge.EVENT_BUS.register(new HidePlayers());
            MinecraftForge.EVENT_BUS.register(new WardrobeHelper());
            MinecraftForge.EVENT_BUS.register(new MovementClass());
            MinecraftForge.EVENT_BUS.register(new HideArmor());
            MinecraftForge.EVENT_BUS.register(new HUD());

            Functions.RegisterFunctionType("Chat");
            Functions.RegisterFunctionType("Render");
            Functions.RegisterFunctionType("Player");

            Functions.RegisterFunction("PartyHelper","Chat",true);
            Functions.RegisterFunction("ADClear","Chat",true);
            Functions.RegisterFunction("CarryHelper","Chat",false);
            Functions.RegisterFunction("IgnoreShow","Chat",true);
            Functions.RegisterFunction("HidePlayers","Render",false);
            Functions.RegisterFunction("HideArmor","Render",false);
            Functions.RegisterFunction("HUD","Render",true);
            Functions.RegisterFunction("WardrobeHelper","Player",true);
            Functions.RegisterFunction("Sprint","Player",true);
            Functions.RegisterFunction("Sneak","Player",false);
            Functions.RegisterFunction("NickName","Player",false);

            ClientCommandHandler.instance.registerCommand(new menu());
            ClientCommandHandler.instance.registerCommand(new FunctionSwitch());
            ClientCommandHandler.instance.registerCommand(new FunctionSettings());
            ClientCommandHandler.instance.registerCommand(new WardrobeHelperCommand());

            Config.SetConfig(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/config/DiamondSFunctions.cfg");

            Config.LoadConfig();
        }else{
            JOptionPane.showMessageDialog(this, "DiamondS Mod 未更新至最新版，已停止工作\n请前往下载最新版本\n- QQ群：662811819\n- Github:https://github.com/By-Diamond/DiamondS/releases\n\n感谢你的使用:D\n请前往更新，使用新版本后祝你每天掉 Handle", "DiamondS 更新提示:", JOptionPane.WARNING_MESSAGE);
            java.awt.Desktop.getDesktop().browse(URI.create("http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=8aUa5Y1M8h5mgQe-qZfcccct0nJoSNTN&authKey=Hjtm6lJgd5CaAJvVsTzmmRrROildYajinH7nnGNECzv5kJktk3O%2BCfLO2q%2BtZirn&noverify=0&group_code=662811819"));
        }

        //添加功能的步骤：
        //注册功能 Functions.RegisterFunction("PartyHelper",false);
        //注册事件 MinecraftForge.EVENT_BUS.register(new PartyHelper());
        //注册功能设置 com.notdiamond.diamonds.commands.FunctionSettings
    }

    public static void SendMessage(String Message){
        if(Minecraft.getMinecraft().thePlayer != null){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS >§r " + Message));
        }
    }

    public static boolean IsOnSkyBlock() {
        try {
            return Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0) != null ? ChatFormatting.stripFormatting(Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0).getDisplayName()).contains("SKYBLOCK") : ChatFormatting.stripFormatting(Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()).contains("SKYBLOCK");
        } catch (Exception var1) {
            return false;
        }
    }
    private boolean CheckUpdata() throws IOException {
        String urlStr = "https://result.eolink.com/2qIHAVv78b113503d81e33d70b70dedf1e4d599afd0447a?uri=/diamonds.html"; // 要访问的网址

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
            String content = contentBuilder.toString();
            reader.close();
            return content.contentEquals(DiamondS.VERSION);
        } else {
            return false;
        }
    }
}
