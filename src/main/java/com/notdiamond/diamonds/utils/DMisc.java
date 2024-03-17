package com.notdiamond.diamonds.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class DMisc {
    private static final String[] filter = new String[]{" ","Goblin","Weakling","Kalhuiki","CreeperTam","Pitfighter","Sentry","Scarecrow"};

    public static boolean CheckNPC(Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        } else {
            if(DText.GetLengthAfterDot(String.valueOf(((EntityOtherPlayerMP) entity).getHealth())) >= 6){
                return true;
            }
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            return entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0F;
        }
    }

    public static boolean isValid(EntityPlayer player){
        boolean valid = true;
        for(String Word : filter){
            if(player.getName().toLowerCase().contains(Word.toLowerCase()) || player == Minecraft.getMinecraft().thePlayer){
                valid = false;
                break;
            }
        }
        if(player.isInvisible() && valid) {
            valid = player.getEquipmentInSlot(0) != null || player.getEquipmentInSlot(1) != null || player.getEquipmentInSlot(2) != null || player.getEquipmentInSlot(3) != null || player.getEquipmentInSlot(4) != null;
        }
        return valid;
    }
}
