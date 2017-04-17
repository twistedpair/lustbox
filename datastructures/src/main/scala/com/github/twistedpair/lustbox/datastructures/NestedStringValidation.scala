package com.github.twistedpair.lustbox.datastructures

/**
 * Recursive string testing
 */
object NestedStringValidation {

  def isValid(s: String): Boolean = {
    val chars = s.toList
    validate(chars.take(1), chars.tail)
  }

  @annotation.tailrec
  def validate(headStack: List[Char], chars: List[Char]): Boolean = {

    if (chars.isEmpty) {
      return headStack.isEmpty // must have been balanced
    }

    val next = (headStack.headOption, chars.head) match {
      case (Some('['), c) ⇒ c match {
        case ']' ⇒ (headStack.tail, chars.tail) // close out
        case '(' ⇒ (c +: headStack, chars.tail) // go deeper
        case _ ⇒ return false
      }
      case (Some('('), c) ⇒ c match {
        case '(' ⇒ (c +: headStack, chars.tail) // go deeper
        case ')' ⇒ (headStack.tail, chars.tail) // close out
        case _ ⇒ return false
      }
      case (None, c) ⇒ (c +: headStack, chars.tail) // top level, go deeper
      case _ ⇒ return false
    }

    validate(next._1, next._2)
  }

}
