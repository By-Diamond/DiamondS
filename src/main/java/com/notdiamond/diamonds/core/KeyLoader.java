package com.notdiamond.diamonds.core;


import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyLoader {
    public static KeyBinding HUDUP;
    public static KeyBinding HUDDown;
    public static KeyBinding HUDEnter;
    public static KeyBinding HUDBack;
    public static KeyBinding FunctionSet;
    public static KeyBinding GhostBlock;
    public KeyLoader() {
        KeyLoader.HUDUP = new KeyBinding("DiamondS HUD 上移", Keyboard.KEY_UP, "DiamondS Mod 快捷键设置");
        KeyLoader.HUDDown = new KeyBinding("DiamondS HUD 下移", Keyboard.KEY_DOWN, "DiamondS Mod 快捷键设置");
        KeyLoader.HUDEnter = new KeyBinding("DiamondS HUD 选中", Keyboard.KEY_RIGHT, "DiamondS Mod 快捷键设置");
        KeyLoader.HUDBack = new KeyBinding("DiamondS HUD 返回", Keyboard.KEY_LEFT, "DiamondS Mod 快捷键设置");
        KeyLoader.FunctionSet = new KeyBinding("DiamondS HUD 进入功能设置", Keyboard.KEY_RSHIFT, "DiamondS Mod 快捷键设置");
        KeyLoader.GhostBlock = new KeyBinding("DiamondS 创建 GhostBlock", Keyboard.KEY_G, "DiamondS Mod 快捷键设置");
    }
}
