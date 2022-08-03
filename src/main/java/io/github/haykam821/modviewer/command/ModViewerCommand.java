package io.github.haykam821.modviewer.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import io.github.haykam821.modviewer.ModViewer;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public final class ModViewerCommand {
	public static final SimpleCommandExceptionType NO_VIEWABLE_MODS = new SimpleCommandExceptionType(Text.translatable("text.modviewer.command.no_viewable_mods"));

	private ModViewerCommand() {
		return;
	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal(ModViewer.MOD_ID)
			.requires(ModViewerPermissions::canUseCommand)
			.executes(ModViewerCommand::execute);

		dispatcher.register(builder);
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ModViewerUi ui = new ModViewerUi(context.getSource().getPlayer());
		ui.open();

		return Command.SINGLE_SUCCESS;
	}
}
