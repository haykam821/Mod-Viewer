package io.github.haykam821.modviewer.ui.element;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementInterface.ClickCallback;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public final class PageElement {
	private PageElement() {
		return;
	}

	public static GuiElement ofPrevious(ModViewerUi ui) {
		return PageElement.of(ui, "previous", -1, 0, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzEwODI5OGZmMmIyNjk1MWQ2ODNlNWFkZTQ2YTQyZTkwYzJmN2M3ZGQ0MWJhYTkwOGJjNTg1MmY4YzMyZTU4MyJ9fX0");
	}

	public static GuiElement ofNext(ModViewerUi ui) {
		return PageElement.of(ui, "next", 1, -1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg2MTg1YjFkNTE5YWRlNTg1ZjE4NGMzNGYzZjNlMjBiYjY0MWRlYjg3OWU4MTM3OGU0ZWFmMjA5Mjg3In19fQ");
	}

	private static GuiElement of(ModViewerUi ui, String type, int offset, int shiftPage, String texture) {
		return new GuiElementBuilder(Items.PLAYER_HEAD)
			.setName(ElementUtil.getToolbarName("spectatorMenu." + type + "_page"))
			.setSkullOwner(texture)
			.setCallback(PageElement.createCallback(ui, offset, shiftPage))
			.build();
	}

	private static ClickCallback createCallback(ModViewerUi ui, int offset, int shiftPage) {
		return (index, type, action, guiInterface) -> {
			int previousPage = ui.getPage();
			if (action == SlotActionType.QUICK_MOVE) {
				ui.setWrappedPage(shiftPage);
			} else {
				ui.movePage(offset);
			}

			if (previousPage != ui.getPage()) {
				ElementUtil.playClickSound(ui);
			}
		};
	}
}
