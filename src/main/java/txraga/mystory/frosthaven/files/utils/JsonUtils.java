package txraga.mystory.frosthaven.files.utils;


public class JsonUtils {

	private final static String IMG_URL = "https://dusrndffbb8xh.cloudfront.net";


	public static String replaceIcons(String original) {
		if (original == null) return original;

		// Section icon (special case)
		String sectionIconRegex = "\\[section_(.*?)\\]";
		String sectionIconHtml = "<img class=\"icon section\" src=\"" + IMG_URL + "/icons/section.png\"> <strong>$1</strong>";
		String replaced = original.replaceAll(sectionIconRegex, sectionIconHtml);

		// Scenario icon (special case)
		String scenarioIconRegex = "\\[scenario_(.*?)\\]";
		String scenarioIconHtml = "<span class=\"scenarioNumber\">($1)</span>";
		replaced = replaced.replaceAll(scenarioIconRegex, scenarioIconHtml);

		// Item icon (special case)
		String itemIconRegex = "\\[item_(.*?)\\]";
		String itemIconHtml = "<img class=\"icon item\" src=\"" + IMG_URL + "/icons/item.png\"> <strong>$1</strong>";
		replaced = replaced.replaceAll(itemIconRegex, itemIconHtml);

		// Square icon (special case)
		replaced = replaced.replaceAll("\\[square\\]", "<i class=\"fa-regular fa-square\"></i>");

		// Default case
		return replaced.replaceAll("\\[(.*?)\\]", "<img class=\"icon $1\" src=\"" + IMG_URL + "/icons/$1.png\">");
	}

}
