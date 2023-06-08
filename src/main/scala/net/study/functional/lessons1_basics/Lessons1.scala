package net.study.functional.lessons1_basics

object Lessons1 {

  def main(args: Array[String]): Unit = {
//    val sumFunc = (Int, Int) => _ + _
    val sumFunc : (Int, Int) => Int = _ + _
    println(sumFunc(1,2))
    println(sumFunc(3,4))
  }

}
