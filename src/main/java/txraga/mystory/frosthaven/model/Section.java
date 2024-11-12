package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;
import txraga.mystory.frosthaven.files.utils.IconsMapDeserializer;


@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Section {
	
	@ToString.Include
	private String id;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String trigger;

	@ToString.Include
	private String title;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<UnlockedBy> unlockedBy;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String goals;

	private String introduction;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String text;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String specialRules;

	@JsonDeserialize(using = IconsMapDeserializer.class)
	private Map<String,String> bossSpecials = Map.of();

	@JsonDeserialize(using = IconsDeserializer.class)
	private String conclusion;

	private Rewards rewards;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String sectionLinks;

	private boolean containsImage;
	private List<String> unlockedSections = List.of();


	public String getHtmlSafeId() {
		return id.replace(".", "_");
	}

}
