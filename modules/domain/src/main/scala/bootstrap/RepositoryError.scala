package bootstrap

sealed trait RepositoryError                                    extends Throwable
case class RepositoryUnAuthorizedError(message: Option[String]) extends RepositoryError
case class RepositoryNotFoundError(message: Option[String])     extends RepositoryError
case class RepositoryTransactionError(message: Option[String])  extends RepositoryError
case class RepositorySystemError(message: Option[String])       extends RepositoryError
