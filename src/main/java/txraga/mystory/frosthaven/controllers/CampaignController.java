package txraga.mystory.frosthaven.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.Campaign;
import txraga.mystory.frosthaven.Frosthaven;
import txraga.mystory.frosthaven.FrosthavenStory;
import txraga.mystory.frosthaven.model.personal.PersonalStory;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class CampaignController {
	
	private final Frosthaven frosthaven;
	private final FrosthavenStory fhStory;


	@GetMapping("")
	public ModelAndView campaign(Model model) throws IOException {
		log.entry();
		model.addAttribute("page", Page.CAMPAIGN);
		model.addAttribute("welcome", frosthaven.getWelcome());
		return log.exit(new ModelAndView("campaign"));
	}

	@PostMapping("/personalStory")
	public ModelAndView personalStory(Model model, @RequestBody(required = false) PersonalStory personalStory) throws IOException {
		log.entry(personalStory);
		Campaign campaign = new Campaign(personalStory, frosthaven, fhStory);

		// DEBUG tracker
		log.debug("{}", campaign.getTracker().getAvailableEvents());
		log.debug("{}", campaign.getTracker().getMainQuestAvailableScenarios());
		log.debug("{}", campaign.getTracker().getSideQuestAvailableScenarios());

		model.addAttribute("page", Page.CAMPAIGN);
		model.addAttribute("welcome", frosthaven.getWelcome());
		model.addAttribute("party", campaign.getOriginalParty().values());
		model.addAttribute("story", campaign.getStory());
		return log.exit(new ModelAndView("fragments/campaign :: campaign"));
	}

}
