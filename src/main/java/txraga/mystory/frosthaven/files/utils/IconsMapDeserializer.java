package txraga.mystory.frosthaven.files.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class IconsMapDeserializer extends JsonDeserializer<Map<String,String>> {

	@Override
	public Map<String,String> deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
		Map<String,String> originalMap = parser.readValueAs(new TypeReference<Map<String,String>>() {});
		Map<String,String> modifiedMap = new HashMap<>();
		originalMap.forEach((key, value) -> modifiedMap.put(key, JsonUtils.replaceIcons(value)));
		return modifiedMap;
	}

}
