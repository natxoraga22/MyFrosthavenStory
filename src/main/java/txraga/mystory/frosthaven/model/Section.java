package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.model.personal.StoryItem;


@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Section {
	
	@ToString.Include private String id;
	private boolean partOfScenario;

	private String trigger;
	@ToString.Include private String title;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<StoryItem> unlockedBy;

	private String introduction;
	private String text;
	private String specialRules;
	private Map<String,String> bossSpecials = Map.of();
	private String conclusion;
	private Rewards rewards;
	private String sectionLinks;


	public String getHtmlSafeId() {
		return id.replace(".", "_");
	}

	public boolean hasMap(String scenarioId) {
		Resource resource = new ClassPathResource("static/img/scenarios/" + scenarioId + "_" + id + ".png");
		return resource.exists();
	}

	public void populate(String id, boolean partOfScenario) {
		// Set section id and partOfScenario
		if (this.id == null) this.id = id;
		this.partOfScenario = partOfScenario;
		replaceIcons();
	}

	public void replaceIcons() {
		this.trigger = ModelUtils.replaceIcons(this.trigger);
		this.specialRules = ModelUtils.replaceIcons(this.specialRules);
		for (Entry<String,String> bossSpecial : bossSpecials.entrySet()) {
			bossSpecials.put(bossSpecial.getKey(), ModelUtils.replaceIcons(bossSpecial.getValue()));
		}
		if (this.rewards != null) this.rewards.setText(ModelUtils.replaceIcons(this.rewards.getText()));
		this.sectionLinks = ModelUtils.replaceIcons(this.sectionLinks);
	}

}
