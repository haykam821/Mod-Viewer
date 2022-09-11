package io.github.haykam821.modviewer.ui.element;

import java.util.Iterator;
import java.util.Optional;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class DependenciesElement {
	private static final Text UNFULFILLED_SYMBOL = Text.literal("❌").formatted(Formatting.RED);
	private static final Text PARTIALLY_FULFILLED_SYMBOL = Text.literal("⚠").formatted(Formatting.YELLOW);
	private static final Text FULFILLED_SYMBOL = Text.literal("✔").formatted(Formatting.GREEN);

	private DependenciesElement() {
		return;
	}

	public static GuiElement of(ModMetadata metadata, ModDependency.Kind kind, Item item) {
		GuiElementBuilder builder = new GuiElementBuilder(item);

		boolean included = false;
		for (ModDependency dependency : metadata.getDependencies()) {
			if (dependency.getKind() == kind) {
				included = true;
				builder.addLoreLine(DependenciesElement.getLoreLine(dependency));
			}
		}

		if (!included) {
			return null;
		}

		builder.setName(DependenciesElement.getName(kind));

		return builder.build();
	}

	private static Text getName(ModDependency.Kind kind) {
		String translationKey = "text.modviewer.ui.view." + kind.getKey();
		Formatting formatting = DependenciesElement.getFormatting(kind);

		return Text.translatable(translationKey).formatted(formatting);
	}

	private static Text getLoreLine(ModDependency dependency) {
		MutableText lore = Text.empty();

		Optional<ModContainer> mod = FabricLoader.getInstance().getModContainer(dependency.getModId());
		lore.append(DependenciesElement.getDependencyFulfillmentSymbol(mod, dependency));

		lore.append(" " + dependency.getModId());
		Formatting formatting = DependenciesElement.getFormatting(dependency.getKind());

		Iterator<VersionPredicate> iterator = dependency.getVersionRequirements().iterator();
		if (iterator.hasNext()) {
			lore.append(" ");

			while (iterator.hasNext()) {
				VersionPredicate predicate = iterator.next();
				lore.append(Text.literal(predicate.toString()).formatted(formatting));

				if (iterator.hasNext()) {
					lore.append(" || ");
				}
			}
		}

		return lore.formatted(Formatting.GRAY);
	}

	private static Formatting getFormatting(ModDependency.Kind kind) {
		return kind.isPositive() ? Formatting.GREEN : Formatting.RED;
	}

	private static Text getDependencyFulfillmentSymbol(Optional<ModContainer> mod, ModDependency dependency) {
		if (mod.isEmpty()) {
			return UNFULFILLED_SYMBOL;
		} else if (dependency.matches(mod.get().getMetadata().getVersion())) {
			return FULFILLED_SYMBOL;
		} else {
			return PARTIALLY_FULFILLED_SYMBOL;
		}
	}
}
