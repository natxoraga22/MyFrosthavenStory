package txraga.mystory.frosthaven.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import txraga.mystory.frosthaven.model.raw.RawEvent;
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
		model.addAttribute("typesAndSeasonsList", RawEvent.TypeAndSeason.values());
		// Maps with messageSource values for each enum value
		model.addAttribute("typesAndSeasonsStrings", EnumUtils.toMap(RawEvent.TypeAndSeason.class, messageSource, "event.typeAndSeason", locale));
	}

}
