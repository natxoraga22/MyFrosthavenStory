package txraga.mystory.frosthaven.controllers;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.utils.EnumUtils;


@RequiredArgsConstructor
@ControllerAdvice
public class GlobalController {

	private final MessageSource messageSource;

	@Value("${aws.cloudfront.url}") private String imgUrl;


	@ModelAttribute("imgUrl")
	private String getContextPath() {
		return imgUrl;
	}

	@ModelAttribute
	public void addEnumsToModel(Model model, Locale locale) {
		model.addAttribute("typesAndSeasonsList", Event.TypeAndSeason.values());
		// Maps with messageSource values for each enum value
		model.addAttribute("typesAndSeasonsStrings", EnumUtils.toMap(Event.TypeAndSeason.class, messageSource, "event.typeAndSeason", locale));
	}

	
	/**
	 * Add all web pages in the {@link WebPage} enum to the model.
	 */
	@ModelAttribute
	public void addWebPagesToModel(Model model) {
		for (WebPage page : WebPage.values()) {
			String attributeName = toCamelCase(page.name());
			model.addAttribute(attributeName + "Page", page);
		}
	}

	private String toCamelCase(String input) {
		List<String> inputWords = List.of(input.split("_"));
		StringBuilder result = new StringBuilder(inputWords.get(0).toLowerCase());
		for (int i = 1; i < inputWords.size(); i++) {
			String inputWord = inputWords.get(i);
			result.append(inputWord.substring(0, 1).toUpperCase() + inputWord.substring(1).toLowerCase());
		}
		return result.toString();
	}

}
