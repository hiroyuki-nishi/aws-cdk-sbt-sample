import Dependencies._
import com.typesafe.config.ConfigFactory
import sbt.Keys.libraryDependencies

val envName = sys.env.getOrElse("ENV_NAME", "dev")
val regionName = sys.env.getOrElse("AWS_REGION", "ap-northeast-1")
val contextName = "sign"

lazy val commonSettings = Seq(
  organization := "sample",
  scalaVersion := "2.12.8",
  scalacOptions := Seq(
    "-deprecation",
    "-feature"
  ),
  scalafmtOnCompile in ThisBuild := true,
  test in assembly := {}
)


val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case _ => MergeStrategy.first
  },
  assemblyJarName in assembly := s"${name.value}.jar",
  publishArtifact in(Compile, packageBin) := false,
  publishArtifact in(Compile, packageSrc) := false,
  publishArtifact in(Compile, packageDoc) := false
)

lazy val root = (project in file("."))
  .aggregate(
    presentation,
    graaIVM
  )
  .settings(commonSettings: _*)
  .settings(
    commonSettings,
    publishArtifact := false
  )
  .settings(
    commands += Command.command("assemblyAll") { state =>
      "helloWorld/assembly" ::
      "signConsumer/assembly" ::
        state
    }
  )

val image_version = taskKey[String]("image_version")
image_version := {
  val v = version.value
  println(v)
  v
}

lazy val cdk = (project in file("modules/cdk"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= cdkDependencies
  )

// domain
lazy val domain = (project in file("modules/domain"))
  .settings(commonSettings: _*)

// presentation
lazy val presentation = (project in file("modules/adapter/presentation"))
  .aggregate(
    helloWorld
  )
  .settings(
    name := "sign-presentation",
    publishArtifact := false
  )

lazy val helloWorld = (project in file("modules/adapter/presentation/helloworld"))
  .dependsOn(application, infraSQS)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= lambdaDependencies
  )

lazy val graaIVM = (project in file("modules/adapter/presentation/graaivm"))
  .dependsOn(application)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= lambdaDependencies
  )

lazy val signConsumer = (project in file("modules/adapter/presentation/sign"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn(application, infraS3)
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    libraryDependencies ++= s3Dependencies
  )
  .settings(
    name := s"$contextName-consumer-sign",
    dockerBaseImage := "openjdk:8-jre-alpine",
    dockerExposedPorts := Seq(9010),
    mainClass in assembly := Some("example.com.Main"),
    javaOptions in Universal ++= Seq(
      "-J-Xms1024m",
      "-J-Xmx2048m"
    )
  )

// application
lazy val application = (project in file("modules/application"))
  .settings(commonSettings: _*)
  .dependsOn(service)
  .settings(
    libraryDependencies ++= testDependencies
  )


// service
lazy val service = (project in file("modules/service"))
  .settings(commonSettings: _*)
  .dependsOn(domain)

// infrastructure
lazy val infrastructure = (project in file("modules/adapter/infrastructure"))
  .settings(commonSettings: _*)
  .aggregate(
    infraS3,
    infraSQS,
    infraDynamoDB
  )

lazy val infraS3 = (project in file("modules/adapter/infrastructure/s3"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= s3Dependencies)
  .dependsOn(domain)

lazy val infraSQS = (project in file("modules/adapter/infrastructure/sqs"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= sqsDependencies)
  .dependsOn(domain)

lazy val infraDynamoDB = (project in file("modules/adapter/infrastructure/dynamodb"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= dynamodbDependencies)
  .dependsOn(domain)


