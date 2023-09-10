package net.study.functional.lesson10_OOP_classes.hometask.oop.validator

import net.study.functional.lesson10_OOP_classes.hometask.oop.services._
import net.study.functional.lesson10_OOP_classes.hometask.oop.errors._

// Here you can declare all supplementary method for validation with style below(or use your own signature and return type
// if you want)
object ValidatorUtil extends LoginService {

  def validateStringEmptyParam(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    maybeStringEmpty match {
      case Some(value) if value.nonEmpty => Right(())
      case Some(_) => Left(new ValidationError(paramName, EmptyStringError))
      case None => Left(new ValidationError(paramName, EmptyStringError))
    }

  def validateLatinSymbolParam(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    maybeStringEmpty match {
      case Some(value) if value.matches("^[a-zA-Z]*$") => Right(())
      case Some(_) => Left(new ValidationError(paramName, InvalidLatinSymbolError))
    }

  def validateDigitsParam(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    maybeStringEmpty match {
      case Some(value) if value.matches("\\d*") => Right(())
      case Some(_) => Left(new ValidationError(paramName, InvalidDigitsError))
    }

  def validateInvalidCharacterParam(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    maybeStringEmpty match {
      case Some(value) if value.matches("[a-zA-Z0-9]*") => Right(())
      case Some(_) => Left(new ValidationError(paramName, InvalidCharacterError))
    }

  def validateUniqLoginParam(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    maybeStringEmpty match {
      case Some(value) if checkUniqueness(value) => Right(())
      case Some(_) => Left(new ValidationError(paramName, InvalidUniqLoginError))
    }

  def validateLengthParam(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    maybeStringEmpty match {
      case Some(value) if value.length == 9 || value.length == 12 => Right(())
      case Some(_) => Left(new ValidationError(paramName, InvalidLengthError))
    }

}
