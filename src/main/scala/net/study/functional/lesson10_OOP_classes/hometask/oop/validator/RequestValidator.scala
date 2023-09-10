package net.study.functional.lesson10_OOP_classes.hometask.oop.validator

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{EmptyStringError, Error, LackMappedParamError, ValidationError}
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.{SignInRequest, SignUpRequest}
import net.study.functional.lesson10_OOP_classes.hometask.oop.validator.RequestValidator.{Login, Msisdn, Name, Surname, pass}
import net.study.functional.lesson10_OOP_classes.hometask.oop.validator.ValidatorUtil.{validateDigitsParam, validateInvalidCharacterParam, validateLatinSymbolParam, validateLengthParam, validateStringEmptyParam, validateUniqLoginParam}

import scala.collection.immutable


// here you can implement sub-traits for validation purpose


trait RequestValidator[R] {

  def validate(request: R): Either[Error, R]

}

trait SignUpValidator extends NameValidator with SurnameValidator with LoginValidator with MsisdnValidator {
  override def validate(request: SignUpRequest): Either[ValidationError, SignUpRequest] = {
    val validations = validateName(request) ++ validateSurname(request) ++ validateLogin(request) ++ validateMsisdn(request)
    val maybeErrors = validations.collect { case Left(error) => error }
    if (maybeErrors.nonEmpty) Left(maybeErrors.reduceLeft(_ + _)) else Right(request)
  }
}

trait NameValidator extends RequestValidator[SignUpRequest] {
  def validateName(request: SignUpRequest): List[Either[ValidationError, Unit]] = {
    List(
      validateStringEmptyParam(Name, request.name),
      validateLatinSymbolParam(Name, request.name)
    )
  }
}

trait SurnameValidator extends RequestValidator[SignUpRequest] {
  def validateSurname(request: SignUpRequest): List[Either[ValidationError, Unit]] = {
    List(
      validateStringEmptyParam(Surname, request.surname),
      validateLatinSymbolParam(Surname, request.surname)
    )
  }
}

trait LoginValidator extends RequestValidator[SignUpRequest] {
  def validateLogin(request: SignUpRequest): List[Either[ValidationError, Unit]] = {
    List(
      validateStringEmptyParam(Login, request.login),
      validateInvalidCharacterParam(Login, request.login),
      validateUniqLoginParam(Login, request.login)
    )
  }
}

trait MsisdnValidator extends RequestValidator[SignUpRequest] {
  def validateMsisdn(request: SignUpRequest): List[Either[ValidationError, Unit]] = {
    List(
      validateStringEmptyParam(Msisdn, request.msisdn),
      validateDigitsParam(Msisdn, request.msisdn),
      validateLengthParam(Msisdn, request.msisdn)
    )
  }
}

object RequestValidator {

  val Name = "name"
  val Surname = "surname"
  val Login = "login"
  val pass = "pass"
  val Msisdn = "msisdn"
}
