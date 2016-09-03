package com.maocq.futures.actors

import akka.actor.{Actor, Props}

class Actor1 extends Actor{

  override def preStart(): Unit = {
    // Crear un nuevo actor
    val actor2 = context.actorOf(Props[Actor2], "actor2")
    // Llamando actor
    actor2 ! "mensaje"
  }

  def receive = {
    case msg:String => println(msg)
    case _       => println("\twtf")
  }
}
