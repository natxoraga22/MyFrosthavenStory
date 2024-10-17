package txraga.mystory.frosthaven.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.global.Frosthaven;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(WebPage.CAMPAIGN_CONTROLLER_URL)
public class CampaignController {
	
	private final Frosthaven frosthaven;


	@GetMapping
	public ModelAndView campaign(Model model) throws IOException {
		log.entry();
		WebPage webPage = WebPage.CAMPAIGN;
		model.addAttribute("webPage", webPage);
		model.addAttribute("welcome", frosthaven.getWelcome());
		model.addAttribute("charactersMap", frosthaven.getCharacters());
		model.addAttribute("personalQuestsMap", frosthaven.getPersonalQuests());
		model.addAttribute("scenariosMap", frosthaven.getScenarios());
		model.addAttribute("eventsMap", frosthaven.getEvents());
		model.addAttribute("sectionsMap", frosthaven.getSections());
		model.addAttribute("buildingsMap", frosthaven.getBuildings());
		return log.exit(new ModelAndView(webPage.getTemplateName()));
	}

}
