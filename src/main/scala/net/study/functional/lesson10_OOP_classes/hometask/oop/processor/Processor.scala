package net.study.functional.lesson10_OOP_classes.hometask.oop.processor

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{Error, LackMappedParamError, MapperError}
import net.study.functional.lesson10_OOP_classes.hometask.oop.response.SignUpResponse

import scala.util.{Failure, Success, Try}
trait Processor[DTO, RESP] {

  def process(in: DTO): Either[Error, RESP]

}

trait SignUpProcessor extends Processor[SignUpDto,SignUpResponse]{
  override def process(in: SignUpDto): Either[Error, SignUpResponse] = Try {
    SignUpResponse(OK, in)
  } match {
    case Success(response) => Right(response)
    case Failure(_) => Left(LackMappedParamError)
  }
}


//  Response status Always wil be ok for just now
trait Status

case object OK extends Status
