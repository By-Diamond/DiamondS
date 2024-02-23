package com.notdiamond.diamonds.functions.Macro;

import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import static com.notdiamond.diamonds.DiamondS.mc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WormCleaner {
    public static boolean DuringClearer = false;
    static int RodSlot = 0;
    static Float[] PlayerRotation = new Float[2];
    static int Quantity = 0;
    static int CurrectTime = 0;
    static int tick = 0;

    public static int ToolSlot = 0;
    public static int[] TextPosition = new int[2];
    public static Float[] TargetRotation = new Float[2];
    public static int Times = 4;
    public static int MaxQuantity = 45;
    public static int Delay = 2500;


    public static void LoadConfig(){
        ToolSlot = Integer.parseInt(Config.prop.getProperty("WormCleaner.ToolSlot","0"));
        TextPosition[0] = Integer.parseInt(Config.prop.getProperty("WormCleaner.TextX","100"));
        TextPosition[1] = Integer.parseInt(Config.prop.getProperty("WormCleaner.TextY","10"));
        TargetRotation[0] = Float.valueOf(Config.prop.getProperty("WormCleaner.TargetYaw","0"));
        TargetRotation[1] = Float.valueOf(Config.prop.getProperty("WormCleaner.TargetPitch","0"));
        Times = Integer.parseInt(Config.prop.getProperty("WormCleaner.Times","4"));
        MaxQuantity = Integer.parseInt(Config.prop.getProperty("WormCleaner.MaxQuantity","45"));
        Delay = Integer.parseInt(Config.prop.getProperty("WormCleaner.Delay","2500"));
    }

    public static void SaveConfig() throws IOException {
         Config.prop.setProperty("WormCleaner.ToolSlot", String.valueOf(ToolSlot));
         Config.prop.setProperty("WormCleaner.TextX", String.valueOf(TextPosition[0]));
         Config.prop.setProperty("WormCleaner.TextY", String.valueOf(TextPosition[1]));
         Config.prop.setProperty("WormCleaner.TargetYaw", String.valueOf(TargetRotation[0]));
         Config.prop.setProperty("WormCleaner.TargetPitch", String.valueOf(TargetRotation[1]));
         Config.prop.setProperty("WormCleaner.Times", String.valueOf(Times));
         Config.prop.setProperty("WormCleaner.MaxQuantity", String.valueOf(MaxQuantity));
         Config.prop.setProperty("WormCleaner.Delay", String.valueOf(Delay));
         FileOutputStream fos = new FileOutputStream(Config.fileName);
         Config.prop.store(fos, null);
    }


    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(Functions.GetStatus("WormCleaner")){
            if(mc.theWorld != null && mc.thePlayer != null){
                double x = mc.thePlayer.posX;
                double y = mc.thePlayer.posY;
                double z = mc.thePlayer.posZ;
                List<EntitySilverfish> entityList = mc.theWorld.getEntitiesWithinAABB(EntitySilverfish.class, new AxisAlignedBB(x - (20 / 2d), y - (20 / 2d), z - (20 / 2d), x + (20 / 2d), y + (20 / 2d), z + (20 / 2d)), null);
                Quantity = entityList.size();
                if (!DuringClearer) {
                    PlayerRotation[0] = mc.thePlayer.rotationYaw;
                    PlayerRotation[1] = mc.thePlayer.rotationPitch;
                    RodSlot = mc.thePlayer.inventory.currentItem;
                    if(Quantity >= MaxQuantity){
                        tick=0;
                        CurrectTime = 0;
                        DuringClearer = true;
                    }
                }
                if(DuringClearer){
                    tick++;
                    if(CurrectTime <= Times){
                        if(tick==1){
                            CurrectTime++;
                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = ToolSlot;
                            mc.thePlayer.rotationYaw = TargetRotation[0];
                            mc.thePlayer.rotationPitch = TargetRotation[1];
                            if(Quantity <= 0){
                                DuringClearer = false;
                                tick=0;
                                CurrectTime = 0;
                                Minecraft.getMinecraft().thePlayer.inventory.currentItem = RodSlot;
                                mc.thePlayer.rotationYaw = PlayerRotation[0];
                                mc.thePlayer.rotationPitch = PlayerRotation[1];
                            }
                        }
                        if(tick==40){
                            if(mc.thePlayer.getHeldItem() != null){
                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                            }
                        }
                        if(tick >= 40+Math.round((float) Delay / 25)){
                            tick = 0;
                        }
                    }else{
                        DuringClearer = false;
                        tick=0;
                        CurrectTime = 0;
                        Minecraft.getMinecraft().thePlayer.inventory.currentItem = RodSlot;
                        mc.thePlayer.rotationYaw = PlayerRotation[0];
                        mc.thePlayer.rotationPitch = PlayerRotation[1];
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.ElementType.TEXT){
            if(Functions.GetStatus("WormCleaner")&& mc.fontRendererObj != null && mc.theWorld != null){
                mc.fontRendererObj.drawStringWithShadow("§b「 §lDiamondS §r§bWorm Fishing Info 」", TextPosition[0], TextPosition[1], 0xFFFFFF);
                mc.fontRendererObj.drawStringWithShadow("§6附近的 Worm 数量: §a"+ Quantity + "§6/§c"+MaxQuantity, TextPosition[0], TextPosition[1]+10, 0xFFFFFF);
                mc.fontRendererObj.drawStringWithShadow("§6当前世界时间: §aDay "+ (int) Math.floor((double) mc.theWorld.getWorldTime() /24000), TextPosition[0], TextPosition[1]+20, 0xFFFFFF);
            }
        }
    }
}
