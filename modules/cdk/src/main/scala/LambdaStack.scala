import software.amazon.awscdk.core.{Construct, Duration, Stack, StackProps}
import software.amazon.awscdk.services.lambda.{Code, Function, FunctionProps, Runtime}
import software.amazon.awscdk.services.stepfunctions.tasks.InvokeFunction
import software.amazon.awscdk.services.stepfunctions.{Chain, RetryProps, StateMachine, Task}
import collection.JavaConverters._

class LambdaStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {
  private val env              = this.getNode.tryGetContext("env").asInstanceOf[String]
  private val presentationPath = "modules/adapter/presentation"
  private val targetPath       = "target/scala-2.13"

  private val helloLambda = new Function(
    this,
    "helloworld",
    FunctionProps
      .builder()
      .functionName(s"HelloLambda-$env")
      .code(
        Code.fromAsset(s"${presentationPath}/helloworld/${targetPath}/helloworld.jar")
      )
      .runtime(Runtime.JAVA_8)
      .handler("sample.App::handleRequest")
      .timeout(Duration.seconds(180))
      .memorySize(1024)
      .environment(Map("env" -> s"$env", "region" -> "ap-northeast-1").asJava)
      .build()
  )
}
