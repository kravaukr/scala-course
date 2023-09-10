package net.study.functional.lesson10_OOP_classes.hometask.oop.mappers

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{Error, MapperError}
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest

import scala.util.{Failure, Success, Try}

private[oop] trait Mapper[R, DTO] {
  def map(request: R)(implicit defaultMapper: R => DTO): Either[Error, DTO]
}


trait SignUpMapper extends Mapper[SignUpRequest, SignUpDto] {
  override def map(request: SignUpRequest)(implicit defaultMapper: SignUpRequest => SignUpDto): Either[Error, SignUpDto] =
    Try {
      defaultMapper(request)
    } match {
      case Success(dto) => Right(dto)
      case Failure(_) => Left(MapperError)
    }

}