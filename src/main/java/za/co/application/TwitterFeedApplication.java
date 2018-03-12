package za.co.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import za.co.application.twitterfeed.FeedService;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class TwitterFeedApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TwitterFeedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		new FeedService().execute(args);
	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("TwitterFeed-");
		executor.initialize();
		return executor;
	}
}
