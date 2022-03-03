package io.github.haykam821.modviewer.ui.element;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public final class AuthorsElement {
	private AuthorsElement() {
		return;
	}

	public static GuiElement of(ModMetadata metadata) {
		GuiElementBuilder builder = new GuiElementBuilder(Items.PLAYER_HEAD)
			.setName(new TranslatableText("text.modviewer.ui.view.authors"));

		for (Person author : metadata.getAuthors()) {
			builder.addLoreLine(AuthorsElement.getLoreLine(author, false));
		}
		for (Person contributor : metadata.getContributors()) {
			builder.addLoreLine(AuthorsElement.getLoreLine(contributor, true));
		}

		return builder.build();
	}

	private static MutableText getLoreLine(Person person, boolean contributor) {
		return new LiteralText(person.getName()).styled(style -> {
			return style.withFormatting(Formatting.GRAY).withItalic(contributor);
		});
	}
}
