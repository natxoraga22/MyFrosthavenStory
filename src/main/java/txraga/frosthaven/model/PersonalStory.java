package txraga.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class PersonalStory {
	
	private List<FhCharacter> party;
	private List<StoryItem> story;

}
