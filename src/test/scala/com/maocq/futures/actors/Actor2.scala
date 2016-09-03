package com.maocq.futures.actors

import akka.actor.Actor

class Actor2 extends Actor{
  def receive = {
    case msg:String =>
      //println(msg)
      assert(msg == "mensaje")
    case _       => println("\twtf")
  }
}
