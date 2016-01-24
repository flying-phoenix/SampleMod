package com.tan90.examplemod.event;

import com.tan90.examplemod.ExampleMod;
import com.tan90.examplemod.entity.ExtendedPlayer;
import com.tan90.examplemod.init.ModItems;
import com.tan90.examplemod.network.MessageSetSlotNumber;
import com.tan90.examplemod.network.NetworkHandler;
import com.tan90.examplemod.proxy.ServerProxy;
import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.reference.Textures;
import com.tan90.examplemod.util.LogHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

public class ExampleModEventHandler
{
    @SubscribeEvent
    public void addDrops(LivingDropsEvent event)
    {
        LogHelper.info("Items Dropped");
        if (event.entity instanceof EntityCow)
        {
            if (!((EntityCow) event.entity).isChild() && ((EntityCow) event.entity).getRNG().nextInt(10) == 0)
            {
                event.drops.add(1, new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.watchItem, 1)));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {
//        Minecraft mc = Minecraft.getMinecraft();
//        MovingObjectPosition objMouseOver = mc.objectMouseOver;
//
//        if (event.side == Side.SERVER && event.phase == TickEvent.Phase.START)
//        {
//            if(objMouseOver != null)
//            {
//                if(objMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
//                {
//                    mc.theWorld.setBlock(objMouseOver.blockX, objMouseOver.blockY, objMouseOver.blockZ, Blocks.grass);
//                }
//            }else
//            {
//                LogHelper.info("objectMouseOver == null");
//            }
//        }
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent event)
    {
        Entity entity = event.entity;

        if (entity instanceof EntityCreeper)
        {
            ((EntityCreeper) entity).explosionRadius = 1000;

            try
            {
                Field field = ReflectionHelper.findField(EntityCreeper.class, "field_82225_f", "fuseTime");
                field.set(event.entity, 80);

            } catch (Throwable e)
            {
                LogHelper.warn("Reflection failed on Creeper fuseTime.");
            }
        }else if(entity instanceof EntityPlayer)
        {
            if(!event.entity.worldObj.isRemote)
            {
                NetworkHandler.sendTo(new MessageSetSlotNumber(ExtendedPlayer.get((EntityPlayer) event.entity).getCurrSlots()), (net.minecraft.entity.player.EntityPlayerMP) event.entity);
            }

            NBTTagCompound playerData = ExampleMod.proxy.getPlayerData(event.entity.getCommandSenderName() + Names.ExtendedProperties.PLAYER);
            if(playerData != null)
            {
                ExtendedPlayer.get((EntityPlayer) event.entity).setCurrSlots(playerData.getInteger("slotsNumber"));
            }
        }
    }

    @SubscribeEvent
    public void onEntityDied(LivingDeathEvent event)
    {
        if(event.entity instanceof EntityPlayer)
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("slotsNumber", ExtendedPlayer.get((EntityPlayer) event.entity).getCurrSlots());

            ExampleMod.proxy.setPlayerData(event.entity.getCommandSenderName() + Names.ExtendedProperties.PLAYER, tag);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null)
        {
            ExtendedPlayer.register((EntityPlayer) event.entity, ((EntityPlayer) event.entity).getEntityWorld());
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderGameOverlay(RenderGameOverlayEvent event)
    {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE)
        {
            return;
        }

        int xPos = 2;
        int yPos = 2;

        Collection collection = Minecraft.getMinecraft().thePlayer.getActivePotionEffects();

        if (!collection.isEmpty())
        {
            for (Iterator iterator = collection.iterator(); iterator.hasNext(); yPos += 10)
            {
                PotionEffect potionEffect = (PotionEffect) iterator.next();

                Minecraft.getMinecraft().fontRenderer.drawString(I18n.format(potionEffect.getEffectName()), xPos, yPos, 0x404040);
            }
        }
    }
}
