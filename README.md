# Scala.js project skeleton
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.14.svg)](https://www.scala-js.org)

---

## Project structure
 * Backend code is in `app/jvm`. This includes code for the web server and connecting to the database. It will run on the JVM.
 * Frontend code is in `app/js`. This code will be translated to JavaScript and run in the client's browser.
 * The code shared between frontend and backend is in `app/shared`.

## Backend
 * [Scala](https://www.scala-lang.org/): Implements the server logic
 * [http4s](http://http4s.org/): Provides the HTTP routing

## Frontend
 * [Scala.js](https://www.scala-js.org/): Compiles Scala to JavaScript
 * [Pure.css](https://purecss.io/): A set of small, responsive CSS modules
