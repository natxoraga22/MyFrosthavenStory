package txraga.frosthaven.model;


public abstract class StoryObject {
	
	public enum Type {
		SCENARIO,
		OUTPOST,
		EVENT
	}
	
	public abstract Type getObjectType();

}
