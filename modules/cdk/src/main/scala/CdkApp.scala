import software.amazon.awscdk.core.{App, Environment, StackProps}

sealed abstract class CdkStackName(val name: String)
case object DynamoDB      extends CdkStackName("dynamodb")
case object Sqs           extends CdkStackName("sqs")
case object S3            extends CdkStackName("s3")
case object ECR           extends CdkStackName("ecr")
case object Fargate       extends CdkStackName("fargate")
case object Lambda        extends CdkStackName("lambda")
case object StepFunctions extends CdkStackName("stepfunctions")

case class ContextName(value: String) extends AnyVal

object Converter {
  implicit def converterService: CdkStackName => String = (c: CdkStackName) => c.name
  implicit def converterContext: ContextName => String  = (s: ContextName) => s.value
}

object CdkApp {
  import Converter._

  private def createStackProps(
      c: ContextName,
      s: CdkStackName,
      environment: Environment
  ): StackProps = {
    lazy val contextName: String = c
    lazy val serviceName: String = s
    StackProps
      .builder()
      .stackName(s"$contextName-$serviceName")
      .description(s"$contextName for $serviceName")
      .env(environment)
      .build()
  }

  def main(args: Array[String]): Unit = {
    lazy val region      = "ap-northeast-1"
    lazy val environment = Environment.builder().region(region).build()
    lazy val context     = "sign"

    val app = new App
    new SqsStack(app, s"${Sqs.name}", createStackProps(ContextName(context), Sqs, environment))
    new S3Stack(app, s"${S3.name}", createStackProps(ContextName(context), S3, environment))
    new DynamoDBStack(
      app,
      s"${DynamoDB.name}",
      createStackProps(ContextName(context), DynamoDB, environment)
    )
    new EcrStacks(app, s"${ECR.name}", createStackProps(ContextName(context), ECR, environment))
    new LambdaStack(
      app,
      s"${Lambda.name}",
      createStackProps(ContextName(context), Lambda, environment)
    )
    new StepFunctionsStack(
      app,
      s"${StepFunctions.name}",
      createStackProps(ContextName(context), StepFunctions, environment)
    )
    new FargateStack(
      app,
      s"${Fargate.name}",
      createStackProps(ContextName(context), Fargate, environment)
    )
    app.synth
  }
}
