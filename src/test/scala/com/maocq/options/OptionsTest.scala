package com.maocq.options

import org.scalatest.FunSuite

class OptionsTest extends FunSuite {

  /**
    * Comprobar si la opción tiene un valor
    */
  test("Option") {
    val someValue: Option[String] = Some("Algo")
    val emptyValue: Option[String] = None

    assert(someValue.isDefined)
    assert(emptyValue.isEmpty)
  }


  /**
    * Proporcionar un valor por defecto
    */
  test("getOrElse") {
    val someValue: Option[String] = Some("Algo")
    val emptyValue: Option[String] = None

    assert((someValue getOrElse "Sin valor") == "Algo")
    assert((emptyValue getOrElse "Sin valor") == "Sin valor")
  }


  /**
    * Coincidencia de patrones
    */
  test("match") {
    val number: Option[Int] = Some(5)
    val value = number match {
      case Some(v) ⇒ v
      case None ⇒ 0
    }
    assert(value == 5)

    val noNumber: Option[Int] = None
    val value2 = noNumber match {
      case Some(v) ⇒ v
      case None ⇒ 0
    }
    assert(value2 == 0)
  }


  /**
    * Mapear el valor interno a un tipo diferente sin perder la opción
    */
  test("map") {
    val number: Option[Int] = Some(3)
    val noNumber: Option[Int] = None
    val result1 = number.map(_ * 1.5)
    val result2 = noNumber.map(_ * 1.5)

    assert(result1 == Some(4.5))
    assert(result2 == None)
  }


  /**
    * Foreach (Si es None no se ejecuta)
    */
  test("foreach") {
    val number: Option[Int] = Some(3)
    val noNumber: Option[Int] = None

    number.foreach(
      i => assert(i == 3)
    )
  }


  /**
    * Valor por defecto en caso de None
    */
  test("fold") {
    val number: Option[Int] = Some(3)
    val noNumber: Option[Int] = None
    val result1 = number.fold(0)(_ * 2)
    val result2 = noNumber.fold(0)(_ * 2)

    assert(result1 == 6)
    assert(result2 == 0)
  }


  /**
    * Map get retorna un option
    */
  test("Map get") {
    val myMap = Map("name" -> "Mauricio", "email" -> "carmonaesc@gmail.com")
    val name = myMap get "name"

    val none = myMap get "xyx"

    assert(name == Some("Mauricio"))
    assert(none == None)
  }


  /**
    * Función que retorna un option
    */
  test("Return option") {
    def number(): Option[Int] = {
      Some(5)
    }
    val myNumber = number()
    assert(myNumber == Some(5))
  }


  /**
    * Options equivalentes
    */
  test("Equivalentes") {
    val opt = Option(1)
    //val opt: Option[Int] = Some(1)
    val optNone: Option[Int] = None


    val resultA = opt match {
      case Some(x) => x + 1
      case None => 0
    }

    val resultB = opt map (_ + 1) getOrElse 0

    //                   None  Some
    val resultC = opt.fold(0)(_ + 1)

    assert(resultA == resultB && resultB == resultC)
  }


  /**
    * Llamar una funcion (coincidencia de patrones)
    */
  test("Coincidencia de patrones") {
    def check(value: String) = assert(value == "Ok")
    def printNone = println("None =(")

    val opt: Option[String] = Some("Ok")
    val optNone: Option[String] = None


    opt match {
      case Some(x) => check(x)
      case None => printNone
    }

    opt map check getOrElse printNone

    //          None      Ok
    opt.fold(printNone)(check(_))
  }


  /**
    * FlatMap
    * param  -> Funcion que recibe un A y retorna un option B
    * return -> Retorna un option B
    *
    * def flatMap[B](f: (A) => Option[B]): Option[B]
    */
  test("FlatMap") {
    def returnOpt(value: Int): Option[String] = {
      Some("Value: " + value)
    }

    val opt: Option[Int] = Some(1)
    val optNone: Option[Int] = None

    // Retorna Some o None
    val some = opt.flatMap(returnOpt(_))
    val none = optNone.flatMap(returnOpt(_))

    assert(some == Some("Value: 1") && none == None)
  }


  /**
    * Flatten Option[Option[Int]] a Option[Int]
    */
  test("flatten") {
    val option = Some(Some(1))
    val r = option.flatten

    assert(r == Some(1))
  }


  /**
    * Map
    * def map[B](f: (A) => B): Option[B]
    */
  test("Map") {
    def foo(value: Int): String = "Value: " + value

    val opt = Option(1)
    val r = opt.map(foo(_))
    assert(r == Some("Value: 1"))
  }


  /**
    * Foreach
    * def foreach[U](f: (A) => U): Unit
    */
  test("Foreach") {
    def check(value: String): Unit = assert(value == "Ok")

    val opt = Option("Ok")
    opt.foreach(check)
  }


  /**
    * isDefined
    * def isDefined: Boolean
    */
  test("IsDefined") {
    val optNone: Option[Int] = None
    val optDefined = optNone.isDefined

    assert(!optDefined)
  }


  /**
    * isEmpty
    * def isEmpty: Boolean
    */
  test("isEmpty") {
    val optNone: Option[Int] = None
    val optDefined = optNone.isEmpty

    assert(optDefined)
  }


  /**
    * forall
    * def forall(p: (A) => Boolean): Boolean
    */
  test("forall") {
    def foo(value: Int): Boolean = {
      if ((value % 2) == 0) true else false
    }

    val opt1 = Option(1)
    val res1 = opt1.forall(foo(_))
    assert(!res1)

    val opt2 = Option(2)
    val res2 = opt2.forall(foo(_))
    assert(res2)
  }


  /**
    * exists
    * def exists(p: (A) => Boolean): Boolean
    */
  test("exists") {
    def foo(value: Int): Boolean = {
      if ((value % 2) != 0)
      //code
        true
      else
        false
    }

    val option1 = Option(1)
    val result1 = option1.exists(foo(_))
    assert(result1)

    val option2 = Option(2)
    val result2 = option2.exists(foo(_))
    assert(!result2)

    val option3: Option[Int] = None
    val result3 = option3.exists(foo(_))
    assert(!result3)
  }


  /**
    * orElse
    * def orElse[B >: A](alternative: => Option[B]): Option[B]
    */
  test("orElse") {
    val option1: Option[Int] = None
    val option2 = Option(2)

    val result = option1 orElse option2
    assert(result == Some(2))
  }


  /**
    * getOrElse
    * def getOrElse[B >: A](default: => B): B
    */
  test("GetOrElse") {
    val option1 = Option(1)
    val result1 = option1 getOrElse 0
    assert(result1 == 1)

    val option2: Option[Int] = None
    val result2 = option2 getOrElse 0
    assert(result2 == 0)
  }


  /**
    * toList (Convierte a una lista)
    * def toList: List[A]
    */
  test("toList") {
    val opt1 = Option(1)
    val res1 = opt1.toList

    val opt2: Option[Int] = None
    val res2 = opt2.toList

    assert(res1 == List(1))
    assert(res2 == List())
  }


  /**
    * Option.map/getOrElse
    */
  test("Option.map/getOrElse") {
    val number: Option[Int] = Some(3)
    val noNumber: Option[Int] = None

    val result = number map { _.toString} getOrElse "0"
    val result2 = noNumber map { _.toString} getOrElse "0"

    assert(result == "3")
    assert(result2 == "0")
  }


  /**
    * Option.fold
    */
  test("Option.fold") {
    val number: Option[Int] = Some(3)
    val noNumber: Option[Int] = None

    val result = number.fold("0") { _.toString }
    val result2 = noNumber.fold("0") { _.toString }

    assert(result == "3")
    assert(result2 == "0")
  }

}
