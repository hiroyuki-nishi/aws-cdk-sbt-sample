import java.util

import software.amazon.awscdk.core.{Construct, Duration, Stack, StackProps}
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.stepfunctions.tasks.InvokeFunction
import software.amazon.awscdk.services.stepfunctions.{Chain, RetryProps, StateMachine, Task}

import scala.collection.JavaConverters._

class StepFunctionsStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {
  private val env = this.getNode.tryGetContext("env").asInstanceOf[String]
  private val context =
    this.getNode.tryGetContext(env).asInstanceOf[util.LinkedHashMap[String, String]]
  private val account = context.get("account_id").asInstanceOf[String]
  private val region  = context.get("region").asInstanceOf[String]
  private val helloLambda = Function.fromFunctionArn(
    this,
    s"sign-hello-world-lambda-$env",
    s"arn:aws:lambda:$region:$account:function:HelloLambda-local"
  )

  private val helloWorldTask = Task.Builder
    .create(this, "HelloWorldTask")
    .inputPath("$")
    .outputPath("$")
    .task(InvokeFunction.Builder.create(helloLambda).build())
    .build()
  helloWorldTask.addRetry(
    RetryProps
      .builder()
      .interval(Duration.seconds(3))
      .backoffRate(1.5)
      .maxAttempts(5)
      .errors(Seq("States.ALL").asJava)
      .build()
  )

  private val machine = StateMachine.Builder
    .create(this, s"sign-$env")
    .stateMachineName(s"sign-$env")
    .definition(Chain.start(helloWorldTask))
    .timeout(Duration.seconds(360))
    .build()
}
