package com.maocq.classScala

import org.scalatest.FunSuite

class TraitsTest extends FunSuite {

  /**
    * Trait
    */
  test("Trait") {
    trait Animal {
      def getName: String
    }

    class Dog(name: String) extends Animal {
      def getName: String = name
    }

    val dog = new Dog("toby")
    val name = dog.getName
    assert(name == "toby")
  }


  /**
    * Intancia
    */
  test("InstanceOf") {
    trait Animal {
      def getName: String
    }
    class Dog(name: String) extends Animal {
      def getName: String = name
    }

    val dog = new Dog("toby")
    assert(dog.isInstanceOf[Dog])
    assert(dog.isInstanceOf[Animal])
  }


  /**
    * Atributos abstractos y concretos
    */
  test("Atributos") {
    trait ExampleTrait {
      var numA: Int
      // abstract
      val numB: Int = 5 // concrete
    }

    class Example(num: Int) extends ExampleTrait {
      var numA: Int = num
    }

    val e = new Example(3)
    val result = e.numA + e.numB
    assert(result == 8)
  }


  /**
    * Métodos abstractos y concretos
    */
  test("Métodos") {
    trait ExampleTrait {
      def add(value: Int): Int = value + 1

      def remove(value: Int): Int
    }

    class Example extends ExampleTrait {
      def remove(value: Int): Int = value - 1
    }

    val e = new Example
    assert(e.add(1) == 2)
    assert(e.remove(2) == 1)
  }

}
