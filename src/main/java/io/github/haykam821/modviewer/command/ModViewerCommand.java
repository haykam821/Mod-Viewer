package io.github.haykam821.modviewer.command;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import io.github.haykam821.modviewer.ModViewer;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public final class ModViewerCommand {
	public static final SimpleCommandExceptionType NO_VIEWABLE_MODS = new SimpleCommandExceptionType(Text.translatable("text.modviewer.command.no_viewable_mods"));
	public static final DynamicCommandExceptionType UNKNOWN_MOD = new DynamicCommandExceptionType(mod -> Text.translatable("text.modviewer.command.unknown_mod", mod));

	private ModViewerCommand() {
		return;
	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal(ModViewer.MOD_ID)
			.requires(ModViewerPermissions::canUseCommand)
			.executes(ModViewerCommand::execute)
			.then(CommandManager.argument("mod", StringArgumentType.word())
				.suggests(ModViewerCommand::suggestMods)
				.executes(ModViewerCommand::executeSpecific));

		dispatcher.register(builder);
	}

	public static CompletableFuture<Suggestions> suggestMods(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
		FabricLoader.getInstance().getAllMods()
			.stream()
			.filter(mod -> ModViewerPermissions.canViewMod(context.getSource(), mod))
			.forEach(mod -> {
				ModMetadata metadata = mod.getMetadata();

				String id = metadata.getId();
				String name = metadata.getName();

				if (name == null) {
					builder.suggest(id);
				} else {
					builder.suggest(id, Text.literal(name));
				}
			});

		return builder.buildFuture();
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ModViewerUi ui = new ModViewerUi(context.getSource().getPlayer());
		ui.open();

		return Command.SINGLE_SUCCESS;
	}

	public static int executeSpecific(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		String modId = StringArgumentType.getString(context, "mod");

		ModContainer mod = FabricLoader.getInstance().getModContainer(modId)
			.filter(modx -> ModViewerPermissions.canViewMod(context.getSource(), modx))
			.orElseThrow(() -> UNKNOWN_MOD.create(modId));

		ModViewerUi ui = new ModViewerUi(context.getSource().getPlayer());
		ui.setViewedMod(mod, true);
		ui.open();

		return Command.SINGLE_SUCCESS;
	}
}
