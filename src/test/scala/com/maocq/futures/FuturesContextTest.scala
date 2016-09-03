package com.maocq.futures

import org.scalatest.FunSuite

import scala.concurrent.{ExecutionContext, Future}

class FuturesContextTest extends FunSuite {

  /**
    * Contexto de ejecución
    */
  test("Execution Context") {

    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

    val future = Future {
      5
    }
    future onSuccess {
      case answer: Int => assert(answer == 5)
    }
    future onFailure {
      case th: Throwable => println(s"\tFAILURE! returned: $th")
    }
  }

}
