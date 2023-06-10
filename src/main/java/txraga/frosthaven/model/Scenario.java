package txraga.frosthaven.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Scenario extends StoryObject {
	
	private int id;
	private String coordinates;
	private String name;
	private String requirement;
	private StoryItem unlockedBy;
	private int complexity;
	private String location;

	private String goals;
	private String effects;
	private Map<String,Section> sections;

	private List<String> path;


	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.SCENARIO;
	}

	public String getIdString() {
		return String.format("%03d", id);
	}

	public void replaceIcons() {
		this.goals = Scenario.replaceIcons(this.goals);
		this.effects = Scenario.replaceIcons(this.effects);
		for (Section section : sections.values()) section.replaceIcons();
	}

	public static String replaceIcons(String original) {
		if (original == null) return original;
		return original.replaceAll("\\[(.*?)\\]", "<img class=\"icon\" src=\"/img/icons/$1.png\">");
	}

}
