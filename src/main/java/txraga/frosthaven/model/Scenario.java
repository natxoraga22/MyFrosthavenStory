package txraga.frosthaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Scenario {
    
    private int id;
    private String coordinates;
    private String name;
    private int complexity;
    private String location;

    private String goals;
    private String specialRules;
    private String introduction;

}
