package txraga.mystory.frosthaven.files.utils;


public class JsonUtils {

	public static String replaceIcons(String original) {
		if (original == null) return original;

		// Section icon (special case)
		String sectionIconRegex = "\\[section_(.*?)\\]";
		String sectionIconHtml = "<img class=\"icon section\" src=\"/img/icons/section.png\"> <strong>$1</strong>";
		String replaced = original.replaceAll(sectionIconRegex, sectionIconHtml);

		// Scenario icon (special case)
		String scenarioIconRegex = "\\[scenario_(.*?)\\]";
		String scenarioIconHtml = "<span class=\"scenarioNumber\">($1)</span>";
		replaced = replaced.replaceAll(scenarioIconRegex, scenarioIconHtml);

		// Default case
		return replaced.replaceAll("\\[(.*?)\\]", "<img class=\"icon $1\" src=\"/img/icons/$1.png\">");
	}

}
