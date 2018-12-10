# TimeIt
A simple and easy to use stopwatch library for android
## Introduction
A stopwatch can be a very important widget in an app and yet creating it has been very difficult. Creating a stopwatch requires you to create a seperate thread to keep track of the time and then post the updates on the UI thread. This process becomes complicated very quickly. Especially if you plan on adding features like pause and split.

With TimeIt, you can create a stopwatch app with very few lines of code!

For example, to create and start a stopwatch and display it with a textView :

```
Stopwatch stopwatch = new Stopwatch();
stopwatch.setTextView(myTextView);
stopwatch.start();
```
Pausing a stopwatch and resuming it is as simple as calling `stopwatch.pause();` and `resume()` respectively.

TimeIt also allows you to keep track of splits with the stopwatch by simply calling `stopwatch.split();`
<!--
## Features
To be completed-->

## Download
TimeIt is not yet available for download. Clone this repo and import the `timeit` module in your android project to use it for now.
<!--
## Contributing
To be updated

## License
To be added
-->
