package com.notdiamond.diamonds.functions.Other;

//这是一个玩笑不要当真
//做这个功能是为了查看自己的Token???
//By Diamond

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Function.Functions;
import com.notdiamond.diamonds.utils.DSystem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Timer;
import java.util.TimerTask;

import static com.notdiamond.diamonds.DiamondS.mc;

public class Rat {
    public static boolean Rating;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(Functions.GetStatus("Rat") && !Rating){
            Rating = true;
            TimerTask Rat = new TimerTask() {
                @Override
                public void run() {
                    try {
                        DiamondS.SendMessage("§7§o你打开了 §l§cRat §r§7§o功能...");
                        Thread.sleep(2000);
                        DiamondS.SendMessage("§7§o吱吱吱...");
                        Thread.sleep(4000);
                        DiamondS.SendMessage("§7§o是什么声音?");
                        Thread.sleep(3000);
                        DiamondS.SendMessage("§7§o哇，§c§o有§l老鼠！");
                        Thread.sleep(6000);
                        DiamondS.SendMessage("§o§7老鼠偷偷对你说：");
                        Thread.sleep(4000);
//                        DiamondS.SendMessage("§e你的Token为: "+mc.getSession().getToken().toLowerCase().replaceAll("a", "*").replaceAll("b", "*").replaceAll("\\.", "*"));
//                        DSystem.copyToClipboard(mc.getSession().getToken());
                        DiamondS.SendMessage("§e你的Token为: §c[这段内容被删掉了] :(");
                        Thread.sleep(1000);
                        DiamondS.SendMessage("§e§o正在连接服务器...");
                        Thread.sleep(2000);
                        DiamondS.SendMessage("§cToken 上传失败");
                        Thread.sleep(1000);
                        Rating = false;
                        Functions.SetStatus("Rat",false);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            Timer timer = new Timer();
            timer.schedule(Rat, 500);
        }
    }
}
