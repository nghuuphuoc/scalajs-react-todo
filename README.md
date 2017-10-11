# Introduction

A simple app written in ScalaJS and ReactJS to demonstrate a few ways to optimize React performance.
It is used in my talk of [Optimize React Performance - Tools and Good Practices](http://slides.com/nghuuphuoc/react-performance-tools-and-good-practices/).

# Run it on your local

You can follow the steps below to run it locally:
* Clone this repository
* Go to the cloned directory
* Run command to build the project
```
$ sbt
> fastOptJS
```
* Create a simple HTTP server to host the current directory
```
$ python -m SimpleHTTPServer 9000
```
You can change the port number 9000 to other number as long as it isn't used by any application.

Now, you can access the app at `http://localhost:9000`.
