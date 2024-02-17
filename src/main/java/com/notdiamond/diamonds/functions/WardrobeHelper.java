package com.notdiamond.diamonds.functions;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

import static net.minecraft.client.Minecraft.*;

public class WardrobeHelper {
    public static int Delay = 500;

    @SubscribeEvent
    public void WardrobeHelper(final GuiOpenEvent event) throws InterruptedException {
        if (event.gui instanceof GuiChest) {
            if (DiamondS.IsOnSkyBlock() && event.gui instanceof GuiChest && Functions.GetStatus("WardRobeHelper") && DiamondS.tempint > 0) {
                Container container = ((GuiChest) event.gui).inventorySlots;
                if (container instanceof ContainerChest) {
                    final String chestName = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
                    final TimerTask task2 = new TimerTask() {
                        @Override
                        public void run() {
                            if(getMinecraft().thePlayer.openContainer != null){
                                getMinecraft().thePlayer.closeScreen();
                                DiamondS.tempint = 0;
                            }
                        }
                    };
                    TimerTask task1 = new TimerTask() {
                        @Override
                        public void run() {
                            if (chestName.contains("Wardrobe") && chestName.contains("(1/2)")) {
                                if (DiamondS.tempint > 9) {
                                    getMinecraft().playerController.windowClick(
                                            getMinecraft().thePlayer.openContainer.windowId,
                                            53,
                                            0,
                                            0,
                                            getMinecraft().thePlayer
                                    );
                                } else {
                                    getMinecraft().playerController.windowClick(
                                            getMinecraft().thePlayer.openContainer.windowId,
                                            35 + DiamondS.tempint,
                                            0,
                                            0,
                                            getMinecraft().thePlayer
                                    );
                                    Timer timer2 = new Timer();
                                    timer2.schedule(task2, Delay);
                                    DiamondS.tempint = 0;
                                }
                            }
                            if (chestName.contains("Wardrobe") && chestName.contains("(2/2)")) {
                                getMinecraft().playerController.windowClick(
                                        getMinecraft().thePlayer.openContainer.windowId,
                                        35 + DiamondS.tempint - 9,
                                        0,
                                        0,
                                        getMinecraft().thePlayer
                                );
                                Timer timer2 = new Timer();
                                timer2.schedule(task2, Delay);
                            }
                        }
                    };
                    Timer timer1 = new Timer();
                    timer1.schedule(task1, Delay);
                }
            }
        }
    }
}
