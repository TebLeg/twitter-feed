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
	public Executor executorService() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(100);
		executor.setKeepAliveSeconds(10); //10s a pool that remains idle for long enough will not consume any resources
		executor.setThreadNamePrefix("TwitterFeed-");
		executor.initialize();
		return executor;
	}

}
