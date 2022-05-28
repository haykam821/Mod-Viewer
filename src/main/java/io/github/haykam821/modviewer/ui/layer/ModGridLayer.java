package io.github.haykam821.modviewer.ui.layer;

import java.util.Comparator;
import java.util.stream.Collectors;

import io.github.haykam821.modviewer.ui.ModViewerUi;
import io.github.haykam821.modviewer.ui.element.ErrorElement;
import io.github.haykam821.modviewer.ui.element.ModElement;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.util.math.MathHelper;

public class ModGridLayer extends AbstractModLayer {
	public static final Comparator<ModContainer> COMPARATOR = Comparator.comparing(mod -> {
		ModMetadata metadata = mod.getMetadata();

		String name = metadata.getName();
		return name == null ? metadata.getId() : name;
	});

	public ModGridLayer(ModViewerUi ui, int width, int height) {
		super(ui, width, height);
	}

	@Override
	public void update() {
		this.clearSlots();

		int slot = 0;
		for (ModContainer mod : this.getPageEntries()) {
			if (slot >= this.getSize()) {
				break;
			}

			this.setSlot(slot, ErrorElement.wrap(() -> ModElement.of(this.ui, mod, false)));
			slot += 1;
		}
	}

	private Iterable<ModContainer> getPageEntries() {
		return this.ui.getMods().stream()
			.filter(this::shouldShow)
			.sorted(COMPARATOR)
			.skip(this.getSize() * this.ui.getPage())
			.collect(Collectors.toList());
	}

	public boolean shouldShow(ModContainer mod) {
		return this.ui.shouldShowLibraries() || !ModElement.isLibrary(mod.getMetadata());
	}

	public int getMaxPage() {
		int shown = 0;
		for (ModContainer mod : this.ui.getMods()) {
			if (this.shouldShow(mod)) {
				shown += 1;
			}
		}

		return MathHelper.ceil(shown / (float) this.getSize());
	}
}
