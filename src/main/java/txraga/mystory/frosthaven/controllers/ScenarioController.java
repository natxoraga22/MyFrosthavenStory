package txraga.mystory.frosthaven.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.global.Frosthaven;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(WebPage.SCENARIO_CONTROLLER_URL)
public class ScenarioController {

	private final Frosthaven frosthaven;


	@GetMapping({"", WebPage.SCENARIO_URL})
	public ModelAndView scenario(Model model, @PathVariable(required = false) String scenarioId,
	                                          @RequestParam(name = "path", required = false) List<String> scenarioPath) {
		log.entry(scenarioId, scenarioPath);
		WebPage webPage = WebPage.SCENARIO;
		model.addAttribute("webPage", webPage);
		model.addAttribute("scenariosMap", frosthaven.getScenarios());
		// URL parameters
		model.addAttribute("scenarioId", scenarioId);
		model.addAttribute("scenarioPath", scenarioPath);
		return log.exit(new ModelAndView(webPage.getTemplateName()));
	}

}
