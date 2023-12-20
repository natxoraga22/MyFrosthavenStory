package txraga.mystory.frosthaven.model;


public final class ModelUtils {
	
	public static String replaceIcons(String original) {
		if (original == null) return original;

		// Section icon (special case)
		String sectionIconRegex = "\\[section_(.*?)\\]";
		String sectionIconHtml = "<img class=\"icon section\" src=\"/img/icons/section.png\"> <strong>$1</strong>";
		String replaced = original.replaceAll(sectionIconRegex, sectionIconHtml);

		// Scenario icon
		String scenarioIconRegex = "\\[scenario_(.*?)\\]";
		String scenarioIconHtml = "<span class=\"scenarioNumber\">($1)</span>";
		replaced = replaced.replaceAll(scenarioIconRegex, scenarioIconHtml);

		return replaced.replaceAll("\\[(.*?)\\]", "<img class=\"icon $1\" src=\"/img/icons/$1.png\">");
	}

}
