package sample

import java.util

import bootstrap.SignService
import bootstrap.sign.{SignApplication, SignRepositoryOnSQS}
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

class App extends RequestHandler[util.LinkedHashMap[String, Object], Unit] {
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
      override protected val queueName: String    = "test-sign-local"
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
