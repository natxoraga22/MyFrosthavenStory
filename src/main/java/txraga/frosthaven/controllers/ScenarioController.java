package txraga.frosthaven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.XSlf4j;


@XSlf4j
@Controller
@RequestMapping("/scenario/{scenarioId}")
public class ScenarioController {
	
	@GetMapping("")
	public ModelAndView campaign(Model model, @PathVariable String scenarioId) {
		log.entry();
		model.addAttribute("scenario", CampaignUtils.getScenario(scenarioId, null));
		return log.exit(new ModelAndView("scenario"));
	}

}
