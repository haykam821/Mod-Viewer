package io.github.haykam821.modviewer.ui.layer;

import eu.pb4.sgui.api.gui.layered.Layer;
import io.github.haykam821.modviewer.ui.ModViewerUi;

public abstract class AbstractModLayer extends Layer {
	protected final ModViewerUi ui;

	public AbstractModLayer(ModViewerUi ui, int width, int height) {
		super(height, width);
		this.ui = ui;
	}

	public abstract void update();
}