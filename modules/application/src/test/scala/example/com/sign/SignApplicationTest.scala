package example.com.sign

import example.com.SignService
import org.scalamock.scalatest.MockFactory
import org.scalatest.diagrams.Diagrams
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}

import scala.util.Success

class SignApplicationTest extends AnyWordSpec with Diagrams with MockFactory {

  "SignApplication" when {
    // TODO: nishi ①テストパターンのTODOを書き出す
    // TODO: nishi ②テストを失敗させる
    // TODO: nishi ③テストを成功させる
    // TODO: nishi ④リファクタリングする
    // TODO: nishi ⑤ ②③を繰り返す

    "run" should {
      "xxxx" in new WithFixture {
        (mockSignService.send _)
          .expects(*)
          .returns(Right())
          .once()
        private val actual = signService.send("test message")
        assert(actual.isRight)
      }
    }

    trait WithFixture extends SignApplication {
      protected val mockSignService                   = mock[SignService]
      override protected val signService: SignService = mockSignService
    }
  }
}
