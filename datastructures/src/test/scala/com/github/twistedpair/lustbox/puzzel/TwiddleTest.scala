package com.github.twistedpair.lustbox.puzzel

import com.github.twistedpair.lustbox.puzzel.SudokuSolver._
import org.scalatest.{FlatSpec, Matchers}

final class TwiddleTest extends FlatSpec with Matchers {

  behavior of "A Twiddle Detector"

  it should "match twiddled strings" in {
    val a = "themoonis"
    val b = "hnoo32"
    Twiddler.isTwiddled(a,b) shouldBe true
  }

  it should "not match non-twiddled strings" in {
    val a = "1234567890"
    val b = "abcdefghij"
    Twiddler.isTwiddled(a,b) shouldBe false
  }

  behavior of "A Twiddle Arry Impl"

  it should "match twiddled strings" in {
    val a = "themoonis"
    val b = "hnoo32"
    Twiddler.isTwiddled(a,b) shouldBe true
  }

  it should "not match non-twiddled strings" in {
    val a = "1234567890"
    val b = "abcdefghij"
    Twiddler.isTwiddledArr(a,b) shouldBe false
  }
}
