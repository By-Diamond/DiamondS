package com.notdiamond.diamonds.functions.Dungeon;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Config.Config;
import com.notdiamond.diamonds.core.Function.Functions;
import com.notdiamond.diamonds.core.KeyLoader.KeyLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.notdiamond.diamonds.DiamondS.mc;

public class GhostBlock {
    public static int type = 1;
    public static String ItemName = "pickaxe";

    static boolean alreadysend = false;
    public static void LoadConfig(){
        type = Integer.parseInt(Config.prop.getProperty("GhostBlock.Type","1"));
        ItemName = Config.prop.getProperty("GhostBlock.ItemName","pickaxe");
    }

    public static void SaveConfig() throws IOException {
        Config.prop.setProperty("GhostBlock.Type", String.valueOf(type));
        Config.prop.setProperty("GhostBlock.ItemName",ItemName);
        FileOutputStream fos = new FileOutputStream(Config.fileName);
        Config.prop.store(fos, null);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Functions.GetStatus("GhostBlock") && mc.thePlayer != null && mc.theWorld != null) {
            if (type <= 1 || type > 4) {
                if (KeyLoader.GhostBlock.isKeyDown()) {
                    SetAir();
                }
            }
        }
        if (type == 2 && mc.gameSettings.keyBindSneak.isKeyDown()) {
            SetAir();}
        if(type == 3 && mc.gameSettings.keyBindUseItem.isKeyDown()){
            if(mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getDisplayName().toLowerCase().contains(ItemName)){
                SetAir();
            }
        }
        if(type == 4 && mc.gameSettings.keyBindAttack.isKeyDown()){
            if(mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getDisplayName().toLowerCase().contains(ItemName)){
                SetAir();
            }
        }
    }
    private void SetAir(){
        //这一段代码完全来自Antimony - By GreenCat
        MovingObjectPosition position = mc.thePlayer.rayTrace(6.0D, 0.0F);
        Block TheBlock = Minecraft.getMinecraft().thePlayer.worldObj.getBlockState(position.getBlockPos()).getBlock();
        if (TheBlock!= Blocks.barrier && TheBlock!= Blocks.air && TheBlock != Blocks.chest && TheBlock!= Blocks.ender_chest && TheBlock != Blocks.trapped_chest && TheBlock != Blocks.stone_button && TheBlock != Blocks.wooden_button && TheBlock != Blocks.skull && TheBlock != Blocks.lever && TheBlock != Blocks.command_block) {
            mc.thePlayer.worldObj.setBlockToAir(position.getBlockPos());
        }
        if(TheBlock == Blocks.trapped_chest && !alreadysend){
            DiamondS.SendMessage("§a你发现了一个陷阱箱，在 Dungeon 中可能含有 §c§lMimic");
            alreadysend = true;
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    alreadysend = false;
                }
            };
            Timer timer1 = new Timer();
            timer1.schedule(task1, 3000);
        }
    }
}
