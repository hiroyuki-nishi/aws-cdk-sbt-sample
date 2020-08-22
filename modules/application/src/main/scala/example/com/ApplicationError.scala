package example.com

sealed trait ApplicationError                           extends Throwable
case class AuthorizedError(message: Option[String])     extends ApplicationError
case class NotFoundError(message: Option[String])       extends ApplicationError
case class ConflictError(message: Option[String])       extends ApplicationError
case class InternalServerError(message: Option[String]) extends ApplicationError
