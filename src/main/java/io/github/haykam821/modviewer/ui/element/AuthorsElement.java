package io.github.haykam821.modviewer.ui.element;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class AuthorsElement {
	private AuthorsElement() {
		return;
	}

	public static GuiElement of(ModMetadata metadata) {
		GuiElementBuilder builder = new GuiElementBuilder(Items.PLAYER_HEAD)
			.setName(Text.translatable("text.modviewer.ui.view.authors"));

		for (Person author : metadata.getAuthors()) {
			builder.addLoreLine(AuthorsElement.getLoreLine(author, false));
		}
		for (Person contributor : metadata.getContributors()) {
			builder.addLoreLine(AuthorsElement.getLoreLine(contributor, true));
		}

		return builder.build();
	}

	private static Text getLoreLine(Person person, boolean contributor) {
		return Text.literal(person.getName()).styled(style -> {
			return style.withFormatting(Formatting.GRAY).withItalic(contributor);
		});
	}
}
