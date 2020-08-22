import software.amazon.awscdk.core.{Construct, Stack, StackProps}
import software.amazon.awscdk.services.sqs.{Queue, QueueProps}
import software.amazon.awscdk.core.Duration

class SqsStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {
  private val env = this.getNode.tryGetContext("env").asInstanceOf[String]
  private val testSqs = new Queue(
    this,
    s"test-sign-sqs-$env",
    QueueProps
      .builder()
      .queueName(s"test-sign-$env")
      .visibilityTimeout(Duration.seconds(180))
      .build()
  )
}
