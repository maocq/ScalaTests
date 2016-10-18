package com.maocq.futures

import akka.actor.{ActorRef, ActorSystem, Actor, Props}
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


  /**
    * Props atributos
    */
  test("Props") {
    object MyActor {
      def props(name:String): Props = Props(new MyActor(name))
    }

    class MyActor(name:String) extends Actor {
      def receive = {
        case x: String =>
          assert(x == "Hello")
          assert(name == "mao")
      }
    }

    val system = ActorSystem()
    val myActor = system.actorOf(MyActor.props("mao"), "myactor")

    myActor ! "Hello"
  }


  /**
    * ActorSelection
    */
  test("actorSelection") {
    object MyActor {
      def props: Props = Props(new MyActor)
    }
    class MyActor extends Actor {
      def receive = {
        case x: String =>
          //println(x + " " +  self.toString())
          assert(x == "Hello")
      }
    }

    val system = ActorSystem()
    val a1 = system.actorOf(MyActor.props, "myactor1")
    val a2 = system.actorOf(MyActor.props, "myactor2")
    val a3 = system.actorOf(MyActor.props, "myactor3")

    val a4 = system.actorOf(Props(new MyActor), "Mauricio")
    val a5 = system.actorOf(Props(new MyActor))

    /** Enviar mensaje */
    //system.actorSelection("user/myactor1") ! "Hello"

    /** Enviar a todos */
    system.actorSelection("user/*") ! "Hello"
  }


  /**
    * Routing
    */
  test("routing") {
    import akka.routing._

    class MyActor extends Actor {
      def receive = {
        case x: String =>
          //println(x + " " +  self.path)
          assert(x.contains("Msg"))
      }
    }

    val system = ActorSystem()
    /** Crea 5 Actores */
    val routerPool: ActorRef = system.actorOf(RoundRobinPool(5).props(Props(new MyActor)), "myRouter")

    /** Emitir a todos (5) */
    //routerPool ! Broadcast("Watch out for Davy Jones' locker")

    /** Emitir Uno por uno (Ejecuta tomando actores de forma aleatoria) */
    for (i <- 1 to 10) {
      routerPool ! "Msg " + i
    }
  }


  /**
    * EventStream
    */
  test("eventStream") {

    case class Tweet(tweet: String)
    class Listener extends Actor {
      def receive = {
        case t: Tweet =>
          //println(t.tweet + " " +  self.toString())
          assert(t.tweet == "My tweet")
      }
    }
    val system = ActorSystem()

    val tw1 = system.actorOf(Props(new Listener), "tw1")
    val tw2 = system.actorOf(Props(new Listener), "tw2")
    val tw3 = system.actorOf(Props(new Listener), "tw3")

    /** Subscribir tw1 y tw3 a Tweet */
    system.eventStream.subscribe(tw1, classOf[Tweet])
    system.eventStream.subscribe(tw3, classOf[Tweet])

    /** Publicar en Tweet */
    system.eventStream.publish(Tweet("My tweet"))
  }


  /**
    * Event Bus
    */
  test("EventBus") {
    import akka.event.EventBus
    import akka.event.LookupClassification

    final case class MsgEnvelope(topic: String, payload: Any)

    class LookupBusImpl extends EventBus with LookupClassification {
      type Event = MsgEnvelope
      type Classifier = String
      type Subscriber = ActorRef

      override protected def classify(event: Event): Classifier = event.topic

      override protected def publish(event: Event, subscriber: Subscriber): Unit = {
        subscriber ! event.payload
      }

      override protected def compareSubscribers(a: Subscriber, b: Subscriber): Int =
        a.compareTo(b)

      override protected def mapSize: Int = 128
    }

    val lookupBus = new LookupBusImpl

    /** Actor */
    class MyActor extends Actor {
      def receive = {
        case x: String =>
          //println(x + " " +  self.toString())
          assert(x == "hello")
      }
    }
    val system = ActorSystem()
    val a1 = system.actorOf(Props(new MyActor), "myactor1")
    val a2 = system.actorOf(Props(new MyActor), "myactor2")
    val a3 = system.actorOf(Props(new MyActor), "myactor3")

    /** Subscribir */
    lookupBus.subscribe(a1, "greetings")
    lookupBus.subscribe(a2, "greetings")

    /** Publicar */
    lookupBus.publish(MsgEnvelope("greetings", "hello"))

  }

}
