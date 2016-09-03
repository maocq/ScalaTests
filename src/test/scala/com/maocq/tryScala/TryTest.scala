package com.maocq.tryScala

import org.scalatest.FunSuite

import scala.util.{Failure, Success, Try}

class TryTest extends FunSuite {

  /**
    * Verificar Try
    */
  test("Try") {
    val result = Try("abc".toInt)
    assert(result.isFailure)

    val result2 = Try("2".toInt)
    assert(result2.isSuccess)
  }


  /**
    * Success, Failure
    */
  test("Success Failure") {
    def parseIntException(value: String): Int = value.toInt
    def parseInt(value: String): Try[Int] = Try(value.toInt)

    parseInt("o") match {
      case Success(num) => println(num)
      case Failure(f) => assert(f.isInstanceOf[NumberFormatException])
    }
  }


  /**
    * Map
    */
  test("Map") {
    def parseInt(value: String): Try[Int] = Try(value.toInt)

    val result = parseInt("2L").map(v => v * v)
    result match {
      case Success(num) => println(num)
      case Failure(f) => assert(f.isInstanceOf[NumberFormatException])
    }
  }


  /**
    * Option
    */
  test("Option") {
    def parseInt(value: String): Try[Int] = {
      Try(value.toInt)
    }

    val none = parseInt("2L").toOption
    assert(none == None)

    val some = parseInt("2").toOption
    assert(some == Some(2))
  }


  /**
    * Usar un valor por defecto
    */
  test("Default value") {
    def parseInt(value: String) = Try(value.toInt)
    val result = parseInt("2L").getOrElse(0)
    assert(result == 0)
  }


  /**
    * For comprehension
    */
  test("For comprehension") {
    val result = for (
      v <- Try("1".toInt);
      k <- Try("2".toInt);
      z <- Try("3".toInt)
    ) yield (v + k + z)

    result match {
      case Success(r) => assert(r == 6)
      case Failure(e) => println(e.getMessage)
    }

  }


  /**
    * For comprehension error
    */
  test("For comprehension error") {
    val result = for (
      v <- Try("1".toInt);
      k <- Try("2L".toInt);
      z <- Try("3".toInt)
    ) yield (v + k + z)

    assert(result.isFailure)
  }


  /**
    * My Exception
    */
  test("MyException") {

    class MyException(error: String) extends Exception(error: String)

    def myFunction(number: Int): Try[Int] = {
      Try({
        if (number % 2 != 0) {
          throw new MyException("Error!")
        } else
          number
      })
    }

    myFunction(1) match {
      case Success(num) => println(num)
      case Failure(f) => assert(f.isInstanceOf[MyException])
    }

    myFunction(2) match {
      case Success(num) => assert(num == 2)
      case Failure(f) => println(f.toString)
    }
  }

}
