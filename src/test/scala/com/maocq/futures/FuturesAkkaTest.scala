package com.maocq.futures

import akka.actor.{ActorSystem, Props}
import com.maocq.futures.actors.{Actor1, MyActor}
import org.scalatest.FunSuite

class FuturesAkkaTest extends FunSuite {

  /**
    * Hello Akka
    */
  test("Hola Akka") {
    val system = ActorSystem("HelloSystem")
    val helloActor = system.actorOf(Props[MyActor], name = "helloActor")
    /*
     ! (“tell”) envía el mensaje y devuelve inmediatamente
     */
    helloActor ! "hello"
    helloActor ! "buenos dias"
  }


  /**
    * Llamado entre actores
    */
  test("Varios actores") {
    akka.Main.main(Array(classOf[Actor1].getName))
  }

}
