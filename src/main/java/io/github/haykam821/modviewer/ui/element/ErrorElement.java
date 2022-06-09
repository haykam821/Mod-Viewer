package io.github.haykam821.modviewer.ui.element;

import java.util.function.Supplier;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import io.github.haykam821.modviewer.ModViewer;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class ErrorElement {
	private static final GuiElement INSTANCE = ErrorElement.of();

	private ErrorElement() {
		return;
	}

	public static GuiElement wrap(Supplier<GuiElement> elementSupplier) {
		try {
			return elementSupplier.get();
		} catch (Exception exception) {
			ModViewer.LOGGER.warn("Failed to resolve element", exception);
			return ErrorElement.INSTANCE;
		}
	}

	private static GuiElement of() {
		return new GuiElementBuilder(Items.POISONOUS_POTATO)
			.setName(Text.translatable("text.modviewer.ui.view.error").formatted(Formatting.YELLOW))
			.build();
	}
}
