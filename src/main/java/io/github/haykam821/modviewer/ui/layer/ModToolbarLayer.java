package io.github.haykam821.modviewer.ui.layer;

import eu.pb4.sgui.api.elements.GuiElement;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import io.github.haykam821.modviewer.ui.element.PageElement;
import io.github.haykam821.modviewer.ui.element.ToggleLibrariesElement;

public class ModToolbarLayer extends AbstractModLayer {
	private final GuiElement previousPageElement;
	private final GuiElement nextPageElement;

	public ModToolbarLayer(ModViewerUi ui, int width) {
		super(ui, width, 1);

		this.previousPageElement = PageElement.ofPrevious(ui);
		this.nextPageElement = PageElement.ofNext(ui);
	}

	@Override
	public void update() {
		this.setSlot(0, this.previousPageElement);
		this.setSlot(1, ToggleLibrariesElement.of(ui));
		this.setSlot(2, this.nextPageElement);
	}
}
