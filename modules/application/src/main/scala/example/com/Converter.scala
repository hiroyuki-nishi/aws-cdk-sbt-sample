package example.com

object ApplicationErrorConverter {
  implicit class ToApplicationError[E](val e: Either[RepositoryError, E]) extends AnyVal {
    def toApplicationError(message: Option[String] = None): Either[ApplicationError, E] =
      e.fold(
        {
          case e: RepositoryUnAuthorizedError => Left(AuthorizedError(message))
          case e: RepositoryNotFoundError     => Left(NotFoundError(message))
          case e: RepositoryTransactionError  => Left(ConflictError(message))
          case e: RepositorySystemError => {
            println(e.getMessage)
            e.printStackTrace()
            Left(InternalServerError(message))
          }
        },
        Right(_)
      )
  }

}
