package txraga.frosthaven.model.personal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.frosthaven.model.FhCharacter;


@Getter
@Setter
@ToString
public class PersonalStory {
	
	private List<FhCharacter> party;
	private List<StoryItem> story;

}
