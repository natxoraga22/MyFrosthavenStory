package txraga.frosthaven.model;


public abstract class StoryObject {
	
	public enum Type {
		SCENARIO,
		EVENT
	}
	
	public abstract Type getType();

}
