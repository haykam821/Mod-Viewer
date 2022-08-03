package io.github.haykam821.modviewer.ui;

import java.util.Collection;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import eu.pb4.sgui.api.gui.layered.LayeredGui;
import io.github.haykam821.modviewer.command.ModViewerCommand;
import io.github.haykam821.modviewer.command.ModViewerPermissions;
import io.github.haykam821.modviewer.ui.element.BackgroundElement;
import io.github.haykam821.modviewer.ui.layer.ModGridLayer;
import io.github.haykam821.modviewer.ui.layer.ModToolbarLayer;
import io.github.haykam821.modviewer.ui.layer.ModViewLayer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ModViewerUi extends LayeredGui {
	private static final int SIDEBAR_WIDTH = 3;
	private static final int LAYOUT_SPACING = 1;

	private final ModGridLayer gridLayer;
	private final ModToolbarLayer toolbarLayer;
	private final ModViewLayer viewLayer;

	private final Collection<ModContainer> mods;
	private ModContainer viewedMod;

	private int page = 0;
	private boolean showLibraries = false;

	public ModViewerUi(ServerPlayerEntity player) throws CommandSyntaxException {
		super(ScreenHandlerType.GENERIC_9X6, player, false);

		this.gridLayer = new ModGridLayer(this, SIDEBAR_WIDTH, this.height - 1);
		this.addLayer(this.gridLayer, 0, 0);

		this.toolbarLayer = new ModToolbarLayer(this, SIDEBAR_WIDTH);
		this.addLayer(this.toolbarLayer, 0, this.height - 1);

		this.viewLayer = new ModViewLayer(this, this.width - SIDEBAR_WIDTH - LAYOUT_SPACING, this.height);
		this.addLayer(this.viewLayer, SIDEBAR_WIDTH + LAYOUT_SPACING, 0);

		this.mods = FabricLoader.getInstance().getAllMods();
		this.viewedMod = this.mods.stream()
			.filter(this.gridLayer::shouldShow)
			.sorted(ModGridLayer.COMPARATOR)
			.findFirst()
			.orElseThrow(ModViewerCommand.NO_VIEWABLE_MODS::create);

		for (int slot = 0; slot < this.getSize(); slot += 1) {
			if (slot % this.getWidth() > 2) {
				this.setSlot(slot, BackgroundElement.INSTANCE);
			}
		}

		this.setTitle(Text.translatable("text.modviewer.ui.title", this.mods.size()));
		this.update();
	}

	public Collection<ModContainer> getMods() {
		return FabricLoader.getInstance().getAllMods();
	}

	public ModContainer getViewedMod() {
		return this.viewedMod;
	}

	public void setViewedMod(ModContainer viewedMod) {
		if (ModViewerPermissions.canViewMod(this.getPlayer(), viewedMod)) {
			this.viewedMod = viewedMod;
			this.update();
		}
	}

	public int getPage() {
		return this.page;
	}

	private void setPage(int page) {
		this.page = page;
		this.update();
	}

	public void setWrappedPage(int page) {
		if (page < 0) {
			page = this.gridLayer.getMaxPage() - page - 1;
		}
		this.setPage(page);
	}

	public void movePage(int offset) {
		this.setPage(this.getPage() + offset);
	}

	private void clampPage() {
		if (page < 0) {
			page = 0;
		}

		int maxPage = this.gridLayer.getMaxPage();
		if (page >= maxPage) {
			page = maxPage - 1;
		}
	}

	public boolean shouldShowLibraries() {
		return this.showLibraries;
	}

	public void setShowLibraries(boolean showLibraries) {
		this.showLibraries = showLibraries;
		this.update();
	}

	public void update() {
		this.clampPage();

		this.gridLayer.update();
		this.toolbarLayer.update();
		this.viewLayer.update();
	}
}
