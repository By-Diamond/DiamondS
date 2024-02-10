package com.notdiamond.diamonds;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.notdiamond.diamonds.commands.FunctionSettings;
import com.notdiamond.diamonds.commands.FunctionSwitch;
import com.notdiamond.diamonds.commands.WardrobeHelperCommand;
import com.notdiamond.diamonds.core.DiamondSFunction;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.functions.*;
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
    public static final String VERSION = "1.2.0";
    public static final String MODNAME = "DiamondS";
    public static ArrayList<String> TradeList = new ArrayList();
    public static ArrayList<DiamondSFunction> FunctionList = new ArrayList();
    public static int tempint;
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("DiamondS >> Mod 已开始加载");
        MinecraftForge.EVENT_BUS.register(new CarryHelper());
        MinecraftForge.EVENT_BUS.register(new ADClear());
        MinecraftForge.EVENT_BUS.register(new PartyHelper());
        MinecraftForge.EVENT_BUS.register(new HidePlayers());
        MinecraftForge.EVENT_BUS.register(new WardrobeHelper());

        Functions.RegisterFunction("PartyHelper",true);
        Functions.RegisterFunction("ADClear",true);
        Functions.RegisterFunction("CarryHelper",false);
        Functions.RegisterFunction("HidePlayers",false);
        Functions.RegisterFunction("WardrobeHelper",true);

        ClientCommandHandler.instance.registerCommand(new menu());
        ClientCommandHandler.instance.registerCommand(new FunctionSwitch());
        ClientCommandHandler.instance.registerCommand(new FunctionSettings());
        ClientCommandHandler.instance.registerCommand(new WardrobeHelperCommand());

        //添加功能的步骤：
        //注册功能 Functions.RegisterFunction("PartyHelper",false);
        //注册事件 MinecraftForge.EVENT_BUS.register(new PartyHelper());
        //注册功能设置 com.notdiamond.diamonds.commands.FunctionSettings
    }

    public static void SendMessage(String Message){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b§lDiamondS >§r " + Message));
    }

    public static boolean IsOnSkyBlock() {
        try {
            return Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0) != null ? ChatFormatting.stripFormatting(Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0).getDisplayName()).contains("SKYBLOCK") : ChatFormatting.stripFormatting(Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()).contains("SKYBLOCK");
        } catch (Exception var1) {
            return false;
        }
    }
}
