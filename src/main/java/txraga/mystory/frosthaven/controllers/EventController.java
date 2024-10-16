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
@RequestMapping(WebPage.EVENT_CONTROLLER_URL)
public class EventController {

	private final Frosthaven frosthaven;


	@GetMapping({"", WebPage.EVENT_URL})
	public ModelAndView event(Model model, @PathVariable(required = false) String eventId,
	                                       @RequestParam(name = "chosenOptions", required = false) List<String> chosenOptionsIds) {
		log.entry(eventId, chosenOptionsIds);
		WebPage webPage = WebPage.EVENT;
		model.addAttribute("webPage", webPage);
		model.addAttribute("eventsMap", frosthaven.getEvents());
		// URL parameters
		model.addAttribute("eventId", eventId);
		model.addAttribute("chosenOptionsIds", chosenOptionsIds);
		return log.exit(new ModelAndView(webPage.getTemplateName()));
	}
	
}
