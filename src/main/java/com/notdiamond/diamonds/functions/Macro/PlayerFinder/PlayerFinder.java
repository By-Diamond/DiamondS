package com.notdiamond.diamonds.functions.Macro.PlayerFinder;

import com.notdiamond.diamonds.DiamondS;
import com.notdiamond.diamonds.core.Config.Config;
import com.notdiamond.diamonds.core.Function.Functions;
import com.notdiamond.diamonds.utils.DMisc;
import com.notdiamond.diamonds.utils.DText;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.notdiamond.diamonds.DiamondS.mc;

public class PlayerFinder {
    public static int[] TextPosition = new int[2];
    public static boolean colorful = true;

    ArrayList<DPlayer> PlayerList = new ArrayList<DPlayer>();
    public static void LoadConfig() {
        TextPosition[0] = Integer.parseInt(Config.prop.getProperty("PlayerFinder.TextX", "100"));
        TextPosition[1] = Integer.parseInt(Config.prop.getProperty("PlayerFinder.TextY", "50"));
        colorful = Boolean.parseBoolean(Config.prop.getProperty("PlayerFinder.Colorful", "true"));
    }

    public static void SaveConfig() throws IOException {
        Config.prop.setProperty("PlayerFinder.TextX", String.valueOf(TextPosition[0]));
        Config.prop.setProperty("PlayerFinder.TextY", String.valueOf(TextPosition[1]));
        Config.prop.setProperty("PlayerFinder.Colorful", String.valueOf(colorful));
        FileOutputStream fos = new FileOutputStream(Config.fileName);
        Config.prop.store(fos, null);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(Functions.GetStatus("PlayerFinder") || Functions.GetStatus("Debug.Health")){
            if(mc.theWorld != null && mc.thePlayer != null){
                double x = mc.thePlayer.posX;
                double y = mc.thePlayer.posY;
                double z = mc.thePlayer.posZ;
                List<EntityPlayer> entityList = mc.theWorld.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x - 1000, y - 1000, z - 1000, x + 1000, y + 1000, z + 1000), null);
                if(!entityList.isEmpty()){
                    ArrayList<DPlayer> arrayList = new ArrayList<DPlayer>();
                    for(EntityPlayer A:entityList){
                        if(!DMisc.CheckNPC(A) && DMisc.isValid(A)){
                            arrayList.add(new DPlayer(A.getName(),A.getDistanceToEntity(mc.thePlayer),A.posX,A.posY,A.posZ));
                            if(DText.GetLengthAfterDot(String.valueOf(A.getHealth())) > 1 && Functions.GetStatus("Debug.Health")){
                                DiamondS.SendMessage("§a名称" + A.getName() + " || §b生命值" + A.getHealth() + " || §e小数位数" + DText.GetLengthAfterDot(String.valueOf(A.getHealth())));
                            }
                        }
                    }
                    if(!arrayList.isEmpty()){
                        Collections.sort(arrayList);
                    }
                    PlayerList = arrayList;
                }
            }
        }
    }
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.ElementType.TEXT){
            //ScaledResolution scaled = new ScaledResolution(mc);
            int playerin20 = 0;
            int playerin50 = 0;
            int playerin100 = 0;
            for(DPlayer B : PlayerList){
                if(B.Distance > 0){
                    if(B.Distance <=20){playerin20++;}
                    if(B.Distance <=50){playerin50++;}
                    if(B.Distance <=100){playerin100++;}
                }
            }
            if(Functions.GetStatus("PlayerFinder")&& mc.fontRendererObj != null && mc.theWorld != null){
                mc.fontRendererObj.drawStringWithShadow("§b「 §lDiamondS §r§bPlayerFinder 」", TextPosition[0], TextPosition[1], 0xFFFFFF);
                if(colorful){
                    mc.fontRendererObj.drawStringWithShadow("§620格内:§c"+playerin20+"§6   50格内:§6"+playerin50+"   100格内:§a"+playerin100, TextPosition[0], TextPosition[1]+10, 0xFFFFFF);
                    mc.fontRendererObj.drawStringWithShadow("§6§l附近的玩家列表:", TextPosition[0], TextPosition[1]+20, 0xFFFFFF);

                }else{
                    mc.fontRendererObj.drawStringWithShadow("§620格内:§a"+playerin20+"§6   50格内:§a"+playerin50+"§6   100格内:§a"+playerin100, TextPosition[0], TextPosition[1]+10, 0xFFFFFF);
                    mc.fontRendererObj.drawStringWithShadow("§6§l附近的玩家列表:", TextPosition[0], TextPosition[1]+20, 0xFFFFFF);

                }

                if(PlayerList.isEmpty()){
                    mc.fontRendererObj.drawStringWithShadow("§c§l没有玩家", TextPosition[0], TextPosition[1]+30, 0xFFFFFF);
                }else{
                    for (int i =0;i<=PlayerList.size()-1;i++){
                        if(i>4){break;}
                        if(colorful){
                            int Dis = (int)Math.round(PlayerList.get(i).Distance);
                            if(Dis <=20){
                                mc.fontRendererObj.drawStringWithShadow("§c§l"+PlayerList.get(i).ID+" §7["+(int)Math.floor(PlayerList.get(i).X)+","+(int)Math.floor(PlayerList.get(i).Y)+","+(int)Math.floor(PlayerList.get(i).Z)+"] §c§l"+(int)Math.round(PlayerList.get(i).Distance)+" Blocks", TextPosition[0], TextPosition[1]+20+10*(i+1), 0xFFFFFF);
                            } else if (Dis >20 && Dis<= 50) {
                                mc.fontRendererObj.drawStringWithShadow("§6§l"+PlayerList.get(i).ID+" §7["+(int)Math.floor(PlayerList.get(i).X)+","+(int)Math.floor(PlayerList.get(i).Y)+","+(int)Math.floor(PlayerList.get(i).Z)+"] §6§l"+(int)Math.round(PlayerList.get(i).Distance)+" Blocks", TextPosition[0], TextPosition[1]+20+10*(i+1), 0xFFFFFF);
                            }else{
                                mc.fontRendererObj.drawStringWithShadow("§b§l"+PlayerList.get(i).ID+" §7["+(int)Math.floor(PlayerList.get(i).X)+","+(int)Math.floor(PlayerList.get(i).Y)+","+(int)Math.floor(PlayerList.get(i).Z)+"] §a§l"+(int)Math.round(PlayerList.get(i).Distance)+" Blocks", TextPosition[0], TextPosition[1]+20+10*(i+1), 0xFFFFFF);
                            }

                        }else{
                            mc.fontRendererObj.drawStringWithShadow("§b§l"+PlayerList.get(i).ID+" §7["+(int)Math.floor(PlayerList.get(i).X)+","+(int)Math.floor(PlayerList.get(i).Y)+","+(int)Math.floor(PlayerList.get(i).Z)+"] §a§l"+(int)Math.round(PlayerList.get(i).Distance)+" Blocks", TextPosition[0], TextPosition[1]+20+10*(i+1), 0xFFFFFF);

                        }
                    }
                }
            }
        }
    }

}
