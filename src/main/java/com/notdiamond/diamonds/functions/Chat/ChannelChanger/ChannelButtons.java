package com.notdiamond.diamonds.functions.Chat.ChannelChanger;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ChannelButtons extends GuiChat {

    @Override
    public void initGui() {
        super.initGui();
        if(ChannelChanger.Commanding){
            ChannelChanger.Commanding = false;
            super.inputField.setText("/");
        }
        ScaledResolution scaled = new ScaledResolution(mc);
        buttonList.add(new GuiButton(0, 335, scaled.getScaledHeight() - 35 -100, 20, 20, "A"));
        buttonList.add(new GuiButton(1, 335, scaled.getScaledHeight() - 35 -75, 20, 20, "P"));
        buttonList.add(new GuiButton(2, 335, scaled.getScaledHeight() - 35 -50, 20, 20, "G"));
        buttonList.add(new GuiButton(3, 335, scaled.getScaledHeight() - 35 -25, 20, 20, "C"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.thePlayer.sendChatMessage("/chat a");
        }
        if (button.id == 1) {
            mc.thePlayer.sendChatMessage("/chat p");
        }
        if (button.id == 2) {
            mc.thePlayer.sendChatMessage("/chat g");
        }
        if (button.id == 3) {
            mc.thePlayer.sendChatMessage("/chat coop");
        }
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.0F); // 设置颜色为完全透明
        drawRect(0, 0, this.width, this.height, 0); // 绘制一个覆盖整个聊天栏的矩形
    }
    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        ChannelChanger.IsInChat = false;
    }


}



