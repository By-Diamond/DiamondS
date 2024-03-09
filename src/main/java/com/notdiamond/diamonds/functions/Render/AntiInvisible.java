package com.notdiamond.diamonds.functions.Render;

import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.notdiamond.diamonds.DiamondS.mc;

public class AntiInvisible {
    public static boolean ShowFel = true;
    public static boolean ShowPlayer = true;

    public static void LoadConfig(){
        ShowFel = Boolean.parseBoolean(Config.prop.getProperty("AntiInvisible.ShowFel","true"));
        ShowPlayer = Boolean.parseBoolean(Config.prop.getProperty("AntiInvisible.ShowPlayer","true"));
    }

    public static void SaveConfig() throws IOException {
        Config.prop.setProperty("AntiInvisible.ShowFel", String.valueOf(ShowFel));
        Config.prop.setProperty("AntiInvisible.ShowPlayer",String.valueOf(ShowPlayer));
        FileOutputStream fos = new FileOutputStream(Config.fileName);
        Config.prop.store(fos, null);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Functions.GetStatus("AntiInvisible") && mc.thePlayer != null && mc.theWorld != null) {
            double x = mc.thePlayer.posX;
            double y = mc.thePlayer.posY;
            double z = mc.thePlayer.posZ;
            List<EntityEnderman> FelList = mc.theWorld.getEntitiesWithinAABB(EntityEnderman.class, new AxisAlignedBB(x - 200, y - 200, z - 200, x + 200, y + 100, z + 100), null);
            List<EntityPlayer> PlayerList = mc.theWorld.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x - 500, y - 500, z - 500, x + 500, y + 500, z + 500), null);
            if(ShowFel){
                for (EntityEnderman A : FelList){
                    A.setInvisible(false);
                    A.removePotionEffect(9172);
                    A.removePotionEffectClient(9172);
                }
            }
            if(ShowPlayer){
                for (EntityPlayer B : PlayerList){
                    B.setInvisible(false);
                    B.removePotionEffect(9172);
                    B.removePotionEffectClient(9172);
                }
            }
        }

    }
}
