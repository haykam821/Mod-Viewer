package io.github.haykam821.modviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.haykam821.modviewer.command.ModViewerCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModViewer implements ModInitializer {
	public static final String MOD_ID = "modviewer";
	public static final Logger LOGGER = LoggerFactory.getLogger("Mod Viewer");

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			ModViewerCommand.register(dispatcher);
		});
	}
}
