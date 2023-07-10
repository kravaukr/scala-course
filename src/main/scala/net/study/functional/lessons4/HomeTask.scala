package net.study.functional.lessons4

import java.io.Closeable
import scala.io.BufferedSource
import scala.io.Source.fromFile
import scala.util.{Failure, Success, Try}

object HomeTask extends App {

  // 1) try to read file from external system over network (use method getFile with two columns: 1) msisdn, subscriber type)
  // and don't forget to close resource after usage!!!!!!

  // 2) try to enrich get Data using main source getDataFromMainSource

  // 3) if fail to execute step 2) go to alternative source and try once more ( use getDataFromAlternativeSource)

  // 4) if success to do so, try to send to 3-d party system all list

  // 5) Implement enrichAndSend method with proper Left(Error) type or Rigiht[Int] Quantity of msisdns send to our third party system

  // Conditions:
  // use only Try Monad to resolve all problems with exception handling
  // You can use any additional custom functions / methods
  // Don't use method Try monad methods as get, getOrElse, isSuccess, isFailure !!!!!

  /// ===============help code ======================

  trait Error

  case class NetworkException(x: String) extends Exception

  case object NetworkError extends Error // if sftp server not available

  case object SourceTemporaryUnavailableError extends Error // if main source main source unavailable

  case object AllSourceTemporaryUnavailableError extends Error //if all source were unavailable

  case object ThirdPartySystemError extends Error //if 3-d party system error

  case object AnyOtherError extends Error

  case class TemporaryUnavailableException(string: String) extends Exception

  case class ThirdPartySystemException(string: String) extends Exception

  case class SubscriberInfo(msisdn: String, subscriberType: Int, isActive: Boolean)

  case class Subscriber(msisdn: String, subscriberType: Int)

  val fileSource = "src/main/resources/lesson4/externalSourceFile.txt"

  // do not change this methods !!!!
  @throws[NetworkException]
  def getFile(isRisky: Boolean, source: String) = if (isRisky) throw NetworkException("SFTP server network exception") else fromFile(source)

  @throws[TemporaryUnavailableException]
  def getActiveData(isRisky: Boolean, msisdns: Seq[String]) = if (isRisky) throw TemporaryUnavailableException("Temporary Unavailable Exception") else {
    msisdns.map(m => (m, if (m.toInt % 2 == 0) 1 else 0))
  }

  @throws[TemporaryUnavailableException]
  def getDataFromMainSource(isRisky: Boolean, msisdns: Seq[String]) = getActiveData(isRisky, msisdns)

  @throws[TemporaryUnavailableException]
  def getDataFromAlternativeSource(isRisky: Boolean, msisdns: Seq[String]) = getActiveData(isRisky, msisdns)

  def sendToProvider(isRisky: Boolean, msisdns: Seq[SubscriberInfo]): Unit =
    if (isRisky) throw ThirdPartySystemException("third party system exception") else msisdns.foreach(m => s"Sent $m")

  private def getSubsFromSource(getFileIsRisky: Boolean, fileSource: String) =
    Try(usingSource(getFile(getFileIsRisky, fileSource))(parseSubs)).toEither

  private def parseSubs(bf: BufferedSource) = bf.getLines().map(k => Subscriber(k.split(";")(0), k.split(";")(1).toInt)).toList

  private def getListMsisdn(subs: Seq[Subscriber]) = subs.map(s => s.msisdn)

  private def toSubscriberInfo(s: (String, Int), subs: Map[String, Subscriber]): SubscriberInfo =
    subs.get(s._1) match {
      case Some(sub) => SubscriberInfo(s._1, sub.subscriberType, s._2 == 1)
      case None => SubscriberInfo(s._1, 0, s._2 == 1) // set default subscriberType
    }

  private def tryGetDataFromMainSource(getDataFromMainSourceIsRisky: Boolean, subs: Seq[Subscriber]) =
    Try(getDataFromMainSource(getDataFromMainSourceIsRisky, getListMsisdn(subs)))

  private def tryGetDataFromAlternativeSource(getDataFromAlternativeSourceIsRisky: Boolean, subs: Seq[Subscriber]) =
    Try(getDataFromAlternativeSource(getDataFromAlternativeSourceIsRisky, getListMsisdn(subs)))

  private def usingSource[A, R <: Closeable](closeable: R)(body: R => A): A = try body(closeable) finally closeable.close()

  private def enrichedSubs(additionData: Seq[(String, Int)], subs: Seq[Subscriber]) = {
    val subsMap = subs.map(sub => sub.msisdn -> sub).toMap
    additionData.map(toSubscriberInfo(_, subsMap))
  }

  private def getAdditionalData(getDataFromMainSourceIsRisky: Boolean, getDataFromAlternativeSourceIsRisky: Boolean, subs: Seq[Subscriber]) =
    tryGetDataFromMainSource(getDataFromMainSourceIsRisky, subs).orElse(tryGetDataFromAlternativeSource(getDataFromAlternativeSourceIsRisky, subs)).toEither


  private def sendSubsInfoToProvider(sendToProviderIsRisky: Boolean, subsInfo: Seq[SubscriberInfo]) =
    Try(sendToProvider(sendToProviderIsRisky, subsInfo)).toEither

  // implement this one
  def enrichAndSend(getFileIsRisky: Boolean,
                    getDataFromMainSourceIsRisky: Boolean,
                    getDataFromAlternativeSourceIsRisky: Boolean,
                    sendToProviderIsRisky: Boolean,
                    fileSource: String): Either[Error, Int] = {
    val result = for {
      subs         <- getSubsFromSource(getFileIsRisky, fileSource)
      additionData <- getAdditionalData(getDataFromMainSourceIsRisky, getDataFromAlternativeSourceIsRisky, subs)
      subsInfo     =  enrichedSubs(additionData, subs)
      _            <- sendSubsInfoToProvider(sendToProviderIsRisky, subsInfo)
    } yield subsInfo.size

//    println(result)

    result.left.map {
      case _: NetworkException => NetworkError
      case _: TemporaryUnavailableException => AllSourceTemporaryUnavailableError
      case _: ThirdPartySystemException => ThirdPartySystemError
      case unpredictable: Throwable => println(unpredictable)
        AnyOtherError
    }
  }

  private val errorOrInt: Either[Error, Int] = enrichAndSend(false, false, false, false, fileSource)
  println(errorOrInt)
}
