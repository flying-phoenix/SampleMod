package com.tan90.examplemod.client.handler;

import com.tan90.examplemod.client.keybindings.KeyBindings;
import com.tan90.examplemod.network.MessageExplode;
import com.tan90.examplemod.network.NetworkHandler;
import com.tan90.examplemod.reference.Key;
import com.tan90.examplemod.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputEventHandler
{
    private static Key getPressedKeyBinding()
    {
        if (KeyBindings.explode.isPressed())
        {
            return Key.EXPLODE;
        } else if (KeyBindings.bigExplode.isPressed())
        {
            return Key.BIG_EXPLODE;
        }

        return null;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        Key pressedKeyBinding = getPressedKeyBinding();
        if (pressedKeyBinding != null)
        {
            switch (pressedKeyBinding)
            {
                case EXPLODE:
                    LogHelper.info(pressedKeyBinding);
                    NetworkHandler.sendToServer(new MessageExplode(3));
                    break;
                case BIG_EXPLODE:
                    LogHelper.info(pressedKeyBinding);
                    NetworkHandler.sendToServer(new MessageExplode(50));
                    break;
            }
        }
    }
}
