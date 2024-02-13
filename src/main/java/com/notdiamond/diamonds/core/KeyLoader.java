package com.notdiamond.diamonds.core;


import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyLoader {
    public static KeyBinding HUDUP;
    public static KeyBinding HUDDown;
    public static KeyBinding HUDEnter;
    public static KeyBinding HUDBack;
    public KeyLoader() {
        KeyLoader.HUDUP = new KeyBinding("DiamondS HUD上移", Keyboard.KEY_UP, "DiamondS Mod");
        KeyLoader.HUDDown = new KeyBinding("DiamondS HUD下移", Keyboard.KEY_DOWN, "DiamondS Mod");
        KeyLoader.HUDEnter = new KeyBinding("DiamondS HUD选中", Keyboard.KEY_RIGHT, "DiamondS Mod");
        KeyLoader.HUDBack = new KeyBinding("DiamondS HUD返回", Keyboard.KEY_LEFT, "DiamondS Mod");
    }
}
