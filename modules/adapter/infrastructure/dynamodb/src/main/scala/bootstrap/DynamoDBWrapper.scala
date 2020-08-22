package bootstrap

import java.util

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, QueryRequest}

import scala.util.Try

trait DynamoDBWrapper {
  protected val region: Region
  protected val queueName: String
  protected val dynamoDBClient = DynamoDbClient
    .builder()
    .region(region)
    .build()

  // TODO: nishi 要検証
  private def queryAllInternal[E](request: QueryRequest,
                                  stream: util.List[util.Map[String, AttributeValue]] = util.Collections.emptyList())(
                                   handler: scala.Predef.Map[String, AttributeValue] => E): Try[util.List[util.Map[String, AttributeValue]]] = Try {
    val result = dynamoDBClient.query(request)
    stream.addAll(result.items())
    val last = result.lastEvaluatedKey()
    if (last.isEmpty) {
      stream
    } else {
      queryAllInternal(request.toBuilder.exclusiveStartKey(last).build(), stream)(handler).getOrElse(util.Collections.emptyList())
    }
  }

  protected def queryAll[E](request: QueryRequest)(
    handler: scala.Predef.Map[String, AttributeValue] => E): Try[util.List[util.Map[String, AttributeValue]]] =
    queryAllInternal(request)(handler)
}
