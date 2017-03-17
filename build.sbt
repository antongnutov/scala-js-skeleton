import NativePackagerHelper._
import sbt.Keys.crossTarget

organization in ThisBuild := "anton.gnutov"

version in ThisBuild := "0.1"

scalaVersion in ThisBuild := ScalaConfig.version

scalacOptions in ThisBuild := ScalaConfig.compilerOptions.value

val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

lazy val root = project.in(file(".")).
  aggregate(client, server).
  settings(
    name := "scala-js-skeleton root"
  )

lazy val app = crossProject.in(file("./app")).
  settings(
    name := "scala-js-skeleton"
  ).
  jvmSettings(

    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % Versions.http4s,
      "org.http4s" %% "http4s-blaze-server" % Versions.http4s,

      "com.typesafe" % "config" % Versions.config,

      "org.apache.logging.log4j" % "log4j-api" % Versions.log4j,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % Versions.log4j
    ),

    mappings in (Compile, packageDoc) := Seq(),

    mappings in Universal ++= directory("webapp")

  ).enablePlugins(JavaAppPackaging).
  jsSettings(

    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % Versions.scalaTags
    ),
    jsDependencies ++= Seq(),

    // Include the JavaScript dependencies
    skip in packageJSDependencies := false,

    // copy jsdeps and launcher into static folder
    Seq(packageMinifiedJSDependencies, packageScalaJSLauncher) map { packageJSKey =>
      crossTarget in(Compile, packageJSKey) := scalajsOutputDir.value
    },

    // the same js filename for `fastOptJS` and `fullOptJS`
    artifactPath in Compile in fastOptJS := scalajsOutputDir.value / ((moduleName in fastOptJS).value + ".js"),
    artifactPath in Compile in fullOptJS := scalajsOutputDir.value / ((moduleName in fullOptJS).value + ".js"),

    persistLauncher in Compile := true,
    persistLauncher in Test := false
  )

lazy val client = app.js.settings(
  scalajsOutputDir := file("webapp") / "js",

  cleanFiles ++= Seq(scalajsOutputDir.value)
)

lazy val server = app.jvm.settings(
  bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/app.conf"""",
  bashScriptExtraDefines += """addJava "-Dlog4j.configurationFile=${app_home}/../conf/log4j2.xml"""",
  bashScriptExtraDefines += """addJava "-server"""",
  bashScriptExtraDefines += """addJava "-Xmx96M""""
)
