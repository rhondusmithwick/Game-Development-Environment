# Java Social Utility

Our utility for the final project is to allow users to login using social accounts and post, notify, or challenge other users. In order to get this up quickly, we are releasing an initial version with just some simple features implemented (only Facebook) so that teams can get this integrated right away. Over this readme, I will explain some example code for the utility, as well as how to set up a Facebook Game in order to take advantage of it.

### About Java Social

Our utility allows various projects to keep track of users and high scores, as well as let users post about them. Our original vision was to incorporate Facebook, Twitter and other apps into one environment using the Scribe library. However, using social APIs like Facebook is a lot harder in Java than in other languages, and for that reason, we want to make sure we can at least do Facebook. Additionally, the Twitter API has proven very difficult to work with under Scribe, so we may switch to another social app. Currently, we have a few actions supported for Facebook that you can use:
* Custom posting
* Challenging other users
* Notifying users from the app
* Posting about your current global ranking
* Posting about the global high score board

Additionally, the high score board can be sorted by highest/lowest score, alphabetical by email, and oldest/newest scores. 

### Setup

To set up the app you need to do a few things:

1. Add the given jars to the project. Our project uses the Scribe Java library to do Oauth requests. This is very necessary in Java so we don't reinvent the wheel. So, there are two jars associated with that. Additionally, in order to make xstream work we had to add some jars that Scribe depends on. At the moment, we are not sure why it's necessary but we'll delete them as soon as we can
2. Fill out the secret.properties file. We are providing an example. This file will give the app direction on where to put things and give it important id/secret info for logins. Please first create a directory for storing users and fill out the "userfolder" line in the properties file. Also please note: *DO NOT MAKE THIS SECRET FILE PUBLIC*. You may be fine to publish it to your private repo, but if you ever decide to make your project public, do NOT publish it. It will allow bad people with too much time to spam you on Facebook.

To set up Facebook:
1. Go to the [Facebook Developer's Page](https://developers.facebook.com/).
2. Sign in/create a new developer's account.
3. In the top right corner, hover over "My Apps" and select "Add a new app"
4. Choose website
5. Type the name of your app. Go wild. Don't let your dreams be dreams. Then hit "Create New Facebook App ID"
6. Give them your email and choose the category as "Games". *THIS IS A CRUCIAL STEP*. Only games have access to certain API calls. Then select the sub category. This can be anything. You do you.
7. Hit "Create APP ID"
8. Hit "Skip Quick Start".
9. Go to the "Settings" tab, click "Add platform" and select "Website".
10. Fill out the website for you. This can really be anything, but it's the page that you'll be redirected to when you, say, login. We have our's as https://duke.edu/ and suggest you just do the same.
11. Hit "Save" and copy this URL into your secret.properties file into the "callback" field.
12. Now, still in settings, copy the "App ID" into the "facebookId" field of the secret.properties file and the "App Secret" into the "facebookSecret" field.
13. If you want to add anyone as a tester, you need to add them in the "Roles" tab. Please note that while in development, certain APIs may not work for users that aren't testers. Also, you'll never get Facebook to approve your fake app to get it out of development, so stick with it. Relax.

That's it! If I missed something please let me know (tjr15@duke.edu).

### Example Code

The main social class is called `JavaSocial.java` and can be created by:

```java
JavaSocial mySocial = new JavaSocial();
```
This will do a few things. First, it loads all the users from the specified folder through xstream and then signs in the applications (note: not the users, just the applications). Then, you can sign in a user with:
```java
mySocial.loginUser(SocialType.FACEBOOK);
```
What this does is tells the JavaSocial app to login a user using the "FACEBOOK" social type. This pops up a web view in Java for the user to sign in. Note that this needs to happen in order for a user to log in. Apps like Facebook only allow users to login using a browser window, because the user must accept certain permissions within their site. Once the user is logged in, you can use the app to complete actions like send a post. To do this you would need to write: 
```java
myUser = mySocial.getActiveUser();
myUser.getProfiles().getActiveProfile().customPost("Hello Facebook!");
```
This mechanism allows you to do certain actions on any platform, and you don't need to worry about how your user is signed in. However he/she is signed in, the app will use that social type to do its actions. So here, since the user signed into Facebook, he'll simply post to Facebook.

There is example code in facebookutil/test/TestFacebook.java . If you run this, you'll be able to log in, then post, notify users (if they've been created) and write the users to xml.
