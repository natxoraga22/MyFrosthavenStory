package txraga.mystory.frosthaven.files.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;


public class IconsDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		String original = parser.getValueAsString();
		return JsonUtils.replaceIcons(original);
	}

}