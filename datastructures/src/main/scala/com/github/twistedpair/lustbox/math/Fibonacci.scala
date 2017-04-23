package com.github.twistedpair.lustbox.math

import scala.annotation.tailrec

/**
  * Fibonacci series examples
  * 0, 1, 1, 2, 3, 5, 8, 13, 21...
  * Note: 0th term is 0
  */
object Fibonacci {

  // TODO add me
  //def constant(n: Int): BigInt = ???

  def iterative(limit: Int): BigInt = {
    var f1:BigInt = 0
    var f2:BigInt = 1
    var f3:BigInt = 1

    for { _ ← 0 until limit } {
      f1 = f2 //1
      f2 = f3
      f3 = f1 + f2
    }
    f1
  }


  def recursive(limit: Int): BigInt = {

    @tailrec
    def fib(n: Int, prev: BigInt = 0, acc: BigInt = 1): BigInt =
      n match {
        case 0 ⇒ 0
        case 1 ⇒ acc
        case _ ⇒ fib(n - 1, acc, acc + prev)
      }

    fib(limit)
  }

  private val BigZero:BigInt = 0:BigInt
  private val BigOne:BigInt = 1

  // Credit: http://www.luigip.com/?p=200
  private val fibs:Stream[BigInt] = BigZero #:: fibs.scanLeft(BigOne)(_ + _)

  def stream(limit: Int): BigInt = fibs(limit)

}
