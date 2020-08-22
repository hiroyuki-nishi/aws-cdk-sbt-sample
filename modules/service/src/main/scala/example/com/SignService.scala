package example.com

// Serviceは永続化しないものをInfrastructureに送る際に表現する層
trait SignService {
  def send(messageBody: String): Either[RepositoryError, Unit]
}
