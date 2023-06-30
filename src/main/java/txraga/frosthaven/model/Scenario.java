package txraga.frosthaven.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.frosthaven.model.utils.ModelUtils;


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
	private Map<String,Section> sections = Map.of();

	private List<String> path;


	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.SCENARIO;
	}

	public void populate() {
		// Populate sections
		for (Entry<String,Section> sectionEntry : sections.entrySet()) {
			Section section = sectionEntry.getValue();
			section.populate(sectionEntry.getKey(), true);
		}
		replaceIcons();
	}

	public void replaceIcons() {
		this.goals = ModelUtils.replaceIcons(this.goals);
		this.effects = ModelUtils.replaceIcons(this.effects);
	}

}
