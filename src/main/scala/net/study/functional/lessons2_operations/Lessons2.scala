package net.study.functional.lessons2_operations

object Lessons2 extends App {

  val seqInt = Seq(1, 2, 3, 4, 5, 6, 7, 8, 9)
  //копютація
  //  seqInt.foreach(println)

  for (x <- seqInt) {
    //    println(x)
  }

  val result = seqInt.map(x => x * x)

  //монада   або контейнер
  for {
    n <- seqInt
  } yield n * n

  // нода => flatmap

  val flattenRelust = seqInt.flatMap { n =>
    seqInt.map(m => s"$n * $m = ${n * m}")
  }

  //  flattenRelust.foreach(println)

  val forComResult = for {
    n <- seqInt
    m <- seqInt

  } yield {
    s"$n * $m = ${n * m}"
  }

  forComResult.foreach(println)


}
