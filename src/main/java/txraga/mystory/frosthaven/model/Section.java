package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;
import txraga.mystory.frosthaven.files.utils.IconsMapDeserializer;
import txraga.mystory.frosthaven.model.personal.StoryItem;


@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Section {
	
	@ToString.Include private String id;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String trigger;

	@ToString.Include private String title;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<StoryItem> unlockedBy;

	private String introduction;
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


	public String getHtmlSafeId() {
		return id.replace(".", "_");
	}

	public boolean hasMap(String scenarioId) {
		if (scenarioId == null) return false;
		Resource resource = new ClassPathResource("static/img/scenarios/" + scenarioId + "_" + id + ".png");
		return resource.exists();
	}

	public void populate(String id) {
		// Set section id
		if (this.id == null) this.id = id;
	}

}
