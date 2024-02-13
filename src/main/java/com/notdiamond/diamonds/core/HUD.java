package com.notdiamond.diamonds.core;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class HUD {

    public static int CurrectType =0;
    public static int CurrectFunction =-1;
    public static String CurrectText ="";
    public static int X=5;
    public static int Y=10;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.ElementType.TEXT) {
            if(CurrectText.isEmpty()){
                SetText();
            }
            if (Functions.GetStatus("HUD") && Minecraft.getMinecraft().fontRendererObj != null) {
                String[] parts = CurrectText.split("\n");
                int y =Y;
                for(int i=0;i<=parts.length-1;i++){
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(parts[i], X, y, 0xFFFFFF);
                    y=y+10;
                }
            }
        }
    }
    @SubscribeEvent
    public void OnKeyPressed(InputEvent.KeyInputEvent event){
        if(!Functions.GetStatus("HUD")){
            return;
        }
        if(KeyLoader.HUDUP.isPressed()){
            if(CurrectFunction == -1){
                CurrectType = CurrectType-1;
                if(CurrectType <= -1){
                    CurrectType = Functions.FunctionTypes.size()-1;
                }
            }else{
                CurrectFunction = CurrectFunction - 1;
                if(CurrectFunction <= -1){
                    CurrectFunction = Functions.FunctionTypes.get(CurrectType).GetFunctionList().size()-1;
                }
            }
        }
        if(KeyLoader.HUDDown.isPressed()){
            if(CurrectFunction == -1){
                CurrectType = CurrectType+1;
                if(CurrectType > Functions.FunctionTypes.size()-1){
                    CurrectType = 0;
                }
            }else{
                CurrectFunction = CurrectFunction + 1;
                if(CurrectFunction > Functions.FunctionTypes.get(CurrectType).GetFunctionList().size()-1){
                    CurrectFunction = 0;
                }
            }
        }
        if(KeyLoader.HUDEnter.isPressed()){
            if(CurrectFunction == -1){
                CurrectFunction = 0;
            }else{
                String theFunction;
                theFunction=Functions.FunctionTypes.get(CurrectType).GetFunctionList().get(CurrectFunction);
                Functions.FunctionSwitch(theFunction);
            }
        }
        if(KeyLoader.HUDBack.isPressed()){
            if(CurrectFunction != -1){
                CurrectFunction = -1;
            }
        }
        SetText();
    }
    private void SetText(){
        String TempText;
        String TotalText ="";
        if(CurrectFunction == -1){
            for (int i=0;i<=Functions.FunctionTypes.size()-1;i++){
                TempText = "§b[类]§6 "+Functions.FunctionTypes.get(i).GetName();
                if(i==CurrectType){
                    TempText = "§b§l> §r"+TempText;
                }
                if(i==0){
                    TotalText = "§b§lDiamondS §r§bMenu\n"+TempText;
                }else{
                    TotalText += "\n"+TempText;
                }
            }
            CurrectText = TotalText;
        }else{
            DiamondSFunctionType TypeTemp = Functions.FunctionTypes.get(CurrectType);
            for (int i=0;i<=TypeTemp.GetFunctionList().size()-1;i++){
                String TempFunction =TypeTemp.GetFunctionList().get(i);
                if(Functions.GetStatus(TempFunction)){
                    TempText = "§b[功能]§a "+TempFunction;
                }else{
                    TempText = "§b[功能]§c "+TempFunction;
                }

                if(i==CurrectFunction){
                    TempText = "§b§l> §r"+TempText;
                }
                if(i==0){
                    TotalText = "§b§l"+Functions.FunctionTypes.get(CurrectType).GetName()+"\n"+TempText;
                }else{
                    TotalText += "\n"+TempText;
                }
            }
            CurrectText = TotalText;
        }
    }
}
