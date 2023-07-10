package net.study.functional.lessons3

import net.study.functional.lessons3.HomeTask.PaymentCenter.getPaymentSum

object HomeTask extends App {

  // if sum not submitted, precise in payment service. In case not found remove from final report.
  // if tax sum is not assigned calculate it(20%) (minimal tax free sum for calculation = 100 or reversal payments are tax free)
  // if desc is empty default value will be "technical"
  // dublicates must be removed
  // get method is not allowed, use for comprehension in main calculation method
  // output must be type of Seq[PaymentInfo] !!!!!!!!!

  final case class PaymentInfoDto(paymentId: Int, customer: Option[String], sum: Option[Long], tax: Option[Long], desc: Option[String])

  final case class PaymentInfo(paymentId: Int, sum: Long, tax: Long, desc: String)

  object PaymentCenter {
    def getPaymentSum(id: Int): Option[Long] = Option(id) filter (_ > 2) map (_ * 100)
  }

  val payments = Seq(
    PaymentInfoDto(1, Some("customerA"), Some(1500), None, Some("payment for Iphone 15")),
    PaymentInfoDto(2, Some("customerH"), None, None, Some("technical payment")),
    PaymentInfoDto(3, Some("customerB"), Some(99), None, Some("payment for headphone")),
    PaymentInfoDto(4, Some("customerC"), Some(1000), Some(200), None),
    PaymentInfoDto(5, Some("customerD"), Some(2500), None, None),
    PaymentInfoDto(6, Some("customerE"), Some(600), Some(120), Some("payment for Oculus quest 2")),
    PaymentInfoDto(7, Some("customerF"), Some(1500), None, Some("payment for Iphone 15")),
    PaymentInfoDto(8, Some("customerG"), Some(-400), None, Some("roll back transaction")),
    PaymentInfoDto(9, Some("customerH"), None, None, Some("some payment")),
    PaymentInfoDto(4, Some("customerC"), Some(1000), Some(200), None)
  )

  def filterObject = (l: Long) => l > 100

  def computeTaxSum(sumToTax: Long): Option[Long] = Some(sumToTax) filter filterObject map (_ * 20 / 100) orElse Some(0)

  def correctPayment(id: Int, p: Option[Long]): Option[Long] = p orElse getPaymentSum(id)

  def correctTax(tax: Option[Long], sum: Long): Option[Long] = tax orElse computeTaxSum(sum)

  def correctDesc(desc: Option[String]): Option[String] = desc.orElse(Some("technical"))

  def correctPaymentInfo(paymentInfoDto: PaymentInfoDto): Option[PaymentInfo] = for {
    sumCalculated <- correctPayment(paymentInfoDto.paymentId, paymentInfoDto.sum)
    taxSum <- correctTax(paymentInfoDto.tax, sumCalculated)
    desc <- correctDesc(paymentInfoDto.desc)

  } yield PaymentInfo(paymentInfoDto.paymentId, sumCalculated, taxSum, desc)

  val result: Seq[PaymentInfo] = (payments distinct) flatMap (x => correctPaymentInfo(x))

  result foreach println



















//
//  private val result1: Seq[PaymentInfo] = for {
//    dto <- payments.distinct
//    sum <- dto.sum match {
//      case Some(value) => Some(value)
//      case None => PaymentCenter.getPaymentSum(dto.paymentId)
//    }
//    tax = dto.tax match {
//      case Some(value) => value
//      case None => if (sum > 100L) (sum * 0.2).toLong else 0L
//    }
//    desc = dto.desc match {
//      case Some(value) => value
//      case None => "technical"
//    }
//  } yield PaymentInfo(dto.paymentId, sum, tax, desc)
//
//  private val result1_1: Seq[PaymentInfo] = for {
//    dto <- payments.distinct
//    sum <- dto.sum match {
//      case Some(value) => Some(value)
//      case None => PaymentCenter.getPaymentSum(dto.paymentId)
//    }
//  } yield {
//    val tax = dto.tax match {
//      case Some(value) => value
//      case None => if (sum > 100L) (sum * 0.2).toLong else 0L
//    }
//    val desc = dto.desc match {
//      case Some(value) => value
//      case None => "technical"
//    }
//    PaymentInfo(dto.paymentId, sum, tax, desc)
//  }
//
//
//
//  private val result2: Seq[PaymentInfo] = for {
//    dto <- payments.distinct
//    sum <- dto.sum.orElse(PaymentCenter.getPaymentSum(dto.paymentId))
//    tax = dto.tax.getOrElse(if (sum > 100L) (sum * 0.2).toLong else 0L)
//    desc = dto.desc.getOrElse("technical")
//  } yield PaymentInfo(dto.paymentId, sum, tax, desc)
//
//
//  private val paymentTax = (sum: Long) => if (sum > 100L) (sum * 0.2).toLong else 0L
//
//  private val result3: Seq[PaymentInfo] = for {
//    dto <- payments.distinct
//    sum <- dto.sum.orElse(getPaymentSum(dto.paymentId))
//  } yield PaymentInfo(dto.paymentId, sum, dto.tax.getOrElse(paymentTax(sum)), dto.desc.getOrElse("technical"))


//  println(result1.equals(result2))
//  println(result2.equals(result3))
//  println(result1)

}
