package example.com.sign

import example.com.RepositoryErrorConverter._
import example.com.{RepositoryError, SqsWrapper}

trait SignRepositoryOnSQS extends SqsWrapper {
  // TODO: nishi
  def send(messageBody: String): Either[RepositoryError, Unit] =
    sendMessage(messageBody).toRepositoryError()
}
