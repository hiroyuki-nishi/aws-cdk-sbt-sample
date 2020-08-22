package sample

import java.net.URI

import bootstrap.SignService
import bootstrap.sign.{SignApplication, SignRepositoryOnSQS}
import org.scalamock.scalatest.MockFactory
import org.scalatest.diagrams.Diagrams
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

class AppTest
    extends AnyWordSpec
    with Diagrams
    with BeforeAndAfterAll
    with BeforeAndAfter
    with MockFactory {

  private val _sqsClient = SqsClient
    .builder()
    .region(Region.US_EAST_1)
    .endpointOverride(new URI("http://localhost:4576"))
    .build()

  override def beforeAll(): Unit = {
    println(s"***** beforeAll *****")
  }

  override def afterAll(): Unit = {
    println(s"***** afterAll *****")
  }

  "SqsWrapper" when {
    // TODO: nishi ①テストパターンのTODOを書き出す
    // TODO: nishi ②テストを失敗させる
    // TODO: nishi ③テストを成功させる
    // TODO: nishi ④リファクタリングする
    // TODO: nishi ⑤ ②③を繰り返す

    "App" should {
      "xxx" in new WithFixture {
        signApplication.run
      }

      trait WithFixture extends App {
        val signApplication = new SignApplication {
          override protected val signService: SignService = new SignService
            with SignRepositoryOnSQS {
            override protected val sqsClient: SqsClient = SqsClient
              .builder()
              .region(Region.US_EAST_1)
              .endpointOverride(new URI("http://localhost:4576"))
              .build()
            override protected val queueName: String = "test-sign-local"
          }
        }
      }
    }
  }
}
