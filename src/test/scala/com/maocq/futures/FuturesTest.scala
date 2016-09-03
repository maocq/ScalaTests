package com.maocq.futures

import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise, blocking}
import scala.util.{Failure, Success}

class FuturesTest extends FunSuite {

  /**
    * Futuro básico
    */
  test("Hola Futuro") {
    val s = "Hello"
    val f: Future[String] = Future {
      s + " future!"
    }
    f onSuccess {
      case msg =>
        println("\t" + msg)
        assert(msg == "Hello future!")
    }
  }


  /**
    * onSuccess, onFailure
    */
  test("Futuro onSuccess, onFailure") {
    val future = Future {
      val response = 5
      response
    }
    future onSuccess {
      case answer: Int =>
        println("\tSuccess!")
        assert(answer == 5)
    }
    future onFailure {
      case th: Throwable => println(s"\tFAILURE! returned: $th")
    }
  }


  /**
    * Múltiples futuros
    */
  test("Futuros multiples") {

    val f = Future {
      5
    }
    val g = Future {
      3
    }
    val h = for {
      x: Int <- f
      y: Int <- g
    } yield x + y

    h onSuccess {
      case answer: Int =>
        assert(answer == 8)
    }
    h onFailure {
      case th: Throwable => println(s"FAILURE! returned: $th")
    }

  }


  /**
    * For comprehension Ok
    */
  test("For comprehension") {

    val f = Future(5)
    val g = Future(3)
    val h = Future(1)

    val j = for {
      x: Int <- f
      y: Int <- g
      z: Int <- h
    } yield x + y + z


    j onComplete {
      case Success(value) => assert(value == 9)
      case Failure(e) => e.printStackTrace()
    }

  }


  /**
    * For comprehension Error (Retorna la primera excepción que se lance)
    */
  test("For comprehension Error") {
    val f = Future(5)
    val g = Future(3 / 0)
    val h = Future("1p".toInt)

    val j = for {
      x: Int <- f
      y: Int <- g
      z: Int <- h
    } yield x + y + z

    j onFailure {
      case th: Throwable => assert(th.getMessage == "/ by zero")
    }
  }


  /**
    * For comprehension def
    */
  test("For comprehension def") {
    val oneFuture: Future[Int] = Future(1)
    val twoFuture: Future[Int] = Future(2)
    val threeFuture: Future[Int] = Future(3)

    def sumForComprehension(): Future[Int] = for {
      oneValue <- oneFuture
      twoValue <- twoFuture
      threeValue <- threeFuture
    } yield oneValue + twoValue + threeValue


    def sumFlatMap(): Future[Int] = oneFuture.flatMap { oneValue =>
      twoFuture.flatMap { twoValue =>
        threeFuture.map { threeValue =>
          oneValue + twoValue + threeValue
        }
      }
    }


    sumForComprehension onSuccess {
      case x => assert(x == 6)
    }
    sumFlatMap onComplete {
      case Success(x) => assert(x == 6)
      case Failure(e) => e.printStackTrace()
    }
  }


  /**
    * Respuesta de futuro con onComplete
    */
  test("onComplete") {
    val f = Future[String] {
      "onComplete"
    }

    //f onComplete { ...
    f.onComplete {
      case Success(value) => assert(value == "onComplete")
      case Failure(e) => e.printStackTrace()
    }
  }


  /**
    * Futuros anidados
    */
  test("Futuros anidados") {
    val f = Future {
      5
    }
    val g = f map { answer =>
      answer + 4
    }
    g onSuccess {
      case answer => assert(answer == 9)
    }
  }


  /**
    * Esperar respuesta de un Futuro
    */
  test("Esperar") {
    val f = Future {
      //sleep(100)
      1 + 1
    }

    Await.ready(f, Duration.Inf).foreach {
      case (value) =>
        assert(value == 2)
    }
  }


  /**
    * Esperar resultado
    */
  test("Await") {
    val f = Future {
      //sleep(1000)
      1 + 1
    }

    val result = Await.result(f, Duration.Inf)
    assert(result == 2)
  }


  /**
    * Verificar si el futuro ya termino
    */
  test("Futuro is completed") {
    val future: Future[String] = Future[String] {
      "Future"
    }
    while (!future.isCompleted) {}
    assert(future.isCompleted)
  }


  /**
    * And then Hacer después
    */
  test("And then") {
    val f = Future {
      5
    }
    f andThen {
      case Success(v) => //println(s"Success! returned: $v")
    } andThen {
      case Failure(t) => println(t)
      case Success(v) => assert(v == 5)
    }
  }


  /**
    * Si falla un futuro continua con el siguiente
    */
  test("fall backTo") {
    val f = Future {
      sys.error("failed")
    }
    val g = Future {
      5
    }
    val h = f fallbackTo g

    h onComplete {
      case Success(value) => assert(value == 5)
      case Failure(e) => e.printStackTrace()
    }

  }


  /**
    * Primero en terminar
    */
  test("firstCompletedOf") {
    val f = Future {
      sleep(20)
      5
    }
    val g = Future {
      "3"
    }
    Future.firstCompletedOf(Seq(f, g)).map {
      case i:Int => println(i)
      case s:String => assert(s == "3")
    }
  }


  /**
    * Filtros
    * Si el filtro no coincide, retorna NoSuchElementException
    */
  test("Filtros") {
    val f = Future {
      5
    }
    //val g = f filter { _ % 2 == 1 }
    val h = f filter {
      _ % 2 == 0
    }

    h onSuccess {
      case value => assert(false)
    }
    h onFailure {
      case th: Throwable =>
        assert(th.isInstanceOf[NoSuchElementException])
    }
  }


  /**
    * Blocking
    */
  test("Blocking") {
    val f = Future {
      blocking {
        3
      }
    }
    f onSuccess {
      case value => assert(value == 3)
    }
  }


  /**
    * Excepciones
    */
  test("Exceptions") {
    val f = Future {
      2 / 0
    }
    f onFailure {
      case nullPointer: NullPointerException =>
        println(nullPointer.getMessage)
      case arithmetic: ArithmeticException =>
        assert(arithmetic.isInstanceOf[ArithmeticException])
    }
  }


  /**
    * Map
    */
  test("Map") {
    val future = Future {
      true
    } map {
      answer => assert(answer)
    }
  }


  /**
    * FlatMap (Ejecuta simultáneamente future1 y future2)
    */
  test("FlatMap") {
    val future1 = Future {
      1
    }
    val future2 = Future {
      3
    }

    val response = future1 flatMap { v1 =>
      future2 map { v2 =>
        v1 + v2
      }
    }
    response onSuccess {
      case num => assert(num == 4)
    }

  }


  /**
    * Foreach
    */
  test("Foreach") {
    val future = Future {
      List("Uno", "Dos")
    } foreach {
      answer =>
        assert(answer.size == 2)
    }
  }


  /**
    * Recover
    */
  test("Recover") {
    val future = Future {
      1 / 0
    } recover {
      case e: ArithmeticException => "Indeterminacion"
    }

    future onSuccess {
      case e => assert(e == "Indeterminacion")
    }
    /*
    future onFailure {
      case e => println(e)
    }
    */
  }

  /**
    * Recover with
    */
  test("RecoverWith") {
    val future = Future {
      1 / 0
    }

    val f = future.recoverWith {
      case e: ArithmeticException =>
        Future.successful(0)
      //Future.failed(new Exception("Mi excepcion"))
    }

    f onSuccess {
      case e => assert(e == 0)
    }

    f onFailure {
      case e => println(e)
    }
  }


  /**
    * Recover with failed
    */
  test("recoverWith failed") {
    val future = Future {
      "1o".toInt
    }

    val f = future.recoverWith {
      case e: ArithmeticException => Future.successful(0)
      //case e:NumberFormatException => Future.failed(new Exception("..."))
    }

    f onSuccess {
      case e => println(e)
    }

    f onFailure {
      case e => assert(e.isInstanceOf[NumberFormatException])
    }

  }


  /**
    * Futuro como una función
    */
  test("Funcion Futuro") {

    def functionFuture(i: Int): Future[Int] = Future {
      i + 1
    }

    functionFuture(4).onComplete {
      case Success(result) => assert(result == 5)
      case Failure(e) => e.printStackTrace()
    }
  }


  /**
    * Futuro con retorno vació
    */
  test("Futuro Unit") {
    val f: Future[Int] = Future {
      3
    }
    val g: Future[Unit] = f map { answer =>
      // code
    }

    g onSuccess {
      case _ =>
        //println("END")
        val result = true
        assert(result)
    }
  }


  /**
    * Promesas
    */
  test("Promesas") {
    val f = Future {
      // sleep(1000)
      1
    }
    val p = Promise[Int]()

    p completeWith f

    p.future onSuccess {
      case x => assert(x == 1)
    }
  }


  /**
    * Promise
    */
  test("Promise") {
    val promise = Promise[String]

    promise.future onSuccess {
      case ok => assert(ok == "Ok =)")
    }

    promise.future onFailure {
      case error => println(error) //java.lang.Exception
    }

    Future {
      //sleep(2000)
      if (true)
        promise success "Ok =)"
      else
        promise failure new Exception
    }
  }


  /**
    * Futuro callBack y Futuro funcional
    */
  test("callBack y funcional") {

    /*
     * Retorno Unit
     * def onSuccess[U](pf: PartialFunction[T, U])(implicit executor: ExecutionContext): Unit
     */
    val futuroCallBack = Future {
      1
    } onSuccess {
      case e => e
    }
    //futuroCallBack: Unit = ()


    /*
     * Retorno Future
     * def map[S](f: (T) ⇒ S)(implicit executor: ExecutionContext): Future[S]
     */
    val futuroFuncional = Future {
      1
    } map {
      e => e + 1
    }
    //futuroFuncional: scala.concurrent.Future[Int] = List()
    //futuroFuncional: scala.concurrent.Future[Int] = Success(2)

  }


  /**
    * andThen
    * def andThen[U](pf: PartialFunction[Try[T], U])(implicit executor: ExecutionContext): Future[T]
    */
  test("andThen") {
    val f = Future(3)
    f andThen {
      case r => // println(r)     Success(3)
    } andThen {
      case Failure(t) => println(t)
      case Success(v) => assert(v == 3)
    }
  }


  /**
    * fallbackTo
    * def fallbackTo[U >: T](that: Future[U]): Future[U]
    */
  test("fallbackTo") {
    val f = Future(sys.error("failed"))
    val g = Future(5)
    val h = f fallbackTo g

    h onSuccess {
      case x => assert(x == 5)
    }
  }


  /**
    * flatMap
    * def flatMap[S](f: (T) ⇒ Future[S])(implicit executor: ExecutionContext): Future[S]
    */
  test("flatMap") {
    val f = Future(5)
    val g = Future(3)

    val h = f flatMap { (x: Int) => g map { (y: Int) => x + y } }

    h onSuccess {
      case res => assert(res == 8)
    }
  }


  /**
    * foreach
    * def foreach[U](f: (T) ⇒ U)(implicit executor: ExecutionContext): Unit
    */
  test("foreach") {
    val f = Future(5)

    f foreach {
      x => assert(x == 5)
    }
  }


  /**
    * map
    * def map[S](f: (T) ⇒ S)(implicit executor: ExecutionContext): Future[S]
    */
  test("map") {
    val f = Future(5)
    val h = f map { (x: Int) => x * 2}

    h onSuccess {
      case res => assert(res == 10)
    }
  }


  /**
    * onSuccess
    * def onSuccess[U](pf: PartialFunction[T, U])(implicit executor: ExecutionContext): Unit
    */
  test("onSuccess") {
    val f = Future(5)
    f onSuccess {
      case x => assert(x == 5)
    }
  }


  /**
    * onFailure
    * def onFailure[U](pf: PartialFunction[Throwable, U])(implicit executor: ExecutionContext): Unit
    */
  test("onFailure") {
    val f = Future(5 / 0)
    f onFailure {
      case x => assert(x.isInstanceOf[ArithmeticException])
    }
  }


  /**
    * recover
    * def recover[U >: T](pf: PartialFunction[Throwable, U])(implicit executor: ExecutionContext): Future[U]
    */
  test("recover") {
    val f = Future(6 / 0)
    val g = f recover {
      case e: ArithmeticException => 0
    }

    //g.map(x => assert(x == 0))
    g onSuccess { case x => assert(x == 0) }
  }


  /**
    * recoverWith
    * def recoverWith[U >: T](pf: PartialFunction[Throwable, Future[U]])(implicit executor: ExecutionContext): Future[U]
    */
  test("recoverWith") {
    val f = Future(5)
    val g = Future(6 / 0) recoverWith { case e: ArithmeticException => f }

    g onSuccess {
      case x => assert(x == 5)
    }
  }


  def sleep(millis: Long) = {
    Thread.sleep(millis)
  }

}
