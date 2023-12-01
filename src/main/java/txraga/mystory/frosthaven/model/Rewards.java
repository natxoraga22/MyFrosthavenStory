package txraga.mystory.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class Rewards {

	private List<Scenario> scenarios;
	private String text;

}
