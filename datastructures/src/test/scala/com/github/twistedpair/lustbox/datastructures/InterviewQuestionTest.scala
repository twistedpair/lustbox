package com.github.twistedpair.lustbox.datastructures

import com.github.twistedpair.lustbox.datastructures.NestedStringValidation.isValid
import org.scalatest.{FlatSpec, Matchers}

/**
 * Ensure crucial aspects of constraints work
 */
final class InterviewQuestionTest extends FlatSpec with Matchers {

  behavior of "A InterviewQuestionTest"

  it should "[] case" in {
    isValid("[]") shouldBe true
  }
  it should "() case" in {
    isValid("()") shouldBe true
  }
  it should "[()] case" in {
    isValid("[()]") shouldBe true
  }
  it should "[(]) case" in {
    isValid("[(])") shouldBe false
  }
  it should "[ case" in {
    isValid("[") shouldBe false
  }
  it should "][ case" in {
    isValid("][") shouldBe false
  }
  it should ")( case" in {
    isValid(")(") shouldBe false
  }
  it should "[((] case" in {
    isValid("[))]") shouldBe false
  }
  it should "[()()][((()))()] case" in {
    isValid("[()()][((()))()]") shouldBe true
  }
}
