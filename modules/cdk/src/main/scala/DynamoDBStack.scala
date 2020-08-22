import software.amazon.awscdk.core.{Construct, RemovalPolicy, Stack, StackProps}
import software.amazon.awscdk.services.dynamodb.{
  Attribute,
  AttributeType,
  BillingMode,
  Table,
  TableProps
}

class DynamoDBStack(val scope: Construct, val id: String, val props: StackProps)
    extends Stack(scope, id, props) {
  private val env = this.getNode.tryGetContext("env").asInstanceOf[String]
  private val testDynamoDB = new Table(
    this,
    s"test-sign-dynamodb-$env",
    TableProps
      .builder()
      .tableName(s"client-status-$env")
      .partitionKey(Attribute.builder().name("company_id").`type`(AttributeType.STRING).build())
      .sortKey(Attribute.builder().name("clietn_id").`type`(AttributeType.STRING).build())
      .billingMode(BillingMode.PAY_PER_REQUEST)
      .removalPolicy(RemovalPolicy.DESTROY)
      .build()
  )
}
