package net.study.functional.lesson10_OOP_classes.hometask.oop

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.handler.RequestHandler
import net.study.functional.lesson10_OOP_classes.hometask.oop.mappers.{Mappers, SignUpMapper}
import net.study.functional.lesson10_OOP_classes.hometask.oop.processor.SignUpProcessor
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest
import net.study.functional.lesson10_OOP_classes.hometask.oop.response.SignUpResponse
import net.study.functional.lesson10_OOP_classes.hometask.oop.validator.SignUpValidator
import net.study.functional.lesson10_OOP_classes.hometask.oop.errors._

import scala.language.postfixOps

object HomeTask extends App with Mappers{
  private val handler = new RequestHandler[SignUpRequest, SignUpDto, SignUpResponse]
    with SignUpValidator
    with SignUpMapper
    with SignUpProcessor

  private val signUpRequest: SignUpRequest = SignUpRequest(Option("test"), Option("testSurname"), Option("testLogin"), Option("pass"), Option("067847785409"))

  private val errorOrResponse: Either[Error, SignUpResponse] = handler.handle(signUpRequest)

  println(errorOrResponse)
//  println(errorOrResponse.swap.map(_.errorMessage))


}




