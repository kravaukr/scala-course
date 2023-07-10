package net.study.functional

import org.scalatest.{Matchers, OptionValues, WordSpec}
import net.study.functional.lessons4.HomeTask._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Stis3Test extends WordSpec with OptionValues with Matchers {

  val fileSource = "src/test/resources/lesson4/externalSourceFile.txt"

  "enrichAndSend" when {
    "trying to get the file on the wrongFileSource" should {
      "return Left(NetworkError)" in {
        val response = enrichAndSend(true, false, false, false, "wrongFileSource")
        response shouldBe Left(NetworkError)
      }
    }

    "trying to get data when the sources are not available" should {
      "return Left(AllSourceTemporaryUnavailableError)" in {
        val response = enrichAndSend(false, true, true, false, fileSource)
        response shouldBe Left(AllSourceTemporaryUnavailableError)
      }
    }

    "trying to send subs for the provider, but it is not available" should {
      "return Left(ThirdPartySystemError)" in {
        val response = enrichAndSend(false, false, false, true, fileSource)
        response shouldBe Left(ThirdPartySystemError)
      }
    }

    "enrich the data and send it to the provider successfully" should {
      "return Right with the count of Subscribers" in {
        val response = enrichAndSend(false, false, false, false, fileSource)
        response shouldBe Right(5)
      }
    }
  }
}
