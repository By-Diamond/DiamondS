package com.notdiamond.diamonds;

import com.notdiamond.diamonds.commands.FunctionSettings;
import com.notdiamond.diamonds.commands.FunctionSwitch;
import com.notdiamond.diamonds.core.DiamondSFunction;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.functions.ADClear;
import com.notdiamond.diamonds.functions.CarryHelper;
import com.notdiamond.diamonds.functions.HidePlayers;
import com.notdiamond.diamonds.functions.PartyHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import com.notdiamond.diamonds.commands.menu;

import java.util.ArrayList;

@Mod(
        modid = DiamondS.MODID,
        name = DiamondS.MODNAME,
        version = DiamondS.VERSION,
        acceptedMinecraftVersions = "[1.8.9]",
        clientSideOnly = true
)
public class DiamondS
{
    public static final String MODID = "diamonds";
    public static final String VERSION = "1.1.0";
    public static final String MODNAME = "DiamondS";
    public static ArrayList<String> TradeList = new ArrayList();
    public static ArrayList<DiamondSFunction> FunctionList = new ArrayList();
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("DiamondS >> Mod 已开始加载");
        MinecraftForge.EVENT_BUS.register(new CarryHelper());
        MinecraftForge.EVENT_BUS.register(new ADClear());
        MinecraftForge.EVENT_BUS.register(new PartyHelper());
        MinecraftForge.EVENT_BUS.register(new HidePlayers());

        Functions.RegisterFunction("PartyHelper",true);
        Functions.RegisterFunction("ADClear",true);
        Functions.RegisterFunction("CarryHelper",false);
        Functions.RegisterFunction("HidePlayers",false);

        ClientCommandHandler.instance.registerCommand(new menu());
        ClientCommandHandler.instance.registerCommand(new FunctionSwitch());
        ClientCommandHandler.instance.registerCommand(new FunctionSettings());

        //添加功能的步骤：
        //注册功能 Functions.RegisterFunction("PartyHelper",false);
        //注册事件 MinecraftForge.EVENT_BUS.register(new PartyHelper());
        //注册功能设置 com.notdiamond.diamonds.commands.FunctionSettings
    }

    @EventHandler
    public void WorldLoad(WorldEvent.Load event){

    }

    public static void SendMessage(String Message){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS >§r " + Message));
    }

}
