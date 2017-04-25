# <img src="https://github.com/The-LoneWolf/AndroidWordpressJsonApiClient/raw/master/img/icon.png" width="64px" height="64px" > AndroPress
An Open Source Android Client for self hosted wordpress blogs that works with json-api plugin

<img src="https://github.com/The-LoneWolf/AndroPress/raw/master/img/all2.png">

<a href="https://github.com/The-LoneWolf/AndroPress/wiki/Recent-Screenshots">Recent Screenshots</a>

## Instructions
1 Install <a href="https://wordpress.org/plugins/json-api/">JSON API wordpress plugin</a> and activate it.<br>
2 Just Copy your api adress to baseUrl variable in <a href="https://github.com/The-LoneWolf/AndroPress/blob/master/app/src/main/java/ir/technopedia/wordpressjsonclient/util/NetUtil.java">NetUtils.java</a><br>
&nbsp;&nbsp;&nbsp;&nbsp; ** if your domain adress is http://test.com your json api default adress would be http://test.com/api/
<br>
3 Copy your google-services.json files data to <a href="https://github.com/The-LoneWolf/AndroPress/blob/master/app/google-services.json">google-services.json</a> in app folder and you are ready to go.<br><br>

more Instructions on development and other stuff witll be added :)<br>

<b>New screenshots with more instructions & test apk & test website is available <a href="http://mgarebaghi.ir/en/2016/10/15/android-wordpress-json-api-client/">here</a>.</b>
<br><br>

## Download sample apk
<a href="http://mgarebaghi.ir/AndroPress_1.2.apk"><img src="https://github.com/The-LoneWolf/AndroPress/raw/master/img/download.png" width="64px" height="64px" ></a>

## Things Done
### V1
1 Android 4+ and Material design app with apcompat library<br>
2 Custom dynamic navigation menu (dynamic by loading categories)<br>
3 Loading and viewing posts and comments (lazy load)<br>
4 Submitting comment with dialog<br>
5 Badge to show comment counts in post detail activity.<br>
6 Used swipe to close effect on post detail activity and comments activity.<br>

### V1.1
7 Add Splash Screen<br>
8 Solved some errors<br>
9 Add view for empty comment & post list.<br>
10 Push Notification Added With FCM(FireBase Cloud Messaging)<br>
11 Add Material Design Colors to colors.xml<br>
12 Improve Some Views Like Comment Dialog view<br>
13 Cache system to load categories in navigation menu if there is no internet connection<Br>

###V1.2
14 Add aearch fragment<br>
15 Add about us<br>
16 Add archive for offline saving<br>
17 Add reporting System<br>
18 Add double back press to exit<br>
19 Fixed commenting system issues<br>
20 Updated commenting dialog ui<br>
21 Updated posts card ui<br>
22 Updated posts detail activity ui<br>
23 Fixed reloading post list issue on rotate<br>

## Things to do
1 Add Transition Effects<br>
2 Add Settings<br>
3 Add Different Views<br>
....<br>

## Things cannot be done
1 <s>Add Signup and Login if possible</s><br>

## Libraries Used in this project
1. <a href="https://github.com/loopj/android-async-http">loopj async http </a><br>
2. <a href="https://github.com/Jude95/SwipeBackHelper">Swipe Back Helper</a><br>
3. <a href="http://square.github.io/picasso/">Square Picasso image loading</a><br>
