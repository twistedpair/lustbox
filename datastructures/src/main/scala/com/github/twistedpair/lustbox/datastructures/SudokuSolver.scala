package com.github.twistedpair.lustbox.datastructures

/**
  * Recursive string testing
  */
object SudokuSolver {

  // S3 based anagram solver

  type M = Array[Array[Int]]

  val SudokuSize = 9
  val SudokuRange = 0 until SudokuSize
  val AllBitsSet = 1 | 2 | 4 | 8 | 16 | 32 | 64 | 128 | 256
  val Masks = SudokuRange.map(n ⇒ (n + 1, 1 << n))

  // More efficient to use mutable arrays than sequences given direct access need
  case class Board(cols: Array[Int], rows: Array[Int], sqrs: Array[Int]) {
    /** Mark the empty position as taken **/
    def rm(move: (Int, Int), v: Int): Board = {
      val mask = 1 << v - 1
      rows(move._1) ^= mask
      cols(move._2) ^= mask
      sqrs(rcToSqr(move)) ^= mask
      this
    }

    /** Add back a position on guess fail **/
    def add(move: (Int, Int), v: Int): Board = {
      val mask = 1 << v - 1
      rows(move._1) |= mask
      cols(move._2) |= mask
      sqrs(rcToSqr(move)) |= mask
      this
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

    def optimizeMoves(b: Board, moves: List[(Int, Int)]) = moves.sortBy(guesses(b, _).size)

    def guesses(b: Board, m: (Int, Int)) = binToMoves(b.rows(m._1) & b.cols(m._2) & b.sqrs(rcToSqr(m)))

    val board = boardToBinary(b)
    val betterMoves = optimizeMoves(board, boardToMoves(b))

    def solve(b: Board, moves: List[(Int, Int)]): Option[Board] = moves match {
      //optimizeMoves(b, moves) match { // fewest moves, but 1x slower
      case Nil if b.isValid ⇒ Some(b) // solved!
      case m :: xs ⇒ { // next move
        for (guess ← guesses(b, m)) {
          solve(b.rm(m, guess), xs) match {
            case x@Some(_) ⇒ return x
            case _ ⇒ b.add(m, guess); None // undo, continue
          }
        }
        None // all guesses fail, backtrack
      }
    }

    solve(board, betterMoves)
  }

  /** Sudoku Utils **/

  /** Convert to binary representation of remaining empty entries **/
  def boardToBinary(b: M): Board = {
    val empty = Array.fill[Int](SudokuSize)(AllBitsSet)
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

  def boardToMoves(b: M): List[(Int, Int)] = {
    (for (
      r ← SudokuRange;
      c ← SudokuRange if b(r)(c) == 0
    ) yield {
      (r, c)
    }).toList
  }

  def rcToSqr(move: (Int, Int)): Int = (move._1 / 3.0).toInt * 3 + (move._2 / 3.0).toInt

  /** Binary to numerical value of bitfield for set bits **/
  def binToMoves(bin: Int) = bin match {
    case 0 ⇒ Nil
    case _ ⇒ for ((n, mask) ← Masks if (bin & mask) > 0) yield {
      n
    }
  }

}
