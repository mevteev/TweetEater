# TweetEater
Tweet processing tool

Process tweets from Twitter and show three most populat words for each country
Process only messages with locations

Spring Boot, Twitter4j
http://localhost:8000

Add <i>twitter4j.properties</i> file to resource folder with the following content:

debug=false
oauth.consumerKey=<i>your consumer key</i>
oauth.consumerSecret=<i>your consumer secret</i>
oauth.accessToken=<i>your access token</i>
oauth.accessTokenSecret=<i>your access token secre</i>
twitter4j.loggerFactory=twitter4j.NullLoggerFactory

more info: http://twitter4j.org/en/configuration.html


