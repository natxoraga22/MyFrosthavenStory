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
	SCENARIO(WebPage.SCENARIO_CONTROLLER_URL, null, "scenario"),
	EVENTS(WebPage.EVENT_CONTROLLER_URL, null, "event"),
	EVENT(WebPage.EVENT_CONTROLLER_URL, WebPage.EVENT_URL, "event"),
	EVENT_RAW_DATA(WebPage.EVENT_CONTROLLER_URL, WebPage.EVENT_RAW_DATA_URL, null),
	EVENT_PLAYED_DATA(WebPage.EVENT_CONTROLLER_URL, WebPage.EVENT_PLAYED_DATA_URL, null),
	SECTION(WebPage.SECTION_CONTROLLER_URL, null, "section");


	/* ------------- */
	/* URL CONSTANTS */
	/* ------------- */
	// Enum values cannot be used in @RequestMapping annotations, so the URLs must be defined as constants

	public static final String CAMPAIGN_CONTROLLER_URL = "/";
	public static final String SCENARIO_CONTROLLER_URL = "/scenario";
	public static final String EVENT_CONTROLLER_URL = "/event";
	public static final String EVENT_URL = "/{eventId}";
	public static final String EVENT_RAW_DATA_URL = EVENT_URL + "/rawData";
	public static final String EVENT_PLAYED_DATA_URL = EVENT_URL + "/playedData";
	public static final String SECTION_CONTROLLER_URL = "/section";


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
