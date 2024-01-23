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
@RequestMapping("/event")
public class EventController {

	private final FrosthavenStory fhStory;


	@GetMapping({"", "/{eventId}"})
	public ModelAndView event(Model model, @PathVariable(required = false) String eventId, 
	                                       @RequestParam(required = false) List<String> chosenOptions) {
		log.entry(eventId, chosenOptions);
		model.addAttribute("page", Page.EVENT);
		model.addAttribute("eventId", eventId);
		model.addAttribute("event", fhStory.getEvent(eventId, chosenOptions, null));
		return log.exit(new ModelAndView("event"));
	}

	@PostMapping("")
	public ModelAndView eventForm(Model model, @RequestParam String eventId, 
	                                           @RequestParam String chosenOptions) {
		log.entry(eventId, chosenOptions);
		return log.exit(new ModelAndView("redirect:/event/" + eventId + "?chosenOptions=" + chosenOptions));
	}

}
