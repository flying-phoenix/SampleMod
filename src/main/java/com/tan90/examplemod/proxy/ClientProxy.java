package com.tan90.examplemod.proxy;

import com.tan90.examplemod.client.gui.GuiSlotNumberBar;
import com.tan90.examplemod.client.keybindings.KeyBindings;
import com.tan90.examplemod.client.model.ModelDroid;
import com.tan90.examplemod.client.renderer.RenderDroid;
import com.tan90.examplemod.client.renderer.RenderDroidItem;
import com.tan90.examplemod.entity.EntityDroid;
import com.tan90.examplemod.init.ModItems;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
    GuiSlotNumberBar guiSlotNumberBar;

    @Override
    public void initRenderingAndTextures()
    {
        ModelDroid droid = new ModelDroid();
        RenderingRegistry.registerEntityRenderingHandler(EntityDroid.class, new RenderDroid(droid));
        MinecraftForgeClient.registerItemRenderer(ModItems.droidItem, new RenderDroidItem(droid));

        guiSlotNumberBar = new GuiSlotNumberBar(Minecraft.getMinecraft());
        MinecraftForge.EVENT_BUS.register(guiSlotNumberBar);

    }

    @Override
    public void registerEventHandlers()
    {

    }

    @Override
    public void registerKeybindings()
    {
        ClientRegistry.registerKeyBinding(KeyBindings.explode);
        ClientRegistry.registerKeyBinding(KeyBindings.bigExplode);
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }
}
