package com.maocq.collections

import org.scalatest.FunSuite

class MapsTest extends FunSuite{

  /**
    * Maps
    */
  test("myMaps") {
    val myMap = Map("JAN" -> "January", "FEB" -> "February")
    assert(myMap("JAN")== "January")
  }


  /**
    * Size
    */
  test("Size") {
    val myMap = Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")
    assert(myMap.size == 3)
  }


  /**
    * Add
    */
  test("add") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")
    val newMap = myMap + ("APR" -> "April")

    assert(newMap.size == 4)
  }


  /**
    * get
    */
  test("get") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")

    val month = myMap("FEB")
    assert(month == "February")
  }


  /**
    * Clave que no existe
    */
  test("missingKey") {
    try {
      val myMap =  Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")
      val month = myMap("MAY")
    } catch {
      case exception:Exception  =>
        assert(exception.isInstanceOf[NoSuchElementException])
    }
  }


  /**
    * getOrElse
    */
  test("getOrElse") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")
    val x = myMap.getOrElse("JUN", "missing data")
    val y = myMap.getOrElse("MAR", "missing data")

    assert(x == "missing data")
    assert(y == "March")
  }


  /**
    * contains
    */
  test("contains") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February")

    assert(myMap.contains("JAN"))
    assert(!myMap.contains("MAR"))
  }


  /**
    * remove
    */
  test("remove") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February")

    val newMap = myMap - "JAN"
    assert(!newMap.contains("JAN"))
  }


  /**
    * remove list
    */
  test("remove list") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")
    val newMap = myMap -- List("JAN", "FEB")

    assert(newMap.size == 1)
  }


  /**
    * removed with a tuple
    */
  test("removed with a tuple") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")
    val newMap = myMap - ("JAN", "FEB")

    assert(!newMap.contains("JAN") && !newMap.contains("FEB"))
  }


  /**
    * Eliminación de elementos no existentes
    */
  test("removal ") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February")
    val newMap = myMap - "MAR"

    assert(newMap.size == 2)
  }


  /**
    * equals
    */
  test("equals") {
    val myMap1 =  Map("JAN" -> "January", "FEB" -> "February")
    val myMap2 =  Map("FEB" -> "February", "JAN" -> "January")

    assert(myMap1.equals(myMap2))
  }


  /**
    * keys
    */
  test("keys") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February")
    val keys = myMap.keys

    assert(keys == Set("JAN", "FEB"))
  }


  /**
    * values
    */
  test("values") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February")
    val values = myMap.values.toList

    assert(values == List("January", "February"))
  }


  /**
    * foreach
    */
  test("foreach") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February", "MAR" -> "March")
    var months: List[String] = List()
    myMap foreach {
      case (x, y) => months = y :: months
    }
    assert(months == List("March", "February", "January"))
  }


  /**
    * map
    */
  test("map") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February")
    val result = myMap map {
      case (x, y) => s"$x - $y"
    }

    assert(result == List("JAN - January", "FEB - February"))
  }


  /**
    * filter
    */
  test("filter") {
    val myMap =  Map("JAN" -> 10, "FEB" -> 20, "MAR" -> 18)
    val newMap = myMap filter {
      case (x, y) => y > 15
    }

    assert(newMap.size ==  2)
  }


  /**
    * find
    */
  test("find") {
    val myMap =  Map("JAN" -> 10, "FEB" -> 20, "MAR" -> 18)
    val newMap = myMap find {
      case (x, y) => y > 15
    }

    assert(newMap.toString ==  "Some((FEB,20))")
  }


  /**
    * unzip
    */
  test("unzip") {
    val myMap =  Map("JAN" -> "January", "FEB" -> "February")

    val mytuple = myMap.unzip
    assert(mytuple._1 == List("JAN", "FEB"))
    assert(mytuple._2 == List("January", "February"))
  }

}
