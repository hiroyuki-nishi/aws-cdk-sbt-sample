package bootstrap.sign

import bootstrap.RepositoryErrorConverter._
import bootstrap.{RepositoryError, SqsWrapper}

trait SignRepositoryOnSQS extends SqsWrapper {
  // TODO: nishi
  def send(messageBody: String): Either[RepositoryError, Unit] =
    sendMessage(messageBody).toRepositoryError()
}
