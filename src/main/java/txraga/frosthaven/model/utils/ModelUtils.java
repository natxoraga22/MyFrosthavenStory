package txraga.frosthaven.model.utils;


public final class ModelUtils {
	
	public static String replaceIcons(String original) {
		if (original == null) return original;
		return original.replaceAll("\\[(.*?)\\]", "<img class=\"icon\" src=\"/img/icons/$1.png\">");
	}

}
