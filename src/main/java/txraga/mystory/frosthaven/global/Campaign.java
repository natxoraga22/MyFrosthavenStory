package txraga.mystory.frosthaven.global;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.FhCharacter;
import txraga.mystory.frosthaven.model.OutpostPhase;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.StoryObject;
import txraga.mystory.frosthaven.model.personal.PersonalStory;
import txraga.mystory.frosthaven.model.personal.StoryItem;


@Getter
public class Campaign {

	private final Frosthaven frosthaven;
	private final FrosthavenStory fhStory;

	private CampaignTracker tracker;

	private Map<String,FhCharacter> originalParty = Map.of();
	private Map<String,FhCharacter> party = Map.of();
	private List<StoryObject> story = new ArrayList<>();


	public Campaign(PersonalStory personalStory, Frosthaven frosthaven, FrosthavenStory fhStory) {
		this.frosthaven = frosthaven;
		this.fhStory = fhStory;
		this.tracker = new CampaignTracker(frosthaven);

		if (personalStory != null) {
			party = fhStory.getParty(personalStory.getParty());
			originalParty = new LinkedHashMap<>(party);

			int outpostPhaseId = 1;
			for (StoryItem storyItem : personalStory.getStory()) {
				// Event
				if (storyItem.getEvent() != null) {
					Event event = fhStory.getEvent(storyItem.getEvent());
					if (event != null) {
						story.add(event);
						tracker.trackEvent(event);
					}
				}
				// Scenario
				else if (storyItem.getScenario() != null) {
					Scenario scenario = fhStory.getScenario(storyItem.getScenario());
					if (scenario != null) {
						story.add(scenario);
						tracker.trackScenario(scenario);
					}
				}
				// Outpost phase
				else if (storyItem.getOutpostPhase() != null) {
					OutpostPhase outpostPhase = fhStory.getOutpostPhase(storyItem.getOutpostPhase(), outpostPhaseId, party);
					if (outpostPhase != null) {
						story.add(outpostPhase);
						tracker.trackOutpostPhase(outpostPhase);
					}
					outpostPhaseId++;
				}
			}
		}
	}

}
