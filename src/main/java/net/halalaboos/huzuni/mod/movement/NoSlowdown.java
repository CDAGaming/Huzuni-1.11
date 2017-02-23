package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager;
import net.halalaboos.huzuni.api.event.PlayerMoveEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Toggleable;
import net.halalaboos.huzuni.api.settings.Value;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class NoSlowdown extends BasicMod {

	private final Toggleable ITEM_USE = new Toggleable("Item Use", "Move at normal speeds using items.");

	//todo
	private final Value ICE_SPEED = new Value("Ice Speed", 0.5F, 1F, 1F, 0.1F, "Speed on ice");

	public NoSlowdown() {
		super("No slowdown", "Prevents various things from slowing you down.");
		setAuthor("brudin");
		setCategory(Category.MOVEMENT);
		addChildren(ITEM_USE/*, ICE_SPEED*/);
	}

	@EventManager.EventMethod
	public void onMove(PlayerMoveEvent event) {
		getPlayer().setItemUseSlowdown(!ITEM_USE.isEnabled());
	}

	@Override
	protected void onEnable() {
		huzuni.eventManager.addListener(this);
	}

	@Override
	protected void onDisable() {
		huzuni.eventManager.removeListener(this);
		getPlayer().setItemUseSlowdown(true);
	}
}