package com.maocq.futures.actors

import akka.actor.Actor

class MyActor extends Actor{
  def receive = {
    case "hello" => println("\tHello Akka")
    case _       => //println("\twtf")
  }
}
