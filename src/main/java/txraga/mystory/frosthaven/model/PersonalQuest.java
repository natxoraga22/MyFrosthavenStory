package txraga.mystory.frosthaven.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;


@Getter
@Setter
@ToString
public class PersonalQuest {

	private String id;
	private String title;
	private String description;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String requirements;
	
	private Building building;
	private Building altBuilding;

}
