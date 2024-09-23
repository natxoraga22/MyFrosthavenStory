package txraga.mystory.frosthaven.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.global.Frosthaven;
import txraga.mystory.frosthaven.model.played.PlayedEvent;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {

	private final Frosthaven frosthaven;


	@GetMapping({"", "/{eventId}"})
	public ModelAndView event(Model model, @PathVariable(required = false) String eventId, 
	                                       @RequestParam(required = false) List<String> chosenOptions) {
		log.entry(eventId, chosenOptions);
		model.addAttribute("page", Page.EVENT);
		model.addAttribute("eventId", eventId);
		model.addAttribute("chosenOptions", chosenOptions);


		model.addAttribute("allEventsIds", frosthaven.getAllRawEventsIds());
		model.addAttribute("rawEvent", frosthaven.getRawEvent(eventId));
		model.addAttribute("playedEvent", frosthaven.getPlayedEvent(eventId, chosenOptions, null));
		return log.exit(new ModelAndView("event"));
	}

	@PostMapping("")
	public ModelAndView eventForm(Model model, @RequestParam String eventId, 
	                                           @RequestParam(required = false) String chosenOptions) {
		log.entry(eventId, chosenOptions);
		String redirectUrl = "/event/" + eventId + (chosenOptions != null ? "?chosenOptions=" + chosenOptions : "");
		return log.exit(new ModelAndView("redirect:" + redirectUrl));
	}

	@GetMapping("/{eventId}/data")
	@ResponseBody
	public PlayedEvent getPlayedEvent(@PathVariable String eventId, @RequestParam(required = false) List<String> chosenOptions) {
		log.entry(eventId, chosenOptions);
		return log.exit(frosthaven.getPlayedEvent(eventId, chosenOptions, null));
	}

}
