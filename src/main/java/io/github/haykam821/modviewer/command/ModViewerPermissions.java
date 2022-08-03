package io.github.haykam821.modviewer.command;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public final class ModViewerPermissions {
	private static final int DEFAULT_REQUIRED_LEVEL = 2;

	private static final String COMMAND_KEY = "modviewer.command";
	private static final String VIEW_MOD_KEY_PREFIX = "modviewer.view_mod.";

	public static boolean canUseCommand(ServerCommandSource source) {
		return Permissions.check(source, COMMAND_KEY, DEFAULT_REQUIRED_LEVEL);
	}

	public static boolean canViewMod(ServerPlayerEntity player, ModContainer mod) {
		if (mod == null) {
			return true;
		}

		String key = VIEW_MOD_KEY_PREFIX + mod.getMetadata().getId();
		return Permissions.check(player, key, DEFAULT_REQUIRED_LEVEL);
	}

	private ModViewerPermissions() {
		return;
	}
}
