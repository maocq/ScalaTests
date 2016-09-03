package com.maocq.classScala

import org.scalatest.FunSuite

class ObjectsTest extends FunSuite {

  /**
    * Object
    */
  test("Object") {
    object User {
      def name = "Mauricio"
      def email = "a@gmail.com"
    }

    assert(User.name == "Mauricio")
  }


  /**
    * Única instancia
    */
  test("Unica instancia") {
    object Object {
    }

    val o1 = Object
    val o2 = Object

    assert(o1 eq o2)
  }


  /**
    * Método
    */
  test("Método") {
    object Object {
      def num:Int = 1
    }

    assert(Object.num == 1)
  }

}
