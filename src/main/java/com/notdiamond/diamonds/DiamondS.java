package com.notdiamond.diamonds;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.notdiamond.diamonds.commands.FunctionSettings;
import com.notdiamond.diamonds.commands.FunctionSwitch;
import com.notdiamond.diamonds.commands.WardrobeHelperCommand;
import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.core.SmoothRotation.SmoothRotation;
import com.notdiamond.diamonds.functions.Chat.ChannelChanger.ChannelChanger;
import com.notdiamond.diamonds.functions.Render.HUD;
import com.notdiamond.diamonds.core.KeyLoader;
import com.notdiamond.diamonds.functions.Chat.ChatClass;
import com.notdiamond.diamonds.functions.Macro.AutoPurchasePass;
import com.notdiamond.diamonds.functions.Macro.HarpBot;
import com.notdiamond.diamonds.functions.Other.Rat;
import com.notdiamond.diamonds.functions.Render.AntiInvisible;
import com.notdiamond.diamonds.functions.Dungeon.GhostBlock;
import com.notdiamond.diamonds.functions.Macro.AutoFish;
import com.notdiamond.diamonds.functions.Macro.PlayerFinder.PlayerFinder;
import com.notdiamond.diamonds.functions.Macro.WormCleaner;
import com.notdiamond.diamonds.functions.Movement.MovementClass;
import com.notdiamond.diamonds.functions.Player.AngleLock;
import com.notdiamond.diamonds.functions.Player.WardrobeHelper;
import com.notdiamond.diamonds.functions.Render.HideArmor;
import com.notdiamond.diamonds.functions.Render.HideFallingBlock;
import com.notdiamond.diamonds.functions.Render.HidePlayers;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import com.notdiamond.diamonds.commands.menu;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


@Mod(
        modid = DiamondS.MODID,
        version = DiamondS.VERSION,
        name = DiamondS.MODNAME,
        acceptedMinecraftVersions = "[1.8.9]",
        clientSideOnly = true
)
public class DiamondS extends Component {
    public static final String MODID = "diamonds";
    public static final String VERSION = "1.6.0";
    public static final String MODNAME = "DiamondS";
    public static String PLAYERNAME = "";
    public static ArrayList<String> TradeList = new ArrayList();
    public static int tempint;
    public static Minecraft mc = null;

    @EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
//        if(this.CheckUpdate()){
            System.out.println("DiamondS >> Mod 已开始加载");

            mc = Minecraft.getMinecraft();

            new KeyLoader();
            ClientRegistry.registerKeyBinding(KeyLoader.HUDUP);
            ClientRegistry.registerKeyBinding(KeyLoader.HUDDown);
            ClientRegistry.registerKeyBinding(KeyLoader.HUDEnter);
            ClientRegistry.registerKeyBinding(KeyLoader.HUDBack);
            ClientRegistry.registerKeyBinding(KeyLoader.FunctionSet);
            ClientRegistry.registerKeyBinding(KeyLoader.GhostBlock);
            ClientRegistry.registerKeyBinding(KeyLoader.Sprint);

            MinecraftForge.EVENT_BUS.register(new ChatClass());
            MinecraftForge.EVENT_BUS.register(new HidePlayers());
            MinecraftForge.EVENT_BUS.register(new WardrobeHelper());
            MinecraftForge.EVENT_BUS.register(new MovementClass());
            MinecraftForge.EVENT_BUS.register(new HideArmor());
            MinecraftForge.EVENT_BUS.register(new HUD());
            MinecraftForge.EVENT_BUS.register(new AngleLock());
            MinecraftForge.EVENT_BUS.register(new WormCleaner());
            MinecraftForge.EVENT_BUS.register(new PlayerFinder());
            MinecraftForge.EVENT_BUS.register(new GhostBlock());
            MinecraftForge.EVENT_BUS.register(new AntiInvisible());
            MinecraftForge.EVENT_BUS.register(new AutoFish());
            MinecraftForge.EVENT_BUS.register(new Rat());
            MinecraftForge.EVENT_BUS.register(new HideFallingBlock());
            MinecraftForge.EVENT_BUS.register(new AutoPurchasePass());
            MinecraftForge.EVENT_BUS.register(new HarpBot());
            MinecraftForge.EVENT_BUS.register(new SmoothRotation());
            MinecraftForge.EVENT_BUS.register(new ChannelChanger());

            Functions.RegisterFunctionType("Chat");
            Functions.RegisterFunctionType("Dungeon");
            Functions.RegisterFunctionType("Macro");
            Functions.RegisterFunctionType("Movement");
            Functions.RegisterFunctionType("Player");
            Functions.RegisterFunctionType("Render");
            Functions.RegisterFunctionType("Other");

            Functions.RegisterFunction("ChannelChanger","Chat",true);
            Functions.RegisterFunction("PartyHelper","Chat",true);
            Functions.RegisterFunction("ADClear","Chat",true);
            Functions.RegisterFunction("CarryHelper","Chat",false);
            Functions.RegisterFunction("IgnoreShow","Chat",true);

            Functions.RegisterFunction("GhostBlock","Dungeon",false);

            Functions.RegisterFunction("AutoFish","Macro",false);
            Functions.RegisterFunction("AutoPurchasePass","Macro",false);
            Functions.RegisterFunction("HarpBot","Macro",false);
            Functions.RegisterFunction("WormCleaner","Macro",false);
            Functions.RegisterFunction("PlayerFinder","Macro",false);

            Functions.RegisterFunction("Sprint","Movement",true);
            Functions.RegisterFunction("Sneak","Movement",false);
            Functions.RegisterFunction("AutoJump","Movement",false);

            Functions.RegisterFunction("WardrobeHelper","Player",true);
            Functions.RegisterFunction("AngleLock","Player",false);
            Functions.RegisterFunction("NickName","Player",false);

            Functions.RegisterFunction("AntiInvisible","Render",true);
            Functions.RegisterFunction("HidePlayers","Render",false);
            Functions.RegisterFunction("HideArmor","Render",false);
            Functions.RegisterFunction("HideFallingBlock","Render",true);
            Functions.RegisterFunction("HUD","Render",true);

            Functions.RegisterFunction("Rat","Other",false);

            ClientCommandHandler.instance.registerCommand(new menu());
            ClientCommandHandler.instance.registerCommand(new FunctionSwitch());
            ClientCommandHandler.instance.registerCommand(new FunctionSettings());
            ClientCommandHandler.instance.registerCommand(new WardrobeHelperCommand());

            Config.SetConfig(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/config/DiamondSFunctions.cfg");
            Config.LoadConfig();

//        }else{
//            JOptionPane.showMessageDialog(this, "DiamondS Mod 未更新至最新版，已停止工作\n请前往下载最新版本\n当前版本："+DiamondS.VERSION+"\n\n- QQ群：662811819\n- Github:https://github.com/By-Diamond/DiamondS/releases\n\n感谢你的使用:D\n请前往更新，使用新版本后祝你每天掉 Handle", "DiamondS 更新提示:", JOptionPane.WARNING_MESSAGE);
//            java.awt.Desktop.getDesktop().browse(URI.create("http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=8aUa5Y1M8h5mgQe-qZfcccct0nJoSNTN&authKey=Hjtm6lJgd5CaAJvVsTzmmRrROildYajinH7nnGNECzv5kJktk3O%2BCfLO2q%2BtZirn&noverify=0&group_code=662811819"));
//        }

        //添加功能的步骤：
        //注册功能 Functions.RegisterFunction("PartyHelper",false);
        //注册事件 MinecraftForge.EVENT_BUS.register(new PartyHelper());
        //注册功能设置 com.notdiamond.diamonds.commands.FunctionSettings
    }


    public static void SendMessage(String Message){
        if(mc.thePlayer != null){
            mc.thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS >§r " + Message));
        }
    }

    public static boolean IsOnSkyBlock() {
        try {
            return mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0) != null ? ChatFormatting.stripFormatting(mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0).getDisplayName()).contains("SKYBLOCK") : ChatFormatting.stripFormatting(mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()).contains("SKYBLOCK");
        } catch (Exception var1) {
            return false;
        }
    }
    public static boolean CheckUpdate() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://result.eolink.com/2qIHAVv78b113503d81e33d70b70dedf1e4d599afd0447a?uri=/diamonds.html");
        CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result.contentEquals(DiamondS.VERSION);
            }
        return false;
    }
}
