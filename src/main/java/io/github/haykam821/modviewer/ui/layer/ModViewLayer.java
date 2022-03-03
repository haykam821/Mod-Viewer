package io.github.haykam821.modviewer.ui.layer;

import eu.pb4.sgui.api.elements.GuiElement;
import io.github.haykam821.modviewer.ui.ModViewerUi;
import io.github.haykam821.modviewer.ui.element.AuthorsElement;
import io.github.haykam821.modviewer.ui.element.DependenciesElement;
import io.github.haykam821.modviewer.ui.element.DescriptionElement;
import io.github.haykam821.modviewer.ui.element.ModElement;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.item.Items;

public class ModViewLayer extends AbstractModLayer {
	public ModViewLayer(ModViewerUi ui, int width, int height) {
		super(ui, width, height);
	}

	@Override
	public void update() {
		this.clearSlots();

		ModContainer mod = this.ui.getViewedMod();
		ModMetadata metadata = mod.getMetadata();

		this.set(0, 1, ModElement.of(null, mod, true));

		if (!metadata.getDescription().isBlank()) {
			this.set(1, 1, DescriptionElement.of(metadata));
		}

		if (!metadata.getAuthors().isEmpty() || !metadata.getContributors().isEmpty()) {
			this.set(3, 1, AuthorsElement.of(metadata));
		}

		this.set(0, 3, new GuiElement[] {
			DependenciesElement.of(metadata, ModDependency.Kind.DEPENDS, Items.TOTEM_OF_UNDYING),
			DependenciesElement.of(metadata, ModDependency.Kind.RECOMMENDS, Items.CAKE),
			DependenciesElement.of(metadata, ModDependency.Kind.SUGGESTS, Items.COOKIE),
		});

		this.set(0, 4, new GuiElement[] {
			DependenciesElement.of(metadata, ModDependency.Kind.CONFLICTS, Items.WITHER_ROSE),
			DependenciesElement.of(metadata, ModDependency.Kind.BREAKS, Items.BARRIER)
		});
	}

	private void set(int startX, int y, GuiElement[] elements) {
		int index = (this.getWidth() * y) + startX;

		for (GuiElement element : elements) {
			if (element == null) continue;

			this.setSlot(index, element);
			index += 1;
		}
	}

	private void set(int x, int y, GuiElement element) {
		int index = (this.getWidth() * y) + x;
		this.setSlot(index, element);
	}
}
