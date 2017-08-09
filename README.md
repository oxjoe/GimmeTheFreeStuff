Shows you the newest listings of Craigslist by occasionally refreshing the sites (E.g. https://chicago.craigslist.org/search/zip) free stuff section!

***How to Install:*** Copy the folder named "GTFS App" to desktop and double click on "GimmeTheFreeStuff.jar" to start it.

## Features
- Gets brand new listings every few minutes
  - The items shown in the application will not match what is shown in your web browser. This is because the application shows the **newest** listings, **not** listings that have been updated. The site by default, shows both newest and updated listings.
- Color coded to see the newest listings (easier to tell which ones you've seen before)
  - Green = New listing
  - Red = Old listing
- Automatically checks for new updates

## Screenshots
Main UI: 
![alt text](https://github.com/oxjoe/GimmeTheFreeStuff/blob/master/screenshots/MainUI.png "Main UI")

Settings UI: 
![alt text](https://github.com/oxjoe/GimmeTheFreeStuff/blob/master/screenshots/SettingsUI.png "Settings UI")


## Notes

1. Table contains at most 120 listings, because the 121<sup>st</sup> listing is probably already gone.

2. Finally, if you are familiar with Java please do critique my code! (wrote this so I could learn how to Java)

### Program Data
Size: 369 KB

Written in JavaFX and uses jsoup to parse the URL and navigate/extract DOM's

### To Be Added Eventually
- Ignore certain words/phrases
- Alerts you of certain words/phrases
- Tells you how far the place is away using Google Maps API
- Search bar that highlights words you type in
- Description of listing (maybe pictures too)
- Run on Startup
- Minimize to system tray
- UI improvements (atm its not very nice looking :()