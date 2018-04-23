package com.github.twistedpair.lustbox.puzzle

/**
  * Are strings twiddled?
  */
object Twiddler {

  private val w = 3 // twiddle width

  // NOTE: Same in place logic can be applied to to the char[]'s to have O(1) memory and no object creation
  // is there a 3 char substring from b that is reversed in a?
  def isTwiddled(a:String, b:String):Boolean = {

      // All 3 char substrings for b
      val bPairs = b.toSeq.sliding(w).map(_.reverse).toSet

      // scan through the A pair iterator, stopping ASAP
      a.toSeq.sliding(w).exists( grp ⇒ bPairs.contains(grp))
  }

  // No substrings or sets or iterators (~15x faster)
  def isTwiddledArr(a:String, b:String):Boolean = {
    val arr = a.toCharArray
    val brr = b.toCharArray

    for {
      off ← 0 to b.length-w
      aOff ← 0 to a.length-w
    } {
      val s = a.length-1 - aOff
      //   println(s"arr[$s, ${s-1}, ${s-2}] == brr[$off, ${off+1}, ${off+2}]")
      if (arr(s) == brr(0+off) && arr(s - 1) == brr(1+off) && arr(s - 2) == brr(2+off))
        return true
    }

    false
  }
}
