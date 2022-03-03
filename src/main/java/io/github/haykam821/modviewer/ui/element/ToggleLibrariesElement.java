package io.github.haykam821.modviewer.ui.element;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementInterface.ClickCallback;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import net.minecraft.item.Items;

public final class ToggleLibrariesElement {
	private ToggleLibrariesElement() {
		return;
	}

	public static GuiElement of(ModViewerUi ui) {
		String translationKey = "text.modviewer.ui.toolbar.toggle_libraries";

		return new GuiElementBuilder(Items.HOPPER)
			.setName(ElementUtil.getToolbarName(translationKey))
			.addLoreLine(ElementUtil.getLoreLine(translationKey + "." + (ui.shouldShowLibraries() ? "showing" : "hiding")))
			.setCallback(ToggleLibrariesElement.createCallback(ui))
			.build();
	}

	private static ClickCallback createCallback(ModViewerUi ui) {
		return (index, type, action, guiInterface) -> {
			ui.setShowLibraries(!ui.shouldShowLibraries());
			ElementUtil.playFilterSound(ui);
		};
	}
}
