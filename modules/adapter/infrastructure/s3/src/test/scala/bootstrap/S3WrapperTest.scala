package bootstrap

import java.net.URI

import org.scalamock.scalatest.MockFactory
import org.scalatest.diagrams.Diagrams
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

class S3WrapperTest
    extends AnyWordSpec
    with Diagrams
    with BeforeAndAfterAll
    with BeforeAndAfter
    with MockFactory {

  private val s3Client = S3Client
    .builder()
    .region(Region.AP_NORTHEAST_1)
    .endpointOverride(new URI("http://localhost:4572"))
    .build()
  //      .withEndpointConfiguration(
  //        new AwsClientBuilder.EndpointConfiguration(, "ap-northeast-1"))
  //      .withPathStyleAccessEnabled(true)

  override def beforeAll(): Unit = {
    println(s"***** beforeAll *****")
  }

  override def afterAll(): Unit = {
    println(s"***** afterAll *****")
  }

  "example.com.S3Wrapper" when {
    // TODO: nishi ①テストパターンのTODOを書き出す
    // TODO: nishi ②テストを失敗させる
    // TODO: nishi ③テストを成功させる
    // TODO: nishi ④リファクタリングする
    // TODO: nishi ⑤ ②③を繰り返す

    "xxx" should {
      "yyy" in new WithFixture {
        private val result = s3Client.listBuckets()
        println(result)
        assert(true)
      }
    }

    trait WithFixture extends S3Wrapper {
      override protected val bucketName: String = "test-bucket-local"
      override protected val region: Region     = Region.AP_NORTHEAST_1
    }
  }
}
