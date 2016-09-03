package com.maocq.collections

import org.scalatest.FunSuite

class TuplesTest extends FunSuite{

  /**
    * Tuples
    */
  test("tuple") {
    val myTuple =  (1, "hello", Console)
    assert(myTuple._1 == 1)
  }


  /**
    * assign multiple
    */
  test("assign multiple") {
    val student = ("Sean Rogers", 21, 3.5)
    val (name, age, gpa) = student
    assert(age == 21)
  }


  /**
    * swap
    */
  test("swap") {
    val tuple = ("apple", 3).swap
    assert(tuple._1 == 3)
  }

}
