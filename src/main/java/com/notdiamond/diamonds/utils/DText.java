package com.notdiamond.diamonds.utils;

import java.util.regex.Pattern;

public class DText
{
    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static String RemoveColor(String text){
        text = text.replaceAll("§1", "");
        text = text.replaceAll("§2", "");
        text = text.replaceAll("§3", "");
        text = text.replaceAll("§4", "");
        text = text.replaceAll("§5", "");
        text = text.replaceAll("§6", "");
        text = text.replaceAll("§7", "");
        text = text.replaceAll("§8", "");
        text = text.replaceAll("§9", "");
        text = text.replaceAll("§0", "");
        text = text.replaceAll("§a", "");
        text = text.replaceAll("§b", "");
        text = text.replaceAll("§c", "");
        text = text.replaceAll("§d", "");
        text = text.replaceAll("§e", "");
        text = text.replaceAll("§f", "");
        text = text.replaceAll("§r", "");
        text = text.replaceAll("§n", "");
        text = text.replaceAll("§m", "");
        text = text.replaceAll("§o", "");
        text = text.replaceAll("§r", "");
        text = text.replaceAll("§k", "");
        return text;
    }
}
