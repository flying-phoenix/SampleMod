package com.tan90.examplemod;

import com.tan90.examplemod.client.handler.KeyInputEventHandler;
import com.tan90.examplemod.event.ExampleModEventHandler;
import com.tan90.examplemod.handler.ConfigurationHandler;
import com.tan90.examplemod.handler.GuiHandler;
import com.tan90.examplemod.init.ModBlocks;
import com.tan90.examplemod.init.ModEntities;
import com.tan90.examplemod.init.ModItems;
import com.tan90.examplemod.init.ModTileEntities;
import com.tan90.examplemod.network.DescriptionPacketHandler;
import com.tan90.examplemod.network.NetworkHandler;
import com.tan90.examplemod.proxy.CommonProxy;
import com.tan90.examplemod.reference.ModIds;
import com.tan90.examplemod.reference.Reference;
import com.tan90.examplemod.tileentity.TileEntityMine;
import com.tan90.examplemod.util.LogHelper;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class ExampleMod
{
    public static boolean thaumcraftLoaded = false;

    @Instance(Reference.MOD_ID)
    public static ExampleMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        if (Loader.isModLoaded(ModIds.THAUMCRAFT))
        {
            thaumcraftLoaded = true;
        }

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        ModBlocks.init();

        ModItems.init();

        ModTileEntities.init();

        ModEntities.init();

//        GameRegistry.registerWorldGenerator(new WorldGeneratorAsh(), 0);
        MinecraftForge.EVENT_BUS.register(new ExampleModEventHandler());
        FMLCommonHandler.instance().bus().register(new ExampleModEventHandler());

        proxy.registerKeybindings();

        proxy.initRenderingAndTextures();

        NetworkHandler.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        DescriptionPacketHandler.init();


        FMLInterModComms.sendMessage("ExampleMod", "camouflageBlackList", new ItemStack(Blocks.hopper));

        FMLInterModComms.sendMessage("Waila", "register", "com.tan90.examplemod.thirdparty.waila.Waila.onWailaCall");

        if (thaumcraftLoaded)
        {
            loadThaumcraft();
        }
    }

    @Optional.Method(modid = ModIds.THAUMCRAFT)
    private void loadThaumcraft()
    {
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.watchItem), new AspectList().add(Aspect.VOID, 5).add(Aspect.GREED, 10));
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void onIMCMessages(IMCEvent event)
    {
        for (IMCMessage message : event.getMessages())
        {
            if (message.isItemStackMessage() && message.key.equalsIgnoreCase("camouflageBlackList"))
            {
                ItemStack blackListedStack = message.getItemStackValue();
                if (blackListedStack.getItem() != null)
                {
                    if (blackListedStack.getItem() instanceof ItemBlock)
                    {
                        TileEntityMine.blackList.add(blackListedStack);
                        LogHelper.info(String.format("%s just added %s to camouflage blacklist.", message.getSender(), blackListedStack.getDisplayName()));
                    } else
                    {
                        LogHelper.info(String.format("%s tried to blacklist non ItemBlock stack for camouflage. All non ItemBlocks are already blacklisted.", message.getSender()));
                    }
                } else
                {
                    throw new IllegalStateException(String.format("%s tried to blacklist a null item for camouflage.", message.getSender()));
                }
            } else
            {
                LogHelper.warn(String.format("%s used illegal key: %s", message.getSender(), message.key));
            }
        }
    }
}
