package io.github.haykam821.modviewer.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import io.github.haykam821.modviewer.ModViewer;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public final class ModViewerCommand {
	private ModViewerCommand() {
		return;
	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal(ModViewer.MOD_ID)
			.requires(Permissions.require("modviewer.command", 2))
			.executes(ModViewerCommand::execute);

		dispatcher.register(builder);
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ModViewerUi ui = new ModViewerUi(context.getSource().getPlayer());
		ui.open();

		return Command.SINGLE_SUCCESS;
	}
}
