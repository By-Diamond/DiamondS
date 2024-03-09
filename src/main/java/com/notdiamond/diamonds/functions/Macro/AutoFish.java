package com.notdiamond.diamonds.functions.Macro;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.utils.DText;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.notdiamond.diamonds.DiamondS.mc;

public class AutoFish {
    public static int RethrowDelay = 300;
    public static int PickDelay = 50;
    public static int MaxTime = 20;
    public static int MaxWaitTime = 3;
    public static String RodName = "rod";
    public boolean AutoSwap = false;
    public static int[] TextPosition = new int[2];
    public static int AntiMacroTime = 12000;
    public static boolean AutoMove = true;
    public static boolean AlwaysSneak = false;
    public static boolean AlwaysW = false;
    public static int AutoMoveDU = 200;
    public static boolean AutoMoveLROnly = false;

    public static int Stage;
    public static int Tick;
    public static boolean HookThrown = false;
    static String Text;
    public static Timer antimacro = new Timer();

    public static void LoadConfig() {
        TextPosition[0] = Integer.parseInt(Config.prop.getProperty("AutoFish.TextX", "100"));
        TextPosition[1] = Integer.parseInt(Config.prop.getProperty("AutoFish.TextY", "120"));
        RethrowDelay = Integer.parseInt(Config.prop.getProperty("AutoFish.RethrowDelay", "300"));
        PickDelay = Integer.parseInt(Config.prop.getProperty("AutoFish.PickDelay", "50"));
        MaxTime = Integer.parseInt(Config.prop.getProperty("AutoFish.MaxTime", "20"));
        MaxWaitTime = Integer.parseInt(Config.prop.getProperty("AutoFish.MaxWaitTime", "3"));
        AutoMove = Boolean.parseBoolean(Config.prop.getProperty("AutoFish.AutoMove", "true"));
        AlwaysSneak = Boolean.parseBoolean(Config.prop.getProperty("AutoFish.AlwaysSneak", "false"));
        AlwaysW = Boolean.parseBoolean(Config.prop.getProperty("AutoFish.AlwaysW", "false"));
        AutoMoveLROnly = Boolean.parseBoolean(Config.prop.getProperty("AutoFish.AMLROnly", "false"));
        AntiMacroTime = Integer.parseInt(Config.prop.getProperty("AutoFish.AntiMacroTime", "12000"));
        AutoMoveDU = Integer.parseInt(Config.prop.getProperty("AutoFish.AutoMoveDuration", "200"));
    }

    public static void SaveConfig() throws IOException {
        Config.prop.setProperty("AutoFish.TextX", String.valueOf(TextPosition[0]));
        Config.prop.setProperty("AutoFish.TextY", String.valueOf(TextPosition[1]));
        Config.prop.setProperty("AutoFish.RethrowDelay", String.valueOf(RethrowDelay));
        Config.prop.setProperty("AutoFish.PickDelay", String.valueOf(PickDelay));
         Config.prop.setProperty("AutoFish.MaxTime", String.valueOf(MaxTime));
         Config.prop.setProperty("AutoFish.MaxWaitTime", String.valueOf(MaxWaitTime));
        Config.prop.setProperty("AutoFish.AlwaysSneak", String.valueOf(AlwaysSneak));
        Config.prop.setProperty("AutoFish.AlwaysW", String.valueOf(AlwaysW));
        Config.prop.setProperty("AutoFish.AMLROnly", String.valueOf(AutoMoveLROnly));
        Config.prop.setProperty("AutoFish.AutoMove", String.valueOf(AutoMove));
        Config.prop.setProperty("AutoFish.AutoMoveDU", String.valueOf(AutoMove));
        Config.prop.setProperty("AutoFish.AntiMacroTime", String.valueOf(AntiMacroTime));
        Config.prop.setProperty("AutoFish.AutoMoveDuration", String.valueOf(AutoMoveDU));
        FileOutputStream fos = new FileOutputStream(Config.fileName);
        Config.prop.store(fos, null);
    }


    public static void switched(){
        if(!AutoMove){
            return;
        }
        if(Functions.GetStatus("AutoFish")){
            TimerTask AntiMacro = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if(!AutoMove || !Functions.GetStatus("AutoFish")){
                            antimacro.cancel();
                            antimacro = new Timer();
                            return;
                        }
                        if(!(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() == Items.fishing_rod)){
                            return;
                        }
                        int type = 1;
                        if(!AlwaysW && !AutoMoveLROnly){
                            type = DText.randomnumber(1, 4);
                        }else{
                            type = DText.randomnumber(1, 2);
                        }
                        int A = DText.randomnumber(AutoMoveDU, AutoMoveDU+50);
                        int B = DText.randomnumber(1, 3);
                        int C = DText.randomnumber(1500, 3000);
                        switch (type){
                            case 1:
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                Thread.sleep(DText.randomnumber(100, 300));
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
                                Thread.sleep(A);
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw + B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch + B/2);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
                                if(!AutoMove || !Functions.GetStatus("AutoFish")){
                                    antimacro.cancel();
                                    antimacro = new Timer();
                                    return;
                                }
                                Thread.sleep(C);
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw- B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch - B/2);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
                                Thread.sleep(A);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
                                break;
                            case 2:
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                Thread.sleep(DText.randomnumber(100, 300));
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
                                Thread.sleep(A);
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw + B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch + B/2);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
                                if(!AutoMove || !Functions.GetStatus("AutoFish")){
                                    antimacro.cancel();
                                    antimacro = new Timer();
                                    return;
                                }
                                Thread.sleep(C);
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw- B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch - B/2);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
                                Thread.sleep(A);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
                                break;
                            case 3:
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                Thread.sleep(DText.randomnumber(100, 300));
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
                                Thread.sleep(A);
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw - B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch - B/2);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
                                if(!AutoMove || !Functions.GetStatus("AutoFish")){
                                    antimacro.cancel();
                                    antimacro = new Timer();
                                    return;
                                }
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw+ B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch + B/2);
                                Thread.sleep(C);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                                Thread.sleep(A);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
                                break;
                            case 4:
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                Thread.sleep(DText.randomnumber(100, 300));
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                                Thread.sleep(A);
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw - B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch - B/2);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
                                if(!AutoMove || !Functions.GetStatus("AutoFish")){
                                    antimacro.cancel();
                                    antimacro = new Timer();
                                    return;
                                }
                                mc.thePlayer.rotationYaw = (float) (mc.thePlayer.rotationYaw+ B/2);
                                mc.thePlayer.rotationPitch = (float) (mc.thePlayer.rotationPitch + B/2);
                                Thread.sleep(C);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
                                Thread.sleep(A);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
                                break;
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            antimacro = new Timer();
            antimacro.schedule(AntiMacro, AntiMacroTime, AntiMacroTime);
        }else{
            if(AlwaysSneak){KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);}
            if(AlwaysW){KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);}

            antimacro.cancel();
            antimacro = new Timer();
        }
    }

    static void Rethrow(int time){
        Tick = 0;
        Stage = 2;
        TimerTask Rethrow = new TimerTask() {
            @Override
            public void run() {
                Tick = 0;
                Stage = 2;
                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                try {
                    Thread.sleep(RethrowDelay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Tick = 0;
                Stage = 1;
                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                this.cancel();
            }
        };
        Timer timer = new Timer();
        timer.schedule(Rethrow, time);
    }

    @SubscribeEvent
    public void AutoPickUp(PlaySoundEvent event) {
        if (Functions.GetStatus("AutoFish") && HookThrown && mc.theWorld != null) {
            if((event.name.contains("game.player.swim.splash"))){
                float x = event.result.getXPosF();
                float y = event.result.getYPosF();
                float z = event.result.getZPosF();
                List<EntityFishHook> entities = mc.theWorld.getEntitiesWithinAABB(EntityFishHook.class, new AxisAlignedBB(x - (0.5 / 2d), y - (0.5 / 2d), z - (0.5 / 2d), x + (0.5 / 2d), y + (0.5 / 2d), z + (0.5 / 2d)), null);
                for (EntityFishHook entity : entities) {
                    if (entity.angler == mc.thePlayer || entity.angler.getName().toLowerCase().contains(mc.thePlayer.getName().toLowerCase())) {
                        Rethrow(PickDelay);
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Functions.GetStatus("AutoFish") && mc.theWorld != null && mc.thePlayer != null) {
            HookThrown = isHookThrown();
            if(AlwaysSneak && !mc.gameSettings.keyBindSneak.isKeyDown()){
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
            }
            if(AlwaysW && !mc.gameSettings.keyBindForward.isKeyDown()){
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            }
            if (HookThrown && Stage == 0) {
                Tick = 0;
                Stage = 1;
                return;
            }
            if(!HookThrown && (Stage == 1 || Stage == 2)){
                Tick = 0;
                Stage = 0;
                return;
            }
            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() == Items.fishing_rod) {
                if (Stage == 2) {
                    Tick = 0;
                    Text = "§6§l正在收竿中...";
                } else {
                    Tick++;
                    if (HookThrown) {
                        Text = "§aHook Thrown Time: §l" + (int) Math.floor((double) Tick / 40) + "§r§as";
                    } else {
                        Text = "§6Wait Time: §l" + (int) Math.floor((double) Tick / 40) + "§r§6s";
                    }
                }
            } else {
                Tick = 0;
                Stage = 0;
                Text = "§c§l[⚠] 未持竿!";
            }
            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() == Items.fishing_rod && !HookThrown && Stage == 0 && Tick >= 40 * MaxWaitTime) {
                Tick = 0;
                Stage = 1;
                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                return;
            }
            if (Stage == 1 && Tick >= 40 * MaxTime) {
                Tick = 0;
                Stage = 2;
                Rethrow(50);
                return;
            }
        }
    }

    @SubscribeEvent
    public void AutoOFF1(WorldEvent.Load event) {
        if (Functions.GetStatus("AutoFish")) {
            Functions.SetStatus("AutoFish", false);
            DiamondS.SendMessage("§c检测到更换世界，已自动关闭 §lAutoFish");
        }
    }

    @SubscribeEvent
    public void AutoOFF2(EntityEvent event) {
        if (Functions.GetStatus("AutoFish") && mc.theWorld != null && mc.thePlayer != null) {
            if (event.entity == mc.thePlayer) {
                if (mc.thePlayer.isDead) {
                    Functions.SetStatus("AutoFish", false);
                    DiamondS.SendMessage("§c检测到你已死亡，已自动关闭 §lAutoFish");
                }
            }
        }
    }

    @SubscribeEvent
    public void AutoOFF2(ClientChatReceivedEvent event) {
        if (Functions.GetStatus("AutoFish") && mc.thePlayer != null) {
            String msg = event.message.getUnformattedText();
            msg = DText.RemoveColor(msg);
            if (msg.startsWith("You can hear mightly rumbles and explosions") || msg.startsWith("You notice the ceiling starting")) {
                mc.thePlayer.playSound("random.anvil_land", 1, 1);
                Functions.SetStatus("AutoFish", false);
                DiamondS.SendMessage("§c检测到矿洞即将崩坏，请及时更换房间！");
            }
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (Functions.GetStatus("AutoFish") && e.type == RenderGameOverlayEvent.ElementType.TEXT) {
            ScaledResolution scaled = new ScaledResolution(mc);
            mc.fontRendererObj.drawStringWithShadow("§b「 §lDiamondS §r§bAutoFish 」", TextPosition[0], TextPosition[1], 0xFFFFFF);
            mc.fontRendererObj.drawStringWithShadow(Text, TextPosition[0], TextPosition[1]+10, 0xFFFFFF);
        }
    }

    public boolean isHookThrown() {
        //原文摘抄绿猫(GreenCat QQ:2122520074)
        if (Functions.GetStatus("AutoFish") && mc.theWorld != null) {
            boolean thrown = false;
            List<EntityFishHook> entities = mc.theWorld.getEntitiesWithinAABB(EntityFishHook.class, new AxisAlignedBB(mc.thePlayer.posX - (200 / 2d), mc.thePlayer.posY - (200 / 2d), mc.thePlayer.posZ - (200 / 2d), mc.thePlayer.posX + (200 / 2d), mc.thePlayer.posY + (200 / 2d), mc.thePlayer.posZ + (200 / 2d)), null);
            for (EntityFishHook entity : entities) {
                if (entity.angler == mc.thePlayer) {
                    thrown = true;
                    break;
                }
            }
            return thrown;
        } else {
            return false;
        }
    }
}
