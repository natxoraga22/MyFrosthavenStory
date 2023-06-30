package txraga.frosthaven.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Scenario extends StoryObject {
	
	private String id;
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

	public void populate(List<String> path) {
		// Set scenario path
		if (path != null) this.path = path;

		// Set sections ids
		for (Entry<String,Section> sectionEntry : sections.entrySet()) {
			Section section = sectionEntry.getValue();
			if (section.getId() == null) section.setId(sectionEntry.getKey());
		}
		replaceIcons();
	}

	public void replaceIcons() {
		this.goals = ModelUtils.replaceIcons(this.goals);
		this.effects = ModelUtils.replaceIcons(this.effects);
		if (sections != null) {
			for (Section section : sections.values()) section.replaceIcons();
		}
	}

}
