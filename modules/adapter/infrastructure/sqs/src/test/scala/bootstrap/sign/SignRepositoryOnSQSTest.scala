package bootstrap.sign

import java.net.URI

import org.scalamock.scalatest.MockFactory
import org.scalatest.diagrams.Diagrams
import org.scalatest.wordspec.AnyWordSpec
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

class SignRepositoryOnSQSTest extends AnyWordSpec with Diagrams with MockFactory {
  private val _sqsClient = SqsClient
    .builder()
    .region(Region.US_EAST_1)
    .endpointOverride(new URI("http://localhost:4576"))
    .build()

  "SignRepositoryOnSQS" when {
    // TODO: nishi ①テストパターンのTODOを書き出す
    // TODO: nishi ②テストを失敗させる
    // TODO: nishi ③テストを成功させる
    // TODO: nishi ④リファクタリングする
    // TODO: nishi ⑤ ②③を繰り返す

    "run" should {
      "xxxx" in new WithFixture {
        private val actual = send("test")
        println(actual)
        assert(true)
      }
    }

    trait WithFixture extends SignRepositoryOnSQS {
      override protected val sqsClient: SqsClient = _sqsClient
      protected val queueName: String             = "test-sign-local"
    }
  }
}
