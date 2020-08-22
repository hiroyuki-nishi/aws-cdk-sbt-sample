package sample

import java.util

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import example.com.SignService
import example.com.sign.{SignApplication, SignRepositoryOnSQS}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

class App extends RequestHandler[util.LinkedHashMap[String, Object], Unit] {
  // TODO: nishi Regionの環境変数を追加
  private val env: String = sys.env.getOrElse("env", "local")
  private val region: Region =
    Region.of(sys.env.getOrElse("region", Region.AP_NORTHEAST_1.toString))

  private val client = SqsClient
    .builder()
    .region(region)
    .build()

  private val signApplication = new SignApplication {
    override protected val signService: SignService = new SignService with SignRepositoryOnSQS {
      override protected val sqsClient: SqsClient = client
// TODO: nishi
//        if (env == "local")
//          sqsClientBuilder.endpointOverride(new URI("http://localhost:4576")).build()
      override protected val queueName: String = "test-sign-local"
    }
  }

  override def handleRequest(input: util.LinkedHashMap[String, Object], context: Context): Unit = {
    (for {
      _ <- signApplication.run
    } yield ()) match {
      case Right(s) =>
        println("Success")
        println(s)
      case Left(e) =>
        println("Failure")
        throw e
    }
  }
}
