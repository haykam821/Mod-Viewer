package io.github.haykam821.modviewer.ui.element;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.StringReader;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class DescriptionElement {
	private static final int MAX_LINE_LENGTH = 25;

	private DescriptionElement() {
		return;
	}

	public static GuiElement of(ModMetadata metadata) {
		GuiElementBuilder builder = new GuiElementBuilder(Items.WRITABLE_BOOK)
			.setName(Text.translatable("text.modviewer.ui.view.description").formatted(Formatting.YELLOW));

		for (String line : DescriptionElement.getWrappedDescription(metadata)) {
			builder.addLoreLine(Text.literal(line).formatted(Formatting.GRAY));
		}

		return builder.build();
	}

	private static List<String> getWrappedDescription(ModMetadata metadata) {
		StringReader reader = new StringReader(metadata.getDescription());

		List<String> lines = new ArrayList<>();
		StringBuilder currentLine = new StringBuilder();

		while (reader.canRead()) {
			char current = reader.read();
			if (current == '\n' || (currentLine.length() > MAX_LINE_LENGTH && Character.isWhitespace(current))) {
				lines.add(currentLine.toString());
				currentLine = new StringBuilder();
			} else {
				currentLine.append(current);
			}
		}

		if (!currentLine.isEmpty()) {
			lines.add(currentLine.toString());
		}

		return lines;
	}
}
