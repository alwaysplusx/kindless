package com.harmony.kindless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wuxii
 */
@EnableDiscoveryClient
@SpringBootApplication
public class KindlessGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(KindlessGatewayApplication.class, args);
	}

//	@Bean
//	public RouteLocator routes(RouteLocatorBuilder builder) {
//		// @formatter:off
//		return builder.routes()
//				.route("user", r -> r.path("/user/**")
//									.uri("lb://kindless-core"))
//				.route("moment", r -> r.path("/moment/**", "/moments/**")
//						            .uri("lb://kindless-moment"))
//				.build();
//		// @formatter:on
//	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}

	@RestController
	public static class GreetingController {

		@Autowired
		@LoadBalanced
		private RestTemplate restTemplate;

		@GetMapping("/greeting/{username}")
		public Object greeting(@PathVariable("username") String username) {
			return restTemplate.getForEntity("lb://kindless-core/user/u/" + username, Object.class);
		}

	}

}
