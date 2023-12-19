package txraga.mystory.frosthaven.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.Frosthaven;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.Section;


@XSlf4j
@Controller
@RequestMapping("/event")
public class EventController {

	@Autowired private Frosthaven frosthaven;


	@GetMapping({"", "/{eventId}"})
	public ModelAndView event(Model model, @PathVariable(required = false) String eventId, 
	                                       @RequestParam(required = false) List<String> chosenOptions,
	                                       @RequestParam(required = false) String sectionId) {
		log.entry(eventId, chosenOptions, sectionId);
		model.addAttribute("page", Page.EVENT);
		model.addAttribute("eventId", eventId);
		model.addAttribute("chosenOptions", chosenOptions);
		model.addAttribute("sectionId", sectionId);
		model.addAttribute("event", getEvent(eventId, chosenOptions, sectionId));
		return log.exit(new ModelAndView("event"));
	}

	@PostMapping("")
	public ModelAndView eventForm(Model model, @RequestParam String eventId, 
	                                           @RequestParam List<String> chosenOptions,
	                                           @RequestParam String sectionId) {
		log.entry(eventId, chosenOptions, sectionId);
		String redirectUrl = "/event/" + eventId + "?chosenOptions=" + chosenOptions + "&sectionId=" + sectionId;
		return log.exit(new ModelAndView("redirect:" + redirectUrl));
	}


	/* ----- */
	/* EVENT */
	/* ----- */

	private Event getEvent(String id, List<String> chosenOptions, String sectionId) {
		log.entry(id, chosenOptions, sectionId);
		Event event = frosthaven.getEvent(id);
		if (event != null) {
			// Chosen option
			if (chosenOptions != null && !chosenOptions.isEmpty()) event.setChosenOptions(chosenOptions);
			// Section
			if (sectionId != null && !sectionId.isBlank()) {
				Section section = frosthaven.getSection(sectionId);
				if (section != null) event.setSection(section);
			}
		}
		return log.exit(event);
	}

}
