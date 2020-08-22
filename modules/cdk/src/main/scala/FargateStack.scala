import software.amazon.awscdk.core.{Construct, Stack, StackProps}
import software.amazon.awscdk.services.ec2.Vpc
import software.amazon.awscdk.services.ecs.patterns.{
  ApplicationLoadBalancedFargateService,
  ApplicationLoadBalancedTaskImageOptions
}
import software.amazon.awscdk.services.ecs.{Cluster, ContainerImage}

class FargateStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {

  private val env = this.getNode.tryGetContext("env").asInstanceOf[String]
  private val vpc = Vpc.Builder
    .create(this, "TestVpc")
    .maxAzs(3) // Default is all AZs in region
    .build()
  private val cluster = Cluster.Builder
    .create(this, "TestCluster")
    .vpc(vpc)
    .build()

  // Create a load-balanced Fargate service and make it public
  ApplicationLoadBalancedFargateService.Builder
    .create(this, "MyFargateService")
    .cluster(cluster) // Required
    .desiredCount(1)
    .taskImageOptions(
      ApplicationLoadBalancedTaskImageOptions
        .builder()
        .image(
          ContainerImage.fromRegistry(
            "" // 任意のImageを指定する
          )
        )
        .build()
    )
    .build()
}
