package txraga.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Scenario {
	
	private int id;
	private String coordinates;
	private String name;
	private int complexity;
	private String location;

	private String goals;
	private String specialRules;
	private String introduction;

	private List<Section> sections;


	public void replaceIcons() {
		this.goals = replaceIcons(this.goals);
		this.specialRules = replaceIcons(this.specialRules);
	}

	private String replaceIcons(String original) {
		if (original == null) return original;
		return original.replaceAll("\\[(.*?)\\]", "<img class=\"icon\" src=\"/img/icons/$1.png\" height=\"16\">");
	}

}
