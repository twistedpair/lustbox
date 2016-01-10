package com.github.twistedpair.lustbox.datastructures

import scala.collection.mutable.ArrayBuffer

/**
 * Recursive string testing
 */
final object SudokuSolver {

  // S3 based anagram solver

  type M = Array[Array[Int]]
  val SudokuRange = 0 until 9

  case class Board(cols: Array[Int], rows: Array[Int], sqrs: Array[Int]) {
    /** Mark the empty position as taken **/
    def apply(move: (Int, Int), v: Int): Board = {

      val cols2 = Array.fill(9)(0)
      Array.copy(cols, 0, cols2, 0, 9)
      val rows2 = Array.fill(9)(0)
      Array.copy(rows, 0, rows2, 0, 9)
      val sqrs2 = Array.fill(9)(0)
      Array.copy(sqrs, 0, sqrs2, 0, 9)

      val b = Board(cols2, rows2, sqrs2)

      val mask = 1 << v - 1
      b.rows(move._1) ^= mask
      b.cols(move._2) ^= mask
      b.sqrs(rcToSqr(move)) ^= mask
      b
    }

    def isValid(): Boolean = Board.isValid(this)
  }

  object Board {

    def isValid(b: Board): Boolean = b match {
      case Board(Array(0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0)) ⇒ true
      case _ ⇒ false
    }
  }

  /** 0 Value indicates unset square **/
  def solveSudoku(b: M): Option[Board] = {

    val moves = boardToMoves(b)
    val board = boardToBinary(b)

    var n = 0

    def solve(b: Board, moves: List[(Int, Int)]): Option[Board] = {
      //def solve(b: Board, moves: List[(Int, Int)], taken: List[(Int, Int, Int)]): Option[Board] = {

      //println(taken.reverse)
      n += 1

      moves match {
        case Nil if b.isValid ⇒ Some(b)
        case m :: xs ⇒ { // next move

          val guesses = b.rows(m._1) & b.cols(m._2) & b.sqrs(rcToSqr(m))
          if (guesses > 0) {
            for (guess ← binToMoves(guesses)) {
              //solve(b(m, guess), xs, (m._1, m._2, guess) +: taken) match {
              solve(b(m, guess), xs) match {
                case x @ Some(_) ⇒ return x
                case _ ⇒ None // continue
              }
            }
          }
          None
        }
      }
    }

    val start = System.nanoTime()
    val r = solve(board, moves)
    val ts = System.nanoTime() - start
    println(s"moves: $n ms: ${ts / 1000000}")
    r
  }

  /** Convert to binary representation of remaining empty entries **/
  def boardToBinary(b: M): Board = {
    val empty = Array.fill[Int](9)(511) // all set
    val cols = empty.toBuffer
    val rows = empty.toBuffer
    val sqrs = empty.toBuffer

    for (
      r ← SudokuRange;
      c ← SudokuRange if b(r)(c) > 0
    ) {

      val mask = 1 << b(r)(c) - 1
      cols(c) ^= mask
      rows(r) ^= mask
      sqrs(rcToSqr((r, c))) ^= mask
    }

    Board(cols.toArray, rows.toArray, sqrs.toArray)
  }

  /** Sudoku Utils **/
  def boardToMoves(b: M): List[(Int, Int)] = {
    (for (
      r ← SudokuRange;
      c ← SudokuRange if b(r)(c) == 0
    ) yield { (r, c) }).toList
  }

  def rcToSqr(move: (Int, Int)): Int = (move._1 / 3.0).toInt * 3 + (move._2 / 3.0).toInt

  /** Binary to numerical value of bitfield for set bits **/
  def binToMoves(bin: Int): List[Int] =
    SudokuRange
      .map(n ⇒ (n + 1, 1 << n))
      .flatMap { nm ⇒ if ((bin & nm._2) > 0) Some(nm._1) else None }
      .toList

}
