package net.study.functional.lessons3

object Lessons3 extends App {

  println("Hi lessons3")

  case class Person(name: String, age: Int)

  val pet = Person("Peter",23)
  val les = Person("Les",25)
  pet == les

  Option(2)

}
