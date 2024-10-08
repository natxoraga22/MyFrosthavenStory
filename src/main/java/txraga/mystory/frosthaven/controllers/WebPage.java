package txraga.mystory.frosthaven.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum WebPage {

	/* ----------- */
	/* ENUM VALUES */
	/* ----------- */

	CAMPAIGN(WebPage.CAMPAIGN_CONTROLLER_URL, null, "campaign"),

	SCENARIOS(WebPage.SCENARIO_CONTROLLER_URL, null, "scenario"),
	SCENARIO(WebPage.SCENARIO_CONTROLLER_URL, WebPage.SCENARIO_URL, "scenario"),

	EVENTS(WebPage.EVENT_CONTROLLER_URL, null, "event"),
	EVENT(WebPage.EVENT_CONTROLLER_URL, WebPage.EVENT_URL, "event"),
	
	SECTIONS(WebPage.SECTION_CONTROLLER_URL, null, "section"),
	SECTION(WebPage.SECTION_CONTROLLER_URL, WebPage.SECTION_URL, "section");


	/* ------------- */
	/* URL CONSTANTS */
	/* ------------- */
	// Enum values cannot be used in @RequestMapping annotations, so the URLs must be defined as constants

	public static final String CAMPAIGN_CONTROLLER_URL = "/";

	public static final String SCENARIO_CONTROLLER_URL = "/scenario";
	public static final String SCENARIO_URL = "/{scenarioId}";

	public static final String EVENT_CONTROLLER_URL = "/event";
	public static final String EVENT_URL = "/{eventId}";

	public static final String SECTION_CONTROLLER_URL = "/section";
	public static final String SECTION_URL = "/{sectionId}";


	/* ------------------ */
	/* FIELDS AND METHODS */
	/* ------------------ */

	private String controllerUrl;
	private String pageUrl;
	private String templateName;

	public String getUrl() {
		return controllerUrl + (pageUrl != null ? pageUrl : "");
	}

	public String getTemplateName() {
		return templateName != null ? "pages/" + templateName : null;
	}

}
