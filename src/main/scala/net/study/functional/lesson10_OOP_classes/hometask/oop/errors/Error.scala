package net.study.functional.lesson10_OOP_classes.hometask.oop.errors

// here you can declare your errors
sealed trait Error {
  def errorMessage: String
}


// mapper errors
case object LackMappedParamError extends Error {
  override val errorMessage: String = "lackMapperParam"
}

case object MapperError extends Error {
  override val errorMessage: String = "mapperError"
}

// validation Errors
case object EmptyStringError extends Error {
  override val errorMessage: String = "emptyError"
}

case object InvalidLatinSymbolError extends Error {
  override val errorMessage: String = "invalidSymbolError"
}

case object InvalidDigitsError extends Error {
  override val errorMessage: String = "invalidDigitsError"
}

case object InvalidCharacterError extends Error {
  override val errorMessage: String = "invalidCharacterError"
}

case object InvalidUniqLoginError extends Error {
  override val errorMessage: String = "InvalidUniqLoginError"
}

case object InvalidLengthError extends Error {
  override val errorMessage: String = "invalidLengthError"
}

// base trait for errors with merging effect
trait CombinedError[T <: CombinedError[T]] extends Error {

  def errors: Map[String, Error]

  def +(error: T): T

}

//
case class ValidationError(errors: Map[String, Error]) extends CombinedError[ValidationError] {
  require(errors.nonEmpty)

  private val spliterator = ", "

  def this(param: String, error: Error) = this(Map(param -> error))

  // TODO
  /**
   * implement this
   * This method has to merge this validationError with @param error
   * @return new merged combined Validation Error
   */
  override def +(error: ValidationError): ValidationError = {
    ValidationError(errors ++ error.errors)
  }

  // TODO
  /**
   * implement this
   * errorMessage using next pattern: -  errorMessage : [ field1,field2,....], errorMessage2: [field3,field4]
   * @return merged combined Validation Error
   */
  override val errorMessage: String = {
    errors.groupBy(_._2.errorMessage)
      .map { case (errMsg, fields) =>
        s"$errMsg: [${fields.keys.mkString(",")}]"
      }
      .mkString(spliterator)
  }

}