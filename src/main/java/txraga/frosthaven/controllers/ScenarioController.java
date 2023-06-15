package txraga.frosthaven.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.XSlf4j;


@XSlf4j
@Controller
@RequestMapping("/scenario")
public class ScenarioController {

	@GetMapping({"", "/{scenarioId}"})
	public ModelAndView scenario(Model model, @PathVariable(required = false) String scenarioId, @RequestParam(required = false) String path) {
		log.entry();
		List<String> scenarioPath = (path != null && !path.isBlank()) ? List.of(path.split(",")) : null;
		model.addAttribute("scenario", CampaignUtils.getScenario(scenarioId, scenarioPath));
		return log.exit(new ModelAndView("scenario"));
	}

	@PostMapping("")
	public ModelAndView scenarioForm(Model model, @RequestParam String scenarioId, @RequestParam String scenarioPath) {
		log.entry();
		return log.exit(new ModelAndView("redirect:/scenario/" + scenarioId + "?path=" + scenarioPath));
	}

}
