package com.notdiamond.diamonds.core.SmoothRotation;

// 原文摘抄 Antimony - By GreenCat
import static com.notdiamond.diamonds.DiamondS.mc;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SmoothRotation {
    private static float pitchDifference;
    public static float yawDifference;
    private static int ticks = -1;
    private static int tickCounter = 0;
    private static Runnable callback = null;

    public static boolean running = false;
    public SmoothRotation() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static void smoothLook(float Yaw,float Pitch, int ticks, Runnable callback) {
        if(ticks == 0) {
            look(Pitch,Yaw);
            callback.run();
            return;
        }

        SmoothRotation.callback = callback;

        pitchDifference = wrapAngleTo180(Pitch -mc.thePlayer.rotationPitch);
        yawDifference = wrapAngleTo180(Yaw - mc.thePlayer.rotationYaw);

        SmoothRotation.ticks = ticks * 20;
        SmoothRotation.tickCounter = 0;
    }
    public static void smoothLookWithout(float Yaw,float Pitch, int ticks) {
        if(ticks == 0) {
            look(Pitch,Yaw);
            callback.run();
            return;
        }

        SmoothRotation.callback = null;

        pitchDifference = wrapAngleTo180(Pitch -mc.thePlayer.rotationPitch);
        yawDifference = wrapAngleTo180(Yaw - mc.thePlayer.rotationYaw);

        SmoothRotation.ticks = ticks * 20;
        SmoothRotation.tickCounter = 0;
    }

    public static void look(float Pitch,float Yaw) {
        mc.thePlayer.rotationPitch = Pitch;
        mc.thePlayer.rotationYaw = Yaw;
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if(mc.thePlayer == null) return;
        if(tickCounter < ticks) {
            running = true;
            mc.thePlayer.rotationPitch += pitchDifference / ticks;
            mc.thePlayer.rotationYaw += yawDifference / ticks;
            tickCounter++;
        } else if(callback != null) {
            running = false;
            callback.run();
            callback = null;
        }
    }

    private static float wrapAngleTo180(float angle) {
        return (float) (angle - Math.floor(angle / 360 + 0.5) * 360);
    }
}

