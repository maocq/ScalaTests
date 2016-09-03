package com.maocq.classScala

import org.scalatest.FunSuite

class CaseClassTest extends FunSuite {

  /**
    * Case Class
    */
  test("Case Class") {
    case class User(
                     firstName: String,
                     lastName: String
                   )

    val user = User("Mauricio", "Carmona")
    assert(user.isInstanceOf[User])
  }


  /**
    * Getters
    */
  test("getters") {
    case class User(name: String)
    val user = User("mao")
    assert(user.name == "mao")
  }


  /**
    * Propiedades mutables
    */
  test("Mutables") {
    case class User(name: String, var email: String)
    val user = User("mao", "a@gmail.com")
    user.email = "abc@gmail.com"
    assert(user.email == "abc@gmail.com")
  }


  /**
    * Comparación
    */
  test("Comparacion") {
    case class User(first: String, last: String)
    val u1 = User("Mauricio", "Carmona")
    val u2 = User("Mauricio", "Carmona")
    val u3 = User("Monica", "Valderrama")

    assert(u1 == u2) // Igual valor
    assert(!(u1 eq u2)) // Diferente Referencia

    assert(!(u1 == u3)) // Diferente valor
  }


  /**
    * Valores por defecto
    */
  test("Valores por defecto") {
    case class User(first: String, city: String = "Medellin")
    val user = User("mao")
    assert(user.city == "Medellin")
  }


  /**
    * Parámetros con nombre
    */
  test("Parametros con nombre") {
    case class User(first: String, last: String)
    val user = User(last = "Carmona", first = "Mauricio")
    //val user = User("Mauricio", "Carmona")
    assert(user.first == "Mauricio")
  }


  /**
    * To String
    */
  test("toString") {
    case class User(first: String, last: String)
    val user = User("Mauricio", "Carmona")
    assert(user.toString == "User(Mauricio,Carmona)")
  }


  /**
    * Copy
    */
  test("Copy") {
    case class User(first: String, last: String)
    val u1 = User("Mauricio", "Carmona")
    val u2 = u1.copy(first = "John Mauricio")

    assert(u2.last == "Carmona")
  }


  /**
    * Tupla
    */
  test("Tupla") {
    case class User(first: String, last: String)
    val user = User("Mauricio", "Carmona")
    val parts = User.unapply(user).get
    assert(parts._1 == "Mauricio")
  }


  /**
    * Case Class son Serializable
    */
  test("Serializable") {
    case class UserCC(name: String)
    val userCC = UserCC("mao")

    class User(name: String)
    val user = new User("mao")

    assert(userCC.isInstanceOf[Serializable])
    assert(!user.isInstanceOf[Serializable])
  }


  /**
    * Coincidencia de patrones
    */
  test("Coincidencia de patrones") {
    case class User(first: String, last: String)
    val user = User("Mauricio", "Carmona")

    val result = user match {
      case User(_, "Carmona") => "Ok"
      case _ => "wtf"
    }

    assert(result == "Ok")
  }

}
