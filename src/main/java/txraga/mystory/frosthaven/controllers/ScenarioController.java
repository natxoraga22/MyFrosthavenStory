package txraga.mystory.frosthaven.controllers;

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
import txraga.mystory.frosthaven.FrosthavenFiles;
import txraga.mystory.frosthaven.model.Scenario;


@XSlf4j
@Controller
@RequestMapping("/scenario")
public class ScenarioController {

	@GetMapping({"", "/{scenarioId}"})
	public ModelAndView scenario(Model model, @PathVariable(required = false) String scenarioId, @RequestParam(required = false) String path) {
		log.entry(scenarioId, path);
		model.addAttribute("page", Page.SCENARIO);
		model.addAttribute("scenarioId", scenarioId);
		model.addAttribute("scenario", getScenario(scenarioId, path));

		// Previous and next scenarios
		if (scenarioId != null) {
			model.addAttribute("prevScenario", String.format("%03d", Integer.parseInt(scenarioId) - 1));
			model.addAttribute("nextScenario", String.format("%03d", Integer.parseInt(scenarioId) + 1));
		}
		return log.exit(new ModelAndView("scenario"));
	}

	@PostMapping("")
	public ModelAndView scenarioForm(Model model, @RequestParam String scenarioId, @RequestParam String scenarioPath) {
		log.entry(scenarioId, scenarioPath);
		return log.exit(new ModelAndView("redirect:/scenario/" + scenarioId + "?path=" + scenarioPath));
	}


	/* -------- */
	/* SCENARIO */
	/* -------- */

	private Scenario getScenario(String id, String path) {
		log.entry(id, path);
		Scenario scenario = FrosthavenFiles.getScenario(id);
		if (scenario != null) {
			// Path
			if (path != null && !path.isBlank()) {
				List<String> scenarioPath = new ArrayList<>();
				String[] pathSplit = path.split(",");
				for (String pathItem : pathSplit) scenarioPath.add(pathItem.trim());
				scenario.setPath(scenarioPath);
			}
		}
		return log.exit(scenario);
	}

}
