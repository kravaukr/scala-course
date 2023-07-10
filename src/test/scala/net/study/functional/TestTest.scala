package net.study.functional

import org.junit.runner.RunWith
import org.scalatest.{Matchers, OptionValues, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestTest extends WordSpec with OptionValues with Matchers {

  import net.study.functional.lessons6.Lesson6._

  // Unit of testing
  "My Test Lessons6" when {

    //Method of testing
    "sum" should {
      "calculate right positive result" in {
        sum(1, 2) shouldEqual 3
      }

      "calculate negative result" in {
        sum(-1, -2) shouldEqual -3
      }
    }

    //Method of testing
    "devide" should {
      "calculate right division result" in {
        devide(9, 3) shouldEqual 3
      }

      "fail with ArithmeticException with interception" in {

        val catchResult: ArithmeticException = intercept[ArithmeticException] {
          devide(3, 0) shouldEqual 0
        }
        catchResult.getMessage shouldEqual "/ by zero"
      }

      "fail with ArithmeticException with assertion" in {
        assertThrows[ArithmeticException] {
          devide(3, 0) shouldEqual 0
        }
      }
    }
  }
}
