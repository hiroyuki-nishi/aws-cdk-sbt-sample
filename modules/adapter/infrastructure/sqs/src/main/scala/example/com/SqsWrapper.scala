package example.com

import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model._

import scala.util.Try

trait SqsWrapper {
  protected val queueName: String
  protected val sqsClient: SqsClient

  private def _getQueueUrl(queueName: String): Try[GetQueueUrlResponse] =
    Try {
      sqsClient.getQueueUrl(
        GetQueueUrlRequest
          .builder()
          .queueName(queueName)
          .build()
      )
    }

  private def _receiveMessage(queueUrl: String, count: Int): Try[ReceiveMessageResponse] =
    Try {
      sqsClient.receiveMessage(
        ReceiveMessageRequest
          .builder()
          .maxNumberOfMessages(count)
          .queueUrl(queueUrl)
          .build()
      )
    }

  private def _sendMessage(queueUrl: String, messageBody: String): Try[SendMessageResponse] =
    Try {
      sqsClient.sendMessage(
        SendMessageRequest
          .builder()
          .queueUrl(queueUrl)
          .messageBody(messageBody)
          .build()
      )
    }

  protected def receiveMessage(count: Int): Try[ReceiveMessageResponse] =
    (for {
      url      <- _getQueueUrl(queueName)
      response <- _receiveMessage(url.queueUrl, count)
    } yield response)

  protected def sendMessage(messageBody: String): Try[Unit] =
    (for {
      url      <- _getQueueUrl(queueName)
      response <- _sendMessage(url.queueUrl, messageBody)
    } yield ())
}
