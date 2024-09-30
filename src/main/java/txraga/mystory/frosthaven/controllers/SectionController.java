package txraga.mystory.frosthaven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.global.Frosthaven;
import txraga.mystory.frosthaven.model.Section;


@XSlf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(WebPage.SECTION_CONTROLLER_URL)
public class SectionController {
	
	private final Frosthaven frosthaven;


	@GetMapping({"", WebPage.SECTION_URL})
	public ModelAndView section(Model model, @PathVariable(required = false) String sectionId) {
		log.entry(sectionId);
		WebPage webPage = WebPage.SECTION;
		model.addAttribute("page", webPage);
		// URL parameters
		model.addAttribute("sectionId", sectionId);
		// Section form
		model.addAttribute("sectionsMap", frosthaven.getAllSectionsAsMap());
		return log.exit(new ModelAndView(webPage.getTemplateName()));
	}

	@GetMapping(WebPage.SECTION_DATA_URL)
	@ResponseBody
	public Section getSection(@PathVariable String sectionId) {
		log.entry(sectionId);
		return log.exit(frosthaven.getSection(sectionId));
	}

}
