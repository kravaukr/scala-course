package net.study.functional.lesson10_OOP_classes.hometask.oop.validator

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{EmptyStringError, Error, LackMappedParamError, ValidationError}
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.{SignInRequest, SignUpRequest}
import net.study.functional.lesson10_OOP_classes.hometask.oop.validator.RequestValidator.{Login, Msisdn, Name, Surname, pass}
import net.study.functional.lesson10_OOP_classes.hometask.oop.validator.ValidatorUtil.{validateDigitsParam, validateInvalidCharacterParam, validateLatinSymbolParam, validateLengthParam, validateStringEmptyParam}

import scala.collection.immutable


// here you can implement sub-traits for validation purpose


trait RequestValidator[R] {

  def validate(request: R): Either[Error, R]

}

trait SignUpValidator extends RequestValidator[SignUpRequest] {
  override def validate(request: SignUpRequest): Either[ValidationError, SignUpRequest] = {

    val validations: immutable.Seq[Either[ValidationError, Unit]] = List(
      validateStringEmptyParam(Name, request.name),
      validateLatinSymbolParam(Name, request.name),
      validateStringEmptyParam(Surname, request.surname),
      validateLatinSymbolParam(Surname, request.surname),
      validateStringEmptyParam(Login, request.login),
      validateInvalidCharacterParam(Login, request.login),
      validateStringEmptyParam(Msisdn, request.msisdn),
      validateDigitsParam(Msisdn, request.msisdn),
      validateLengthParam(Msisdn, request.msisdn)
    )

    val maybeErrors = validations.map(_.swap).map(_.toOption).flatMap(_.toList)

    if (maybeErrors.nonEmpty) Left(maybeErrors.reduceLeft(_ + _)) else Right(request)
  }
}

object RequestValidator {

  val Name = "name"
  val Surname = "surname"
  val Login = "login"
  val pass = "pass"
  val Msisdn = "msisdn"
}
