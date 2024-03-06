package txraga.mystory.frosthaven.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalController {

	@Value("${aws.cloudfront.url}") private String imgUrl;

	@ModelAttribute("imgUrl")
	private String getContextPath() {
		return imgUrl;
	}

}
