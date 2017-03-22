# Scala.js project skeleton
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.15.svg)](https://www.scala-js.org)

---

## Get started
### Run the server locally
1. Install [SBT](http://www.scala-sbt.org/)
2. Start SBT in the project's root directory
3. Compile client module: `appJS/fullOptJS`
4. Start server: `appJVM/run`
5. Point your browser to `localhost:8080`
6. Stop running application by stopping sbt with `crtl + C`

## Project structure
 * Backend code is in `app/jvm`. This includes code for the web server and connecting to the database. It will run on the JVM.
 * Frontend code is in `app/js`. This code will be translated to JavaScript and run in the client's browser.
 * The code shared between frontend and backend is in `app/shared`.
 * Static resources are in `webapp`

## Backend
 * [Scala](https://www.scala-lang.org/): Implements the server logic
 * [http4s](http://http4s.org/): Provides the HTTP routing

## Frontend
 * [Scala.js](https://www.scala-js.org/): Compiles Scala to JavaScript
 * [Pure.css](https://purecss.io/): A set of small, responsive CSS modules

## [View results](https://antongnutov.github.io/scala-js-skeleton)
