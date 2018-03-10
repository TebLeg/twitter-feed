package za.co.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import za.co.application.twitterfeed.FeedService;

@SpringBootApplication
public class TwitterFeedApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TwitterFeedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		new FeedService().execute(args);
	}
}
