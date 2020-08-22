import software.amazon.awscdk.core.{Construct, RemovalPolicy, Stack, StackProps}
import software.amazon.awscdk.services.ecr.{LifecycleRule, Repository, RepositoryProps}

class EcrStacks(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {

  private val env = this.getNode.tryGetContext("env").asInstanceOf[String]
  private val repository = new Repository(
    this,
    s"test-ecr-$env",
    RepositoryProps
      .builder()
      .repositoryName(s"test-sign-$env")
      .removalPolicy(RemovalPolicy.DESTROY)
      .build()
  )
  repository.addLifecycleRule(
    new LifecycleRule.Builder().maxImageCount(999).build()
  )
}
