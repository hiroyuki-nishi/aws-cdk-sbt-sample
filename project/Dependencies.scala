import sbt._

object Dependencies {
  // aws
  lazy val awsCdkVersion = "1.18.0"
  lazy val awsSdkV2Version = "2.13.70"
  lazy val awsSdkSts = "software.amazon.awssdk" % "sts" % awsSdkV2Version
  lazy val awsSqs = "software.amazon.awssdk" % "sqs" % awsSdkV2Version
  lazy val awsS3 = "software.amazon.awssdk" % "s3" % awsSdkV2Version
  lazy val awsDynamoDB = "software.amazon.awssdk" % "dynamodb" % awsSdkV2Version
  lazy val awsLambdaJavaCore = "com.amazonaws" % "aws-lambda-java-core" % "1.2.1"

  // test
  lazy val scalaTestVersion = "3.2.0"

  lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest-wordspec" % scalaTestVersion % "test",
    "org.scalamock" %% "scalamock" % "4.4.0" % Test
  )

  lazy val cdkDependencies = Seq(
    "software.amazon.awscdk" % "core" % awsCdkVersion,
    "software.amazon.awscdk" % "apigateway" % awsCdkVersion,
    "software.amazon.awscdk" % "lambda" % awsCdkVersion,
    "software.amazon.awscdk" % "s3" % awsCdkVersion,
    "software.amazon.awscdk" % "dynamodb" % awsCdkVersion,
    "software.amazon.awscdk" % "stepfunctions" % awsCdkVersion,
    "software.amazon.awscdk" % "stepfunctions-tasks" % awsCdkVersion,
    "software.amazon.awscdk" % "ecr" % awsCdkVersion,
    "software.amazon.awscdk" % "ecs-patterns" % awsCdkVersion,
    "software.amazon.awscdk" % "ec2" % awsCdkVersion,
    "software.amazon.awscdk" % "codecommit" % awsCdkVersion,
    "software.amazon.awscdk" % "codebuild" % awsCdkVersion,
    "software.amazon.awscdk" % "codepipeline" % awsCdkVersion,
    "software.amazon.awscdk" % "codepipeline-actions" % awsCdkVersion
  )

  lazy val s3Dependencies: Seq[ModuleID] = Seq(
    awsS3
  ) ++ testDependencies

  lazy val sqsDependencies: Seq[ModuleID] = Seq(
    awsSqs
  ) ++ testDependencies

  lazy val dynamodbDependencies: Seq[ModuleID] = Seq(
    awsDynamoDB
  ) ++ testDependencies

  lazy val lambdaDependencies: Seq[ModuleID] = Seq(
    awsLambdaJavaCore
  ) ++ testDependencies
}
