import software.amazon.awscdk.core.{Construct, RemovalPolicy, Stack, StackProps}
import software.amazon.awscdk.services.s3.{Bucket, BucketProps}

class S3Stack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {
  private val env = this.getNode.tryGetContext("env").asInstanceOf[String]
  private val testS3 = new Bucket(
    this,
    s"test-sign-$env",
    BucketProps
      .builder()
      .bucketName(s"test-sign-$env")
      .removalPolicy(RemovalPolicy.DESTROY)
      .build()
  )
}
