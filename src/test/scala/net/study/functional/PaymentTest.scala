package net.study.functional

import net.study.functional.lessons3.HomeTask._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, OptionValues, WordSpec}

@RunWith(classOf[JUnitRunner])
class PaymentTest extends WordSpec with OptionValues with Matchers {

  "Payment Processing" when {

    "getPaymentSum" should {
      "return None for values equal to or less than 2" in {
        PaymentCenter.getPaymentSum(2) shouldBe None
      }

      "return correct sum for values more than 2" in {
        PaymentCenter.getPaymentSum(3).value shouldBe 300
      }
    }

    "filterObject" should {
      "return false for values less than or equal to 100" in {
        filterObject(100) shouldBe false
      }

      "return true for values more than 100" in {
        filterObject(101) shouldBe true
      }
    }

    "computeTaxSum" should {
      "return 0 if sumToTax is less than or equal to 100" in {
        computeTaxSum(100) shouldBe Some(0)
      }

      "return 20% of sumToTax if it is more than 100" in {
        computeTaxSum(200) shouldBe Some(40)
      }
    }

    "correctPayment" should {
      "return the sum if it exists" in {
        correctPayment(1, Some(1500)) shouldBe Some(1500)
      }

      "return result of getPaymentSum if the sum does not exist" in {
        correctPayment(3, None) shouldBe Some(300)
      }
    }

    "correctTax" should {
      "return the tax if it exists" in {
        correctTax(Some(200), 1000) shouldBe Some(200)
      }

      "return result of computeTaxSum if the tax does not exist" in {
        correctTax(None, 500) shouldBe Some(100)
      }
    }

    "correctDesc" should {
      "return the description if it exists" in {
        correctDesc(Some("payment for Iphone 15")) shouldBe Some("payment for Iphone 15")
      }

      "return 'technical' if the description does not exist" in {
        correctDesc(None) shouldBe Some("technical")
      }
    }

    "correctPaymentInfo" should {
      "return None if sum is None" in {
        val info = PaymentInfoDto(1, Some("customerA"), None, None, Some("payment for Iphone 15"))
        correctPaymentInfo(info) shouldBe None
      }

      "return correct PaymentInfo" in {
        val info = PaymentInfoDto(3, Some("customerB"), Some(99), None, Some("payment for headphone"))
        correctPaymentInfo(info).value shouldBe PaymentInfo(3, 99, 0, "payment for headphone")
      }
    }
  }
}
