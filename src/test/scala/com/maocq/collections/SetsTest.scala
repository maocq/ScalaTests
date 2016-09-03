package com.maocq.collections

import org.scalatest.FunSuite

class SetsTest extends FunSuite{

  /**
    * Sets
    */
  test("mySet") {
    val fruit:Set[String] = Set("apple", "orange", "pears")

    assert(fruit.head == "apple")
  }


  /**
    * size (Valores distintos)
    */
  test("size") {
    val fruit = Set("apple", "orange", "pears", "apple")
    assert(fruit.size == 3)
  }


  /**
    * add
    */
  test("add") {
    val fruit = Set("apple")
    val newSet = fruit + "orange"

    assert(newSet.size == 2)
  }


  /**
    * removed
    */
  test("removed ") {
    val fruit = Set("apple", "orange", "pears")
    val newSet = fruit - "orange"

    assert(newSet.size == 2)
  }


  /**
    * mixed
    */
  test("mixed") {
    val fruit = Set("apple", 50)
    assert(fruit.size == 2)
  }


  /**
    * contains
    */
  test("contains") {
    val fruit = Set("apple", "orange")
    assert(fruit.contains("orange"))
  }


  /**
    * checked
    */
  test("checked ") {
    val fruit = Set("apple", "orange")
    assert(fruit("orange"))
  }


  /**
    * diff
    */
  test("diff ") {
    val f1 = Set("apple", "orange", "pears")
    val f2 = Set("apple", "pears")

    val newSet = f1 diff f2
    assert(newSet == Set("orange"))
  }


  /**
    * equals
    */
  test("equals") {
    val f1 = Set("apple", "orange", "pears")
    val f2 = Set("apple", "pears", "orange")

    assert(f1.equals(f2))
  }

}
