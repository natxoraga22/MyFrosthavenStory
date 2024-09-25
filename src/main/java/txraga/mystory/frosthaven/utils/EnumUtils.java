package txraga.mystory.frosthaven.utils;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.MessageSource;


public class EnumUtils {

	public static <E extends Enum<E>> Map<String,String> toMap(Class<E> enumClass, MessageSource messageSource, String messageKey, Locale locale) {
		return Stream.of(enumClass.getEnumConstants()).collect(Collectors.toMap(
			Enum::name,
			enumConstant -> messageSource.getMessage(messageKey + "." + enumConstant.name(), null, locale),
			(a, b) -> a,
			LinkedHashMap::new
		));
	}

}
