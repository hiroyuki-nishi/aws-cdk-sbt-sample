package example.com

import java.util

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import example.com.sign.SignApplication

class App extends RequestHandler[Object, Unit] {
  override def handleRequest(input: Object, context: Context): Unit = {
    println("hello world")
  }
}
