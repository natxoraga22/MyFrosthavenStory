package txraga.mystory.frosthaven.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.FrosthavenStory;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/scenario")
public class ScenarioController {

	private final FrosthavenStory fhStory;


	@GetMapping({"", "/{scenarioId}"})
	public ModelAndView scenario(Model model, @PathVariable(required = false) String scenarioId,
	                                          @RequestParam(required = false) List<String> path) {
		log.entry(scenarioId, path);
		model.addAttribute("page", Page.SCENARIO);
		model.addAttribute("scenarioId", scenarioId);
		model.addAttribute("scenario", fhStory.getScenario(scenarioId, path, null));

		// Previous and next scenarios
		if (scenarioId != null) {
			model.addAttribute("prevScenario", String.format("%03d", Integer.parseInt(scenarioId) - 1));
			model.addAttribute("nextScenario", String.format("%03d", Integer.parseInt(scenarioId) + 1));
		}
		return log.exit(new ModelAndView("scenario"));
	}

	@PostMapping("")
	public ModelAndView scenarioForm(Model model, @RequestParam String scenarioId,
	                                              @RequestParam String scenarioPath) {
		log.entry(scenarioId, scenarioPath);
		return log.exit(new ModelAndView("redirect:/scenario/" + scenarioId + "?path=" + scenarioPath));
	}

}
