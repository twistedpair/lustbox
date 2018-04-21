package com.github.twistedpair.lustbox.puzzel

import com.github.twistedpair.lustbox.puzzel.SingleDuplicateSubstrings._
import org.scalatest.{FlatSpec, Matchers}

final class SingleDuplicateSubstringsTest extends FlatSpec with Matchers {

  behavior of "A Fixed Length Substring Duplicate Finder"

  it should "find a single substring" in {
    val s = "abcdeff"
    val k = 4
    findDupes(s,k) shouldBe Seq("deff")
  }

  it should "find three substrings" in {
    val s = "abcdeffgh"
    val k = 4
    findDupes(s,k) shouldBe Seq("deff", "effg", "ffgh")
  }

  it should "a typical string with special characters" in {
    val s = "Check \"Scala\" in technologies list (unavailable if module has Scala facet attached)"
    val k = 2
    findDupes(s,k) shouldBe Seq("tt")
  }

  it should "handle empty string" in {
    val s = ""
    val k = 4
    findDupes(s,k) shouldBe Nil
  }

  it should "handle non viable k value: 0" in {
    val s = "abcdefghijklmnop"
    val k = 1
    findDupes(s,k) shouldBe Nil
  }

  it should "handle non viable k value: -1" in {
    val s = "abcdefghijklmnop"
    val k = 1
    findDupes(s,k) shouldBe Nil
  }

  it should "handle non viable k value: > length s" in {
    val s = "abcd"
    val k = 5
    findDupes(s,k) shouldBe Nil
  }
}
