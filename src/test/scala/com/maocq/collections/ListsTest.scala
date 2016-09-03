package com.maocq.collections


import java.lang.{IndexOutOfBoundsException, NumberFormatException}

import org.scalatest.FunSuite


class ListsTest extends FunSuite {

  /**
    * List
    */
  test("myList") {
    val myList = List(1, 2, 3)
    assert(myList(0) == 1)
    assert(myList(1) == 2)
    assert(myList(2) == 3)
  }


  /**
    * Pueden contener duplicados, inmutables y conservan el orden
    */
  test("List") {
    val list = List(1, 2, 3, 2)
    //list(2) = 5        error
    assert(list == List(1, 2, 3, 2))
  }


  /**
    * Lista vacia
    */
  test("List empty") {
    val empty = Nil
    assert(empty.isEmpty)
  }


  /**
    * Head
    */
  test("Head") {
    val list: List[Int] = List(1, 2)
    assert(list.head == 1)
  }


  /**
    * Tail
    */
  test("Tail") {
    val list: List[Int] = List(1, 2, 3)
    assert(list.tail == List(2, 3))
  }


  /**
    * Length
    */
  test("Length") {
    val list: List[Int] = List(1, 2)
    assert(list.length == 2)
  }


  /**
    * Reverse
    */
  test("Reverse") {
    val list: List[Int] = List(1, 2)
    val reverse = list.reverse
    assert(reverse == List(2, 1))
  }


  /**
    * Map
    */
  test("Map") {
    val list = List(1, 2, 3)
    val listMap = list.map(_ * 2)
    //val listMap = list.map(i => i * 2)
    //val listMap = list.map((i: Int) => i * 2)

    assert(listMap == List(2, 4, 6))
  }


  /**
    * Filter
    */
  test("Filter") {
    //filter
    val list = List(1, 2, 3)
    val listFilter = list.filter(_ % 2 == 0)
    assert(listFilter == List(2))

    //filterNot
    val list2 = List(1, 2, 3)
    val listFilterNot = list2.filterNot(_ % 2 == 0)
    assert(listFilterNot == List(1, 3))
  }


  /**
    * Reducción con una operación matemática
    */
  test("Reduced") {
    val list = List(1, 2, 3)
    val reduced = list.reduceLeft(_ + _)
    assert(reduced == 6)
  }


  /**
    * Reducción con valor inicial
    */
  test("Foldleft") {
    val list = List(1, 2, 3)
    val foldleft = list.foldLeft(2)(_ * _)
    assert(foldleft == 12)
  }


  /**
    * Retornar lista con nuevo elemento
    */
  test("Add") {
    val list = List(2, 3)
    val newList = 1 :: list
    assert(newList == List(1, 2, 3))
  }


  /**
    * Foreach
    */
  test("Foreach") {
    var count = 0;
    val list = List(2, 3)
    list.foreach(
      i => count += i
    )
    assert(count == 5)
  }

}
