package txraga.frosthaven.controllers;

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
import txraga.frosthaven.Frosthaven;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.Section;


@XSlf4j
@Controller
@RequestMapping("/event")
public class EventController {

	@Autowired private Frosthaven frosthaven;


	@GetMapping({"", "/{eventId}"})
	public ModelAndView event(Model model, @PathVariable(required = false) String eventId, 
	                                       @RequestParam(required = false) String chosenOption,
	                                       @RequestParam(required = false) String sectionId) {
		log.entry(eventId, chosenOption, sectionId);
		model.addAttribute("eventId", eventId);
		model.addAttribute("chosenOption", chosenOption);
		model.addAttribute("sectionId", sectionId);
		model.addAttribute("event", getEvent(eventId, chosenOption, sectionId));
		return log.exit(new ModelAndView("event"));
	}

	@PostMapping("")
	public ModelAndView eventForm(Model model, @RequestParam String eventId, 
	                                           @RequestParam String chosenOption,
	                                           @RequestParam String sectionId) {
		log.entry(eventId, chosenOption, sectionId);
		String redirectUrl = "/event/" + eventId + "?chosenOption=" + chosenOption + "&sectionId=" + sectionId;
		return log.exit(new ModelAndView("redirect:" + redirectUrl));
	}


	/* ----- */
	/* EVENT */
	/* ----- */

	private Event getEvent(String id, String chosenOption, String sectionId) {
		log.entry(id, chosenOption, sectionId);
		Event event = frosthaven.getEvent(id);
		if (event != null) {
			// Chosen option
			if (chosenOption != null && !chosenOption.isBlank()) event.setChosenOption(chosenOption);
			// Section
			if (sectionId != null && !sectionId.isBlank()) {
				Section section = frosthaven.getSection(sectionId);
				if (section != null) event.setSection(section);
			}
		}
		return log.exit(event);
	}

}
