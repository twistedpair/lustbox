package com.github.twistedpair.lustbox.math

import org.scalatest.{FlatSpec, Matchers}

/**
 * Ensure crucial aspects of constraints work
 */
final class FibonacciTest extends FlatSpec with Matchers {

  val Series:Seq[BigInt] = Seq(0,1,1,2,3,5,8,13,21) // zero pad to align indexes
  val Fib100:BigInt = BigInt("354224848179261915075")

  behavior of "A Fibonacci Recursive Solution"

  it should "work for the lower series" in {
    for {
      (ans, idx) ← Series.zipWithIndex
    } yield Fibonacci.recursive(idx) shouldBe ans
  }

  it should "work for large values" in {
    Fibonacci.recursive(100) shouldBe Fib100
  }

  behavior of "A Fibonacci Iterative Solution"

  it should "work for the lower series" in {
    for {
      (ans, idx) ← Series.zipWithIndex
    } yield Fibonacci.iterative(idx) shouldBe ans
  }

  it should "work for large values" in {
    Fibonacci.iterative(100) shouldBe Fib100
  }

  behavior of "A Fibonacci Stream Solution"

  it should "work for the lower series" in {
    for {
      (ans, idx) ← Series.zipWithIndex
    } yield Fibonacci.stream(idx) shouldBe ans
  }

  it should "work for large values" in {
    Fibonacci.stream(100) shouldBe Fib100
  }
}
