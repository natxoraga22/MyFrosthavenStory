package txraga.frosthaven.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Scenario;


@XSlf4j
@Controller
@RequestMapping("/")
public class CampaignController {
	
@GetMapping("")
	public ModelAndView campaign(Model model) {
		log.entry();

		// TEST
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<String> scenarioIds = List.of("000", "001", "002", "004-B");
			List<Scenario> scenarios = new ArrayList<>();
			for (String scenarioId : scenarioIds) {
				File scenarioFile = new ClassPathResource("static/json/scenarios/" + scenarioId + ".json").getFile();
				Scenario scenario = objectMapper.readValue(scenarioFile, Scenario.class);
				log.debug("{}", scenario);
				scenarios.add(scenario);
			}
			model.addAttribute("scenarioList", scenarios);
		} 
		catch (Exception e) {
			log.catching(e);
		}

		return log.exit(new ModelAndView("campaign"));
	}

}
