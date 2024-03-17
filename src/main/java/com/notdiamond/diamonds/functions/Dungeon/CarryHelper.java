package com.notdiamond.diamonds.functions.Dungeon;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Config.Config;
import com.notdiamond.diamonds.core.Function.Functions;
import com.notdiamond.diamonds.functions.Chat.ChatClass;
import com.notdiamond.diamonds.utils.DText;
import com.notdiamond.diamonds.utils.Timer.DTimer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.notdiamond.diamonds.DiamondS.mc;

public class CarryHelper {

    public static boolean CarryHelper_IsAutoWarp = false;
    public static boolean CarryHelper_IsAutoMessage = false;
    public static String CarryHelper_AutoMessage = "Pay at sand";
    public static double LastTime = 0D;

    public static void LoadConfig(){
        CarryHelper_IsAutoWarp = Boolean.parseBoolean(Config.prop.getProperty("CarryHelper.AutoWarp", "false"));
        CarryHelper_IsAutoMessage = Boolean.parseBoolean(Config.prop.getProperty("CarryHelper.AutoMessage", "false"));
        CarryHelper_AutoMessage = Config.prop.getProperty("CarryHelper.Msg", "Pay at sand");
    }

    public static void SaveConfig() throws IOException {
        Config.prop.setProperty("CarryHelper.AutoWarp", String.valueOf(CarryHelper.CarryHelper_IsAutoWarp));
        Config.prop.setProperty("CarryHelper.AutoMessage", String.valueOf(CarryHelper.CarryHelper_IsAutoMessage));
        Config.prop.setProperty("CarryHelper.Msg", CarryHelper.CarryHelper_AutoMessage);
        FileOutputStream fos = new FileOutputStream(Config.fileName);
        Config.prop.store(fos, null);
    }

    @SubscribeEvent(
          receiveCanceled = true
    )
    public void CarryHelper(ClientChatReceivedEvent event) {
        String msg = event.message.getUnformattedText();
        msg = DText.RemoveColor(msg);
        String player;
        IChatComponent ALL;

        if (ChatClass.CheckMessage(msg, event)) {
            return;
        }

        if (Functions.GetStatus("CarryHelper")) {
            if (msg.startsWith("Party Finder > ") && msg.contains(" joined the dungeon group!")) {
                mc.thePlayer.playSound("random.levelup", 1, 1);
                if (CarryHelper_IsAutoWarp) {
                    TimerTask task1 = new TimerTask() {
                        @Override
                        public void run() {
                            mc.thePlayer.sendChatMessage("/p warp");
                            LastTime = DTimer.Time;
                        }
                    };
                    DiamondS.SendMessage(LastTime+" "+DTimer.Time);
                    Timer timer1 = new Timer();
                    if(LastTime > 0 && DTimer.Time - LastTime <= 5.2){
                        timer1.schedule(task1, (long) (1000 * (6 - (DTimer.Time - LastTime))));
                    }else{
                        timer1.schedule(task1, (500));
                    }

                }
                if (CarryHelper_IsAutoMessage) {
                    TimerTask task2 = new TimerTask() {
                        @Override
                        public void run() {
                            mc.thePlayer.sendChatMessage("/pc " + CarryHelper_AutoMessage);
                        }
                    };
                    Timer timer2 = new Timer();
                    if (CarryHelper_IsAutoWarp) {
                        timer2.schedule(task2, 2000);
                    } else {
                        timer2.schedule(task2, 1000);
                    }
                }
            }
            if (msg.contains("entered The Catacombs, Floor ") || msg.contains(" entered MM Catacombs, Floor ")) {
                DiamondS.TradeList.clear();
                DiamondS.SendMessage("§a§l你已进入游戏，已清空Trade List");
                return;
            }
            if (msg.startsWith("Trade completed with ") && msg.endsWith("!")) {
                if (msg.contains("] ")) {
                    player = DText.getSubString(msg, "] ", "!");
                } else {
                    player = DText.getSubString(msg, "Trade completed with ", "!");
                }
                player = player.replace(" ", "");

                boolean AlreadyTrade = false;
                String ALLTraded = "";
                for (int i = 0; i <= DiamondS.TradeList.size() - 1; i++) {
                    if (DiamondS.TradeList.get(i).contains(player)) {
                        AlreadyTrade = true;
                    }
                    if (i <= 0) {
                        ALLTraded = DiamondS.TradeList.get(i);
                    } else {
                        ALLTraded += "\n§a" + DiamondS.TradeList.get(i);
                    }
                }

                if (!AlreadyTrade) {
                    if (DiamondS.TradeList.size() <= 0) {
                        DiamondS.TradeList.add(player);
                        ALLTraded = "§a" + player;
                    } else {
                        DiamondS.TradeList.add(player);
                        ALLTraded = ALLTraded + "\n§a" + player;
                    }
                }
                if (DiamondS.TradeList.size() < 4) {
                    ALL = new ChatComponentText("§b§lDiamondS > §r 当前已有 §a" + DiamondS.TradeList.size() + " §r人已完成 Trade");
                    ChatStyle Trade_Style = new ChatStyle();
                    Trade_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("§a" + ALLTraded + "\n§b点击查看队伍成员")));
                    Trade_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p list"));
                    ALL.setChatStyle(Trade_Style);
                    event.setCanceled(true);
                    mc.thePlayer.addChatMessage(event.message.createCopy());
                    mc.thePlayer.addChatMessage(ALL);
                } else {
                    event.setCanceled(true);
                    event.setCanceled(true);
                    mc.thePlayer.addChatMessage(event.message.createCopy());
                    ALL = new ChatComponentText("§b§lDiamondS > §a§l所有人均已完成 Trade，可以进入 Dungeon");
                    ChatStyle Trade_Style = new ChatStyle();
                    Trade_Style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(" §a" + ALLTraded + "\n§b点击清空 Trade List ")));
                    Trade_Style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fs carryhelper clear"));
                    ALL.setChatStyle(Trade_Style);
                    mc.thePlayer.addChatMessage(ALL);
                }
                return;
            }
        }

    }
}
