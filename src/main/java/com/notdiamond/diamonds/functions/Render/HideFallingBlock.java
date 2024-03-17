package com.notdiamond.diamonds.functions.Render;

import com.notdiamond.diamonds.core.Function.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static com.notdiamond.diamonds.DiamondS.mc;

public class HideFallingBlock {
    @SubscribeEvent
    public void getFallingBlock(RenderWorldLastEvent event) {
        //来自绿猫 qwq
        if (Functions.GetStatus("HideFallingBlock")) {
            double x = Minecraft.getMinecraft().thePlayer.posX;
            double y = Minecraft.getMinecraft().thePlayer.posY;
            double z = Minecraft.getMinecraft().thePlayer.posZ;
            List<EntityFallingBlock> entityList = mc.theWorld.getEntitiesWithinAABB(EntityFallingBlock.class, new AxisAlignedBB(x - (500 / 2d), y - (256 / 2d), z - (500 / 2d), x + (500 / 2d), y + (256 / 2d), z + (500 / 2d)), null);
            for(Entity entity : entityList){
                if(entity instanceof EntityFallingBlock){
                    Minecraft.getMinecraft().theWorld.removeEntity(entity);
                }
            }
        }
    }
}
