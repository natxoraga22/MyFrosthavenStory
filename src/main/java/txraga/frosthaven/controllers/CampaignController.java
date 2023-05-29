package txraga.frosthaven.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Party;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;
import txraga.frosthaven.model.StoryItem;


@XSlf4j
@Controller
@RequestMapping("/")
public class CampaignController {
	
@GetMapping("")
	public ModelAndView campaign(Model model) {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Welcome
			File welcomeFile = new ClassPathResource("static/json/welcome.txt").getFile();
			List<String> welcomeLines = Files.readAllLines(welcomeFile.toPath());
			String welcome = String.join("<br/>", welcomeLines);
			log.debug("{}", welcome);

			// Party
			File partyFile = new ClassPathResource("static/json/party.json").getFile();
			Party party = objectMapper.readValue(partyFile, Party.class);
			File charactersFile = new ClassPathResource("static/json/characters.json").getFile();
			Map<String,String> characters = objectMapper.readValue(charactersFile, new TypeReference<Map<String,String>>(){});
			log.debug("{}", party);
			log.debug("{}", characters);

			// Scenarios
			File myStoryFile = new ClassPathResource("static/json/myStory.json").getFile();
			List<StoryItem> myStory = objectMapper.readValue(myStoryFile, new TypeReference<List<StoryItem>>(){});
			log.debug("{}", myStory);

			List<Scenario> scenarios = new ArrayList<>();
			for (StoryItem storyItem : myStory) {
				File scenarioFile = new ClassPathResource("static/json/scenarios/" + storyItem.getScenario() + ".json").getFile();
				Scenario scenario = objectMapper.readValue(scenarioFile, Scenario.class);
				if (storyItem.getPath() != null && storyItem.getPath().size() > 0) scenario.setPath(storyItem.getPath());
				scenario.replaceIcons();
				log.debug("{}", scenario);
				scenarios.add(scenario);
			}

			model.addAttribute("welcome", welcome);
			model.addAttribute("party", party);
			model.addAttribute("characters", characters);
			model.addAttribute("scenarioList", scenarios);
		}
		catch (IOException e) {
			log.catching(e);
		}
		return log.exit(new ModelAndView("campaign"));
	}

}
