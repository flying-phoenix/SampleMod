package com.tan90.examplemod.client.keybindings;

import com.tan90.examplemod.reference.Names;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBindings
{
    public static KeyBinding explode = new KeyBinding(Names.Keys.EXPLODE, Keyboard.KEY_V, Names.Keys.CATEGORY);
    public static KeyBinding bigExplode = new KeyBinding(Names.Keys.BIG_EXPLODE, Keyboard.KEY_R, Names.Keys.CATEGORY);
}
