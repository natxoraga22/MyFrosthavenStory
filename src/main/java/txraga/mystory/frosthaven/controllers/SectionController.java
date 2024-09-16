package txraga.mystory.frosthaven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.global.Frosthaven;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/section")
public class SectionController {
	
	private final Frosthaven frosthaven;


	@GetMapping({"", "/{sectionId}"})
	public ModelAndView section(Model model, @PathVariable(required = false) String sectionId) {
		log.entry(sectionId);
		model.addAttribute("page", Page.SECTION);
		model.addAttribute("sectionId", sectionId);
		model.addAttribute("section", frosthaven.getSection(sectionId));
		return log.exit(new ModelAndView("section"));
	}

}
