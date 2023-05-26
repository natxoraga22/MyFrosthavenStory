package txraga.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StoryItem {

	private String scenario;
	private List<String> path;
	
}
