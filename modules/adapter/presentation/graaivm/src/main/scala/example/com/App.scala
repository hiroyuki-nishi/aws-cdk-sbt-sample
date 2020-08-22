package example.com

import java.util

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import example.com.sign.SignApplication

object Main extends App with RequestHandler[Object, Unit] {
  override def handleRequest(input: Object, context: Context): Unit = {
    println("hello world")
  }
}
