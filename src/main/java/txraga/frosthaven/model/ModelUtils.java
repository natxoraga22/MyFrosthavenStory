package txraga.frosthaven.model;


public final class ModelUtils {
	
	public static String replaceIcons(String original) {
		if (original == null) return original;
		return original.replaceAll("\\[(.*?)\\]", "<img class=\"icon $1\" src=\"/img/icons/$1.png\">");
	}

}
