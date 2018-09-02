Shows you the newest listings of Craigslist by occasionally refreshing the sites (E.g. https://chicago.craigslist.org/search/zip) free stuff section!

***How to Install:*** Copy the folder named "GTFS App" somewhere to your computer and double click on "GimmeTheFreeStuff.jar" to start it.

## Features
- Gets brand new listings every few minutes
  - The items shown in the application will not match what is shown in your web browser. This is because the application shows the **newest** listings and doesn't include listings that have been updated. The site by default, shows both **newest** and **updated** listings which results in some items from a week ago appearing at the top of your search since they have been bumped by the ad lister
- Color coded to see the newest listings (easier to tell which ones you've seen before)
  - Green = New listing
  - Red = Old listing
- Automatically checks for new updates

## Screenshots
![alt text](https://github.com/oxjoe/GimmeTheFreeStuff/blob/master/screenshots/MainUI.png "Main UI")

![alt text](https://github.com/oxjoe/GimmeTheFreeStuff/blob/master/screenshots/SettingsUI.png "Settings UI")


## Notes
1. On startup, every value is false (every row is red) since this program assumes that you have just looked at the website and you are just waiting for it to refresh.
2. Table contains at most 120 listings, because the 121<sup>st</sup> listing is probably already gone.

## How to Build
Uses Zenjava-Javafx-maven-plugin, so just load in "pom.xml" and use 'mvn jfx:jar' command to create the jar file which will be placed in "GimmeTheFreeStuff\target\jfx\app". You have to manually copy "default.properties" and "user.properties" to the \app folder though.


### Program Data
Size: 412 KB

Written in JavaFX and uses jsoup to parse the URL and navigate/extract DOM's
