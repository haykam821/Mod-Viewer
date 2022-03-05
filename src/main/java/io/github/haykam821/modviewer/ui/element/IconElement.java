package io.github.haykam821.modviewer.ui.element;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import io.github.haykam821.modviewer.MicroIconRenderer;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public final class IconElement {
	private IconElement() {
		return;
	}

	public static GuiElement of(ModContainer mod) {
		GuiElementBuilder builder = new GuiElementBuilder(Items.PAINTING)
			.setName(new TranslatableText("text.modviewer.ui.view.icon").formatted(Formatting.YELLOW));

		for (Text line : MicroIconRenderer.getIcon(mod)) {
			builder.addLoreLine(line);
		}

		return builder.build();
	}
}
