name := "scala-testing"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq("org.scalatest" % "scalatest_2.11" % "2.2.6" % "test" withSources() withJavadoc(),
  "joda-time" % "joda-time" % "2.8.2" withSources() withJavadoc(),
  "junit" % "junit" % "4.12" withSources() withJavadoc(),
  "org.mockito" % "mockito-core" % "1.9.5" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.13.0" withSources() withJavadoc(),
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" withSources() withJavadoc(),
  "org.scalamock" %% "scalamock-specs2-support" % "3.2.2" withSources() withJavadoc()
//  "org.specs2" %% "specs2-core" % "3.7.2" % "test"
//  "org.specs2" %% "specs2" % "3.7" % "test"

)
