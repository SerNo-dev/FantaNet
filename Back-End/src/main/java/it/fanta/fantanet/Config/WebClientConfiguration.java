package it.fanta.fantanet.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().exchangeStrategies(exchangeStrategies -> {
            exchangeStrategies
                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10)); // Imposta il limite a 10 MB
        });
    }
}
