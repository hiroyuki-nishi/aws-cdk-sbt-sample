package example.com

import java.net.URI

import org.scalamock.scalatest.MockFactory
import org.scalatest.diagrams.Diagrams
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

class DynamoDBWrapperTest
  extends AnyWordSpec
    with Diagrams
    with BeforeAndAfterAll
    with BeforeAndAfter
    with MockFactory {

  private val _dynamoDBClient = DynamoDbClient
    .builder()
    .region(Region.US_EAST_1)
    .endpointOverride(new URI("http://localhost:4570"))
    .build()

  override def beforeAll(): Unit = {
    println(s"***** beforeAll *****")
  }

  override def afterAll(): Unit = {
    println(s"***** afterAll *****")
  }

  "example.com.DynamoDBWrapper" when {
    // TODO: nishi ①テストパターンのTODOを書き出す
    // TODO: nishi ②テストを失敗させる
    // TODO: nishi ③テストを成功させる
    // TODO: nishi ④リファクタリングする
    // TODO: nishi ⑤ ②③を繰り返す

    "xxx" should {
      "yyy" in new WithFixture {
        assert(true)
      }

      trait WithFixture extends DynamoDBWrapper {
        override val dynamoDBClient: DynamoDbClient = _dynamoDBClient
        override protected val queueName: String = "client-status-local"
        override protected val region: Region = Region.US_EAST_1
      }
    }
  }
}
