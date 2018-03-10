# twitter-feed

## Description
twitter-feed is a JAVA application that simulates a twitter-like feed. It accepts two seven-bit ASCII files. The first file contains a list of users and their followers. The second file contains tweets. Given the users, followers and tweets, it displays a simulated twitter feed for each user to the console.

## Installation
The application can be installed on any OS. The files to be tested must be in the same folder or directory as the application. 

## Usage
On the console execute the command: java -jar twitter-feed user.txt tweet.txt

## Files and assumptions
The following assumptions about the files are made:
1. Each line of a well-formed user file contains a user, followed by the word 'follows' and then a comma separated list of users they follow. Where there is more than one entry for a user, consider the union of all these entries to determine the users they follow.
2. Lines of the tweet file contain a user, followed by greater than, space and then a tweet that may be at most 140 characters in length. 
3. The tweets are considered to be posted by the each user in the order they are found in this file.
The program needs to write console output as follows. For each user / follower (in alphabetical order) output their name on a line. Then for each tweet, emit a line with the following format: <tab>@user: <space>message.
4. For the user.txt file if it is in the following format:

```
    Ward follows Alan

    Alan following Martin

    Ward follows Martin, Alan

    Joe this is wrong
    Mark follows
    Tom
    Réal follows Alan
``` 
  The the empty lines will be skipped, if there are no words after the user's name then the user follows no one but is correctly handled and any line with non ASCII charcters will also be skipped.

## Author
Tebogo Legoabe (tebogo.legoabe@gmail.com)
