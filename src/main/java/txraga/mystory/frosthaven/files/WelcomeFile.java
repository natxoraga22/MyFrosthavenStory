package txraga.mystory.frosthaven.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.XSlf4j;


@XSlf4j
@Component
public class WelcomeFile {

	private final static String WELCOME_FILE_PATH = "static/json/welcome.txt";


	public String getWelcome() {
		log.entry();
		try {
			InputStream welcomeInputStream = new ClassPathResource(WELCOME_FILE_PATH).getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(welcomeInputStream, StandardCharsets.UTF_8));
			List<String> welcomeLines = bufferedReader.lines().collect(Collectors.toList());
			return log.exit(String.join("<br/>", welcomeLines));
		}
		catch (IOException e) {
			log.warn("Error reading file '" + WELCOME_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

}
