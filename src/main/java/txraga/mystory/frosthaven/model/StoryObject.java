package txraga.mystory.frosthaven.model;


public abstract class StoryObject {
	
	public enum Type {
		SCENARIO,
		OUTPOST_PHASE,
		EVENT
	}
	
	public abstract Type getObjectType();

}
