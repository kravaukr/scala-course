package net.study.functional.lesson10_OOP_classes.hometask.oop.mappers

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.exception.MapperException
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest

// here you can assign your implicit mapper function  implement this trait with your logic
trait Mappers {

  // implement this
  implicit val signUpRequestMapper: SignUpRequest => SignUpDto = {
    case SignUpRequest(Some(name), Some(surname), Some(login), Some(pass), Some(msisdn)) =>
      SignUpDto(name, surname, login, pass, msisdn)
    case _ => throw MapperException("MapperException")
  }


}