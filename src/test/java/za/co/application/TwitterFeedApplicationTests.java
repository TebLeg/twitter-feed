package za.co.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.application.twitterfeed.FeedService;
import java.io.File;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class TwitterFeedApplicationTests {

	@Test
	public void invalid_number_of_arguments_failure() throws Exception {

		String[] args = new String[] {"user.txt"};
		try {
			new FeedService().execute(args);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Number of arguments is invalid, there should be 2");
		}

	}

	@Test
	public void invalid_user_filename_failure() throws Exception {

		String[] args = new String[] {"user.txt", "tweet.txt"};
		try {
			new FeedService().execute(args);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "File user.txt not found.");
		}

	}

	@Test
	public void invalid_tweet_filename_failure() throws Exception {

		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("user.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), "twee.txt"};

		try {
			new FeedService().execute(args);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "File twee.txt not found.");
		}

	}

	@Test
	public void user_filename_does_not_exist_failure() throws Exception {

		String[] args = new String[] {"nofiles/user.txt", "nofiles/tweet.txt"};

		try {
			new FeedService().execute(args);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "File nofiles/user.txt not found.");
		}

	}

	@Test
	public void user_and_tweet_files_empty() throws Exception {

		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("emptyfiles/user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("emptyfiles/tweet.txt").getFile());

		String[] args = new String[] {file.getAbsolutePath(), tweetFile.getAbsolutePath()};
		FeedService service = new FeedService();
		service.execute(args);

		String expected = "Empty users file user.txt" + System.getProperty("line.separator");
		assertEquals(expected, service.printErrorList());

	}

	@Test
	public void user_and_tweet_filename_arguments_swapped() throws Exception {

		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("emptyfiles/tweet.txt").getFile());
		File tweetFile = new File(classLoader.getResource("emptyfiles/user.txt").getFile());

		String[] args = new String[] {file.getAbsolutePath(), tweetFile.getAbsolutePath()};

		try {
			new FeedService().execute(args);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid user file name: tweet.txt. It should be user.txt", e.getMessage());
		}

	}

	@Test
	public void tweet_filename_does_not_exist_success() throws Exception {

		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("user.txt").getFile());

		String[] args = new String[] {file.getAbsolutePath(), "nofiles/tweet.txt"};
		FeedService service = new FeedService();
		service.execute(args);

		String expected = "File " + args[1] + " not found." + System.getProperty("line.separator");
		assertEquals(expected, service.printErrorList());

	}

	@Test
	public void parse_file_not_ascii_format_success() {

		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("nonascii/user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("nonascii/tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		FeedService service = new FeedService();
		service.execute(args);

		String expected = "Illegal pattern in user line: Joe. Line skipped." + System.getProperty("line.separator") +
				"Text is not in ASCII format: R��al follows Alan. Line skipped." + System.getProperty("line.separator");
		assertEquals(expected, service.printErrorList());

	}

	@Test
	public void parse_users_file_skip_invalid_format() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("invaliduser/user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("invaliduser/tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		FeedService service = new FeedService();
		service.execute(args);

		String expected = "Illegal pattern in user line: Alan following Martin. Line skipped." + System.getProperty("line.separator") +
				"Illegal pattern in user line: . Line skipped." + System.getProperty("line.separator") +
				"Illegal pattern in user line: Joe this is wrong. Line skipped." + System.getProperty("line.separator");
		assertEquals(expected, service.printErrorList());
	}

	@Test
	public void parse_tweets_file_skip_invalid_format() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("invalidtweet/user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("invalidtweet/tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		FeedService service = new FeedService();
		service.execute(args);

		String expected = "Invalid tweet line pattern: Ward! There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.. Line skipped." + System.getProperty("line.separator") +
		"Invalid tweet length: Martin> This is a random long string This is a random long string This is a random long string This is a random long string This is a random long string This is a random long string This is a random long string This is a random long string This is a random long string. Max characters is 120 Line skipped." + System.getProperty("line.separator") +
		"Invalid tweet line pattern: Alan< If you have a procedure with 10 parameters, you probably missed some.. Line skipped." + System.getProperty("line.separator") +
		"Invalid tweet line pattern: Alan This is missing the > character after the name.. Line skipped." + System.getProperty("line.separator") +
		"Invalid tweet line pattern: Alan>Random numbers should not be generated with a method chosen at random.. Line skipped." + System.getProperty("line.separator");
		assertEquals(expected, service.printErrorList());
	}

	@Test
	public void parse_tweets_success() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File userFile = new File(classLoader.getResource("user.txt").getFile());
		File tweetFile = new File(classLoader.getResource("tweet.txt").getFile());

		String[] args = new String[] {userFile.getAbsolutePath(), tweetFile.getAbsolutePath()};
		FeedService service = new FeedService();
		service.execute(args);

		String expected = "There are no warnings or errors.";
		assertEquals(expected, service.printErrorList());
	}

}
