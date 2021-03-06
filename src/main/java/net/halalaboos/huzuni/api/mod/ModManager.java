package net.halalaboos.huzuni.api.mod;

import com.google.gson.JsonObject;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.mod.keybind.BasicKeybind;
import net.halalaboos.huzuni.api.node.JsonFileHandler;
import net.halalaboos.huzuni.api.node.Node;
import net.halalaboos.huzuni.api.mod.organize.AlphabeticalOrganizer;
import net.halalaboos.mcwrapper.api.event.input.KeyboardEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.getEventManager;

/**
 * Manager class provided to load, save, and provide easy access to {@link Mod}s within the application.
 * */
public final class ModManager extends JsonFileHandler {

	private final List<Mod> mods = new ArrayList<>();
	
	public ModManager(Huzuni huzuni) {
		super(huzuni, null);
	}

	@Override
	public void init() {
		getEventManager().subscribe(KeyboardEvent.class, event -> {
			for (Mod mod : mods) {
				if (mod instanceof BasicMod) {
					BasicMod basicMod = (BasicMod) mod;
					if (basicMod.getKeybind().getKeycode() == event.getKeyCode()) {
						basicMod.getKeybind().pressed();
					}
				}
			}
		});
	}

	@Override
	protected void save(List<JsonObject> objects) throws IOException {
		for (Mod mod : mods) {
			JsonObject object = new JsonObject();
			mod.save(object);
			objects.add(object);
		}
	}

	@Override
	protected void load(JsonObject object) throws IOException {
		for (Mod mod : mods) {
			if (mod.hasNode(object)) {
				mod.load(object);
				return;
			}
		}
	}
	
	@Override
	public void load() {
		super.load();
		new AlphabeticalOrganizer(false).organize(mods);
	}
	
	/**
	 * @return The {@link BasicKeybind} located within the given mod.
	 * */
	public BasicKeybind getKeybind(Mod mod) {
		for (Node child : mod.settings.getChildren()) {
			if (child instanceof BasicKeybind) {
				return (BasicKeybind) child;
			}
		}
		return null;
	}
	
	public void addMod(Mod mod) {
		if (!mods.contains(mod))
			mods.add(mod);
	}

	public void removeMod(Mod mod) {
		mods.remove(mod);
	}
	
	public Mod getMod(String name) {
		for (Mod mod : mods)
			if (mod.getName().equals(name))
				return mod;
		return null;
	}
	
	/**
	 * @return The {@link Mod} loaded which is assignable from the {@code Class} provided. Returns null if the {@code Class} has not been loaded.
	 * @param clazz The {@link Mod} class to be retrieved.
	 * */
	public <T extends Mod> T getMod(Class<T> clazz) {
		for (Mod mod : mods)
			if (mod.getClass().isAssignableFrom(clazz))
				return (T) mod;
		return null;
	}
	
	/**
	 * @return A {@link Mod} associated with the {@link String} provided, ignores casing and spaces.
	 * @param name The name of the mod.
	 * */
	public Mod getModIgnoreCase(String name) {
		name = name.replaceAll(" ", "");
		for (Mod mod : mods)
			if (mod.getName().replaceAll(" ", "").equalsIgnoreCase(name))
				return mod;
		return null;
	}

	/**
     * Replaces the first instance of a given mod type with the mod provided.
     * */
	public <T extends Mod> boolean replaceMod(Class<T> type, T mod) {
        T replaced = getMod(type);
        if (replaced != null) {
            replaced.setEnabled(false);
            mods.set(mods.indexOf(replaced), mod);
            return true;
        }
        return false;
    }
	
	public List<Mod> getMods() {
		return mods;
	}

}
