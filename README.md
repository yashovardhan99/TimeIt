# TimeIt
**Now with Timer support!**

[![](https://jitpack.io/v/yashovardhan99/TimeIt.svg)](https://jitpack.io/#yashovardhan99/TimeIt)
[ ![Download](https://api.bintray.com/packages/yashovardhan99/TimeIt/TimeIt/images/download.svg) ](https://bintray.com/yashovardhan99/TimeIt/TimeIt/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-TimeIt-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7369)

A simple and easy to use stopwatch and timer library for android
## Introduction
A stopwatch can be a very important widget in an app and yet creating it has been very difficult. Creating a stopwatch requires you to create a separate thread to keep track of the time and then post the updates on the UI thread. This process becomes complicated very quickly. Especially if you plan on adding features like pause and split.

Similarly, a timer is another important addon an app may need without going through all the hassle of running threads or handlers. 

With TimeIt, you can create a stopwatch or a timer app with very few lines of code!

For example, to create and start a stopwatch and display it with a textView :

```
Stopwatch stopwatch = new Stopwatch();
stopwatch.setTextView(myTextView);
stopwatch.start();
```
Pausing a stopwatch and resuming it is as simple as calling `stopwatch.pause();` and `resume()` respectively.

TimeIt also allows you to keep track of splits with the stopwatch by simply calling `stopwatch.split();`

Similarly, to set a timer:
```
Timer timer = new Timer(time_in_ms);
timer.setTextView(myTextView);
timer.start();
```
The timer provides utility functions to check the current time and current status (running, paused, stopped etc.). Please check the current status of the timer before actually starting/pausing/resuming the timer.

The Timer class provides an `OnTickListener` interface which you can register to, to listen for updates every clock cycle and receive a callback when the timer completes.

## Features
* **NEW** : Create Timers with pause and resume support!
* Easy to use stopwatch library
* No need to use separate threads. Multithreading is handled by the library itself
* Supports pause/resume and split methods.
* Supports an `OnTickListener` to listen for updates in clock.
* Set the TextView directly with TimeIt (Automatically formats the time).
* Set custom clock delay to update the time more or less frequently!
* Much more to come!

## Documentation
TimeIt is very easy to use. If you face any trouble, you can see the javadocs [available here](https://yashovardhan99.github.io/TimeIt/JavaDocs/). A demo app is included [here](https://github.com/yashovardhan99/TimeIt/tree/master/timeit-demo) for your reference. It implements the basic features of the library. 

## Download
TimeIt is available on jcenter, jitpack and bintray. To download, use the badges above or follow these instructions:

### Jcenter
Step 1. Add the JCenter repository to your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jcenter.bintray.com' }
		}
	}
```

Step 2 : Add the dependency in your app level build.gradle

```gradle
implementation 'com.yashovardhan99.timeit:timeit:1.2.0'
```

### Jitpack
Step 1. Add the JitPack repository to your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2 : Add the dependency in your app level build.gradle
```gradle
	dependencies {
	        implementation 'com.github.yashovardhan99:TimeIt:1.2.0'
	}
```
### Bintray

Step 1 : Download or copy the Gradle configuration in your project level build.gradle:
```gradle
repositories {
    maven {
        url  "https://dl.bintray.com/yashovardhan99/TimeIt" 
    }
}
        
```
Step 2 : Add the dependency in your app level build.gradle

```gradle
implementation 'com.yashovardhan99.timeit:timeit:1.2.0'
```


## Contributing
Contributing guidelines are available [here](https://github.com/yashovardhan99/TimeIt/blob/master/CONTRIBUTING.md). Feel free to report any issues or make new pull requests! TimeIt is an open source project and is free for all to use and improve! 

## License
   Copyright 2018 Yashovardhan Dhanania

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
