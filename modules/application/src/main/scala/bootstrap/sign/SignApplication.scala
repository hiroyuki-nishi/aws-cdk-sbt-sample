package bootstrap.sign

import bootstrap.{ApplicationError, SignService}

import scala.util.Try
import bootstrap.ApplicationErrorConverter._

trait SignApplication {
  protected val signService: SignService
  // TODO: nishi
  def run: Either[ApplicationError, Unit] = signService.send("AAAAAA").toApplicationError()
}
