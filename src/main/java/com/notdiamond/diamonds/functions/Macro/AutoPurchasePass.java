package com.notdiamond.diamonds.functions.Macro;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Functions;
import com.notdiamond.diamonds.utils.DText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

import static com.notdiamond.diamonds.DiamondS.mc;

public class AutoPurchasePass {

    public static Boolean Purchased = false;
    public static Timer autopurchase = new Timer();

    @SubscribeEvent
    public void ChatFunctions(ClientChatReceivedEvent event) {
        if(Functions.GetStatus("AutoPurchasePass")){
            String msg = event.message.getUnformattedText();
            msg = DText.RemoveColor(msg);

            if(msg.startsWith("Your pass to the Crystal Hollows will expire") || msg.startsWith("Click here to purchase a new ")){
                TimerTask Purchase = new TimerTask() {
                    @Override
                    public void run() {
                        if(Purchased){
                            Purchased = false;
                            autopurchase.cancel();
                            autopurchase = new Timer();
                            return;
                        }
                        mc.thePlayer.sendChatMessage("/purchasecrystallhollowspass");
                    }
                };
                autopurchase.schedule(Purchase, 500, 1000);
                return;
            }
            if(msg.startsWith("You purchased a new pass for ")){
                Purchased = true;
                DiamondS.SendMessage("§a已为你自动购买 Crystal Hollows Pass");
                return;
            }
        }
    }
}
