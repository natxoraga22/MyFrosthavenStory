package txraga.mystory.frosthaven.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.global.Frosthaven;
import txraga.mystory.frosthaven.model.Event;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(WebPage.EVENT_CONTROLLER_URL)
public class EventController {

	private final Frosthaven frosthaven;


	@GetMapping({"", WebPage.EVENT_URL})
	public ModelAndView event(Model model, @PathVariable(required = false) String eventId,
	                                       @RequestParam(name = "chosenOptions", required = false) List<String> chosenOptionsIds) {
		log.entry(eventId, chosenOptionsIds);
		WebPage webPage = WebPage.EVENT;
		model.addAttribute("webPage", webPage);
		// URL parameters
		model.addAttribute("eventId", eventId);
		model.addAttribute("chosenOptionsIds", chosenOptionsIds);
		// Event form
		model.addAttribute("eventsMap", frosthaven.getAllEventsAsMap());
		return log.exit(new ModelAndView(webPage.getTemplateName()));
	}

	@GetMapping(WebPage.EVENT_DATA_URL)
	@ResponseBody
	public Event getEvent(@PathVariable String eventId) {
		log.entry(eventId);
		return log.exit(frosthaven.getEvent(eventId));
	}
	
}
