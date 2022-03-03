package io.github.haykam821.modviewer.ui.element;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementInterface.ClickCallback;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.CustomValue.CvType;
import net.fabricmc.loader.api.metadata.ModEnvironment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack.TooltipSection;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;

public final class ModElement {
	private static final TextColor NAME_COLOR = TextColor.fromRgb(0xF4B41C);

	private ModElement() {
		return;
	}

	public static GuiElement of(ModViewerUi ui, ModContainer mod, boolean detailed) {
		GuiElementBuilder builder = new GuiElementBuilder(ModElement.getItem(mod.getMetadata()))
			.hideFlag(TooltipSection.ADDITIONAL);

		ModElement.buildInfo(builder, mod.getMetadata(), detailed);

		if (ui != null) {
			builder.setCallback(ModElement.createCallback(ui, mod));
			if (mod == ui.getViewedMod()) {
				builder.glow();
			}
		}

		return builder.build();
	}

	public static void buildInfo(GuiElementBuilder builder, ModMetadata metadata, boolean detailed) {
		String id = metadata.getId();
		String name = metadata.getName();

		builder.setName(ModElement.getName(name == null ? id : name));
		if (name != null) {
			builder.addLoreLine(ElementUtil.getLoreLine("text.modviewer.ui.grid.mod.id", id));
		}

		String version = metadata.getVersion().getFriendlyString();
		builder.addLoreLine(ElementUtil.getLoreLine("text.modviewer.ui.grid.mod.version", version));

		if (detailed) {
			MutableText environment = ModElement.getEnvironmentName(metadata);
			builder.addLoreLine(ElementUtil.getLoreLine("text.modviewer.ui.grid.mod.environment", environment));

			String license = String.join(", ", metadata.getLicense());
			if (!license.isBlank()) {
				builder.addLoreLine(ElementUtil.getLoreLine("text.modviewer.ui.grid.mod.license", license));
			}
		}
	}

	public static Item getItem(ModMetadata metadata) {
		String id = metadata.getId();

		if ("minecraft".equals(id)) {
			return Items.GRASS_BLOCK;
		} else if ("java".equals(id)) {
			return Items.LAVA_BUCKET;
		} else if (ModElement.isLibrary(metadata)) {
			return Items.BOOK;
		} else {
			return Items.CREEPER_BANNER_PATTERN;
		}
	}

	public static boolean isLibrary(ModMetadata metadata) {
		String id = metadata.getId();
		if (id.startsWith("fabric")) {
			if (metadata.containsCustomValue("fabric-api:module-lifecycle")) {
				return true;
			} else if ("fabricloader".equals(id) || "fabric".equals(id) || "fabric-api".equals(id)) {
				return true;
			} else if (metadata.getProvides().contains("fabricloader") || metadata.getProvides().contains("fabric") || metadata.getProvides().contains("fabric-api")) {
				return true;
			}
		} else if ("java".equals(id)) {
			return true;
		}

		CustomValue modMenu = metadata.getCustomValue("modmenu");
		if (modMenu != null && modMenu.getType() == CvType.OBJECT) {
			CustomValue badges = modMenu.getAsObject().get("badges");
			if (badges != null && badges.getType() == CvType.ARRAY) {
				for (CustomValue badge : badges.getAsArray()) {
					if ("library".equals(badge.getAsString())) {
						return true;
					}
				}
			}
		}

		CustomValue generated = metadata.getCustomValue("fabric-loom:generated");
		if (generated != null && generated.getType() == CvType.BOOLEAN && generated.getAsBoolean()) {
			return true;
		}

		return false;
	}

	public static MutableText getName(String name) {
		return new LiteralText(name).styled(style -> {
			return style.withColor(NAME_COLOR);
		});
	}

	public static MutableText getEnvironmentName(ModMetadata metadata) {
		ModEnvironment environment = metadata.getEnvironment();
		switch (environment) {
			case CLIENT:
				return new TranslatableText("text.modviewer.ui.grid.mod.environment.client");
			case SERVER:
				return new TranslatableText("text.modviewer.ui.grid.mod.environment.server");
			case UNIVERSAL:
				return new TranslatableText("text.modviewer.ui.grid.mod.environment.universal");
			default:
				return null;
		}
	}

	private static ClickCallback createCallback(ModViewerUi ui, ModContainer mod) {
		return (index, type, action, guiInterface) -> {
			ui.setViewedMod(mod);
			ElementUtil.playSelectSound(ui);
		};
	}
}
