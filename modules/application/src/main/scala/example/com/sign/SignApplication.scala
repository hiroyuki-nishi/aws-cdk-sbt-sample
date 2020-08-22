package example.com.sign

import example.com.{ApplicationError, SignService}

import scala.util.Try
import example.com.ApplicationErrorConverter._

trait SignApplication {
  protected val signService: SignService
  // TODO: nishi
  def run: Either[ApplicationError, Unit] = signService.send("AAAAAA").toApplicationError()
}
