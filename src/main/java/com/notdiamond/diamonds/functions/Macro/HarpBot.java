package com.notdiamond.diamonds.functions.Macro;


import com.notdiamond.diamonds.core.Config;
import com.notdiamond.diamonds.core.Functions;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.notdiamond.diamonds.DiamondS.mc;
import static net.minecraft.client.Minecraft.getMinecraft;

public class HarpBot {
    public static boolean InHarp = false;
    public static Timer timer = new Timer();
    public static boolean during;
    public static int delay = 150;
    public static int LastSlot = 0;
    public static int NewSlot = 0;
    public static boolean DupeAllowed = false;
    public static boolean glassing = false;
    public static boolean StartDupe = false;

    public static void LoadConfig(){
        delay = Integer.parseInt(Config.prop.getProperty("HarpBot.Delay","150"));
    }

    public static void SaveConfig() throws IOException {
        Config.prop.setProperty("HarpBot.Delay", String.valueOf(delay));
        FileOutputStream fos = new FileOutputStream(Config.fileName);
        Config.prop.store(fos, null);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiChest) {
            Container container = ((GuiChest) event.gui).inventorySlots;
            if (container instanceof ContainerChest) {
                ContainerChest containerChest = (ContainerChest) container;
                if (containerChest.getLowerChestInventory().getDisplayName().getUnformattedText().startsWith("Harp -")) {
                    InHarp = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.DrawScreenEvent event) throws InterruptedException {
        if (!(event.gui instanceof GuiChest)) return;
        Container container = ((GuiChest) event.gui).inventorySlots;
        if (container instanceof ContainerChest) {
            List<Slot> invSlots = container.inventorySlots;
            if(InHarp && Functions.GetStatus("HarpBot")){
                for (int i = 27; i <= 35; i++) {
                    if(invSlots.get(i).getStack() != null && invSlots.get(i).getStack().getItem() == Item.getItemFromBlock(Blocks.wool)){
                        glassing = false;
                        NewSlot=i;
                        if(invSlots.get(i-9).getStack() != null && invSlots.get(i-9).getStack().getItem() == Item.getItemFromBlock(Blocks.wool) && LastSlot != i){
                            DupeAllowed = true;
                        }
                        if(DupeAllowed && (invSlots.get(i-9).getStack() == null || invSlots.get(i-9).getStack().getItem() != Item.getItemFromBlock(Blocks.wool))){
                            DupeAllowed = false;
                            StartDupe = true;
                        }
                        return;
                    }
                }
                if(LastSlot == NewSlot){
                    LastSlot = 0;
                    glassing = true;
                }
            }
        }
    }

    public static void RegisterTimer(){
        TimerTask HarpBot = new TimerTask() {
            @Override
            public void run() {
                try {
                    if(!InHarp || !Functions.GetStatus("HarpBot")){
                        if(!Functions.GetStatus("HarpBot")){
                            timer.cancel();
                            timer = new Timer();
                        }
                        return;
                    }
                    if(NewSlot <= 0  || during || mc.thePlayer == null || mc.theWorld == null || glassing){
                        return;
                    }
                    during = true;
                    int Slot = NewSlot;
                    if(Slot == LastSlot && !StartDupe){
                        during = false;
                        return;
                    }
                    if(StartDupe){
                        Thread.sleep(delay);
                    }
                    Thread.sleep(delay);
                    mc.playerController.windowClick(
                                mc.thePlayer.openContainer.windowId,
                                Slot+9,
                                0,
                                4,
                                mc.thePlayer
                    );
                    StartDupe = false;
                    LastSlot = Slot;
                    during = false;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer = new Timer();
        timer.schedule(HarpBot, 10,10);
    }
}