package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.player.PostMotionUpdateEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Forces the player to sneak.
 * */
public class Sneak extends BasicMod {
	
	public Sneak() {
		super("Sneak", "Forces you to sneak", Keyboard.KEY_Z);
		setAuthor("brudin");
		this.setCategory(Category.MOVEMENT);
		subscribe(PostMotionUpdateEvent.class, event ->
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true));
	}
	
	@Override
	public void onDisable() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
	}
}
