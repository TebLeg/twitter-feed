package za.co.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.application.twitterfeed.FeedService;

import java.io.File;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class TwitterFeedApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalid_number_of_arguments_failure() throws Exception {

		String[] args = new String[] {"user.txt"};
		new FeedService().execute(args);

	}

	@Test(expected = IllegalArgumentException.class)
	public void invalid_user_filename_failure() throws Exception {

		String[] args = new String[] {"user.txt", "tweet.txt"};
		new FeedService().execute(args);

	}

	@Test(expected = IllegalArgumentException.class)
	public void invalid_tweet_filename_failure() throws Exception {

		String[] args = new String[] {"user.txt", "twee.txt"};
		new FeedService().execute(args);

	}

	@Test(expected = IllegalArgumentException.class)
	public void user_filename_does_not_exist_failure() throws Exception {

		String[] args = new String[] {"files/user.txt", "files/tweet.txt"};
		new FeedService().execute(args);

	}

	@Test(expected = IllegalArgumentException.class)
	public void tweet_filename_does_not_exist_failure() throws Exception {

		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("user.txt").getFile());

		String[] args = new String[] {file.getAbsolutePath(), "files/tweet.txt"};
		new FeedService().execute(args);

	}

	@Test
	public void parse_file_not_ascii_format_success() {

		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("nonascii/user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("nonascii/tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		new FeedService().execute(args);

	}

	@Test
	public void parse_users_file_skip_invalid_format_failure() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("invaliduser/user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("invaliduser/tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		new FeedService().execute(args);
	}

	@Test
	public void parse_tweets_file_skip_invalid_format_failure() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("invalidtweet/user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("invalidtweet/tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		new FeedService().execute(args);
	}

	@Test
	public void parse_tweets_file_invalid_number_of_characters_failure() {

	}

	@Test
	public void parse_tweets_success() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		new FeedService().execute(args);
	}

	@Test
	public void parse_ascii_files_success() {

	}

	@Test
	public void display_twitter_feed_failure() {

	}

	@Test
	public void display_twitter_feed_success() {

	}
}
