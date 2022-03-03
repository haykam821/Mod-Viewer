package io.github.haykam821.modviewer;

import io.github.haykam821.modviewer.command.ModViewerCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModViewer implements ModInitializer {
	public static final String MOD_ID = "modviewer";

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			ModViewerCommand.register(dispatcher);
		});
	}
}
