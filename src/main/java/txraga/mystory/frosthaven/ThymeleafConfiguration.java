package txraga.mystory.frosthaven;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import csb.btd.BootstrapDialect;
import csb.btd.BootstrapInputsDialect;


@Configuration
public class ThymeleafConfiguration {

	@Bean
	public BootstrapDialect bootstrapDialect() {
		return new BootstrapDialect();
	}

	@Bean
	public BootstrapInputsDialect bootstrapInputsDialect() {
		return new BootstrapInputsDialect();
	}

}
