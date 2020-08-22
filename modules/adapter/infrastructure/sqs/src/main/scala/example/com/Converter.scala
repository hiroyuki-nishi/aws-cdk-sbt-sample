package example.com

import scala.util.Try

object RepositoryErrorConverter {
  implicit class ToRepositoryError[E](val e: Try[E]) extends AnyVal {
    def toRepositoryError(message: Option[String] = None): Either[RepositoryError, E] =
      e.fold(
        {
          case e => {
            println(e.getMessage)
            e.printStackTrace()
            Left(RepositorySystemError(message))
          }
        },
        Right(_)
      )
  }
}
