package txraga.frosthaven.controllers;

import java.util.ArrayList;
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
import txraga.frosthaven.FrosthavenFiles;
import txraga.frosthaven.model.Scenario;


@XSlf4j
@Controller
@RequestMapping("/scenario")
public class ScenarioController {

	@GetMapping({"", "/{scenarioId}"})
	public ModelAndView scenario(Model model, @PathVariable(required = false) String scenarioId, @RequestParam(required = false) String path) {
		log.entry();
		// Scenario
		List<String> scenarioPath = new ArrayList<>();
		if (path != null && !path.isBlank()) {
			String[] pathSplit = path.split(",");
			for (String pathItem : pathSplit) scenarioPath.add(pathItem.trim());
		}
		model.addAttribute("scenario", getScenario(scenarioId, scenarioPath));

		// Previous and Next
		if (scenarioId != null) {
			model.addAttribute("prevScenario", String.format("%03d", Integer.parseInt(scenarioId) - 1));
			model.addAttribute("nextScenario", String.format("%03d", Integer.parseInt(scenarioId) + 1));
		}
		return log.exit(new ModelAndView("scenario"));
	}

	@PostMapping("")
	public ModelAndView scenarioForm(Model model, @RequestParam String scenarioId, @RequestParam String scenarioPath) {
		log.entry();
		return log.exit(new ModelAndView("redirect:/scenario/" + scenarioId + "?path=" + scenarioPath));
	}


	private Scenario getScenario(String id, List<String> path) {
		log.entry(id, path);
		Scenario scenario = FrosthavenFiles.getScenario(id);
		if (scenario != null && path != null) scenario.setPath(path);
		return log.exit(scenario);
	}

}
