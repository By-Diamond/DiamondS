package com.notdiamond.diamonds.utils;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;

public class DText {
    public static String getSubString(String text, String left, String right) {
        String result;
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
        if (yLen < 0 || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }
    public static String AddColor(String text) {
        text = text.replaceAll("&1", "§1");
        text = text.replaceAll("&2", "§2");
        text = text.replaceAll("&3", "§3");
        text = text.replaceAll("&4", "§4");
        text = text.replaceAll("&5", "§5");
        text = text.replaceAll("&6", "§6");
        text = text.replaceAll("&7", "§7");
        text = text.replaceAll("&8", "§8");
        text = text.replaceAll("&9", "§9");
        text = text.replaceAll("&0", "§0");
        text = text.replaceAll("&a", "§a");
        text = text.replaceAll("&b", "§b");
        text = text.replaceAll("&c", "§c");
        text = text.replaceAll("&d", "§d");
        text = text.replaceAll("&e", "§e");
        text = text.replaceAll("&f", "§f");
        text = text.replaceAll("&r", "§r");
        text = text.replaceAll("&n", "§n");
        text = text.replaceAll("&m", "§m");
        text = text.replaceAll("&o", "§o");
        text = text.replaceAll("&r", "§r");
        text = text.replaceAll("&k", "§k");
        return text;
    }
    public static String RemoveColor(String text) {
//        text = text.replaceAll("§1", "");
//        text = text.replaceAll("§2", "");
//        text = text.replaceAll("§3", "");
//        text = text.replaceAll("§4", "");
//        text = text.replaceAll("§5", "");
//        text = text.replaceAll("§6", "");
//        text = text.replaceAll("§7", "");
//        text = text.replaceAll("§8", "");
//        text = text.replaceAll("§9", "");
//        text = text.replaceAll("§0", "");
//        text = text.replaceAll("§a", "");
//        text = text.replaceAll("§b", "");
//        text = text.replaceAll("§c", "");
//        text = text.replaceAll("§d", "");
//        text = text.replaceAll("§e", "");
//        text = text.replaceAll("§f", "");
//        text = text.replaceAll("§r", "");
//        text = text.replaceAll("§n", "");
//        text = text.replaceAll("§m", "");
//        text = text.replaceAll("§o", "");
//        text = text.replaceAll("§r", "");
//        text = text.replaceAll("§k", "");
        return ChatFormatting.stripFormatting(text);
    }

    /*
        替换多个文字时，可以使用"|"连接
        例如：Hello|World，将会同时替换Hello和World为Replacement
     */
    public static String Replace(String Msg, String Content, String Replacement) {
        Pattern pattern = Pattern.compile("(?i)(" + Content + ")", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Msg);
        return matcher.replaceAll(Replacement);
    }

    public static int GetRandomNumber(int min,int max){
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }

    public static String TimeFormat(int second){
        int h,m,s = 0;
        s=second;
        m = (int) Math.floor(s/60);
        h =(int) Math.floor(m/60);
        s = s-60*m;
        m = m-60*h;
        String hs,ms,ss = "";
        if(s>0){
            ss= s +"s";
        }else{
            ss = "";
        }
        if(m>0){
            ms= m +"m";
        }else{
            ms = "";
        }
        if(h>0){
            hs= h +"h";
        }else{
            hs = "";
        }
        String All = hs+ms+ss;
        if(All.contentEquals("")){
            return "0s";
        }
        return hs+ms+ss;
    }

    public static int GetLengthAfterDot(String Number) {
        //我还是更喜欢中文:)
        if (Number == null || !Number.contains(".")) {
            return 0;
        }
        int decimalIndex = Number.indexOf('.');
        int lengthAfterDecimal = Number.length() - decimalIndex - 1;
        return lengthAfterDecimal;
    }
}
