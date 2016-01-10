package com.github.twistedpair.lustbox.datastructures

import org.scalatest.{ FlatSpec, Matchers }
import com.github.twistedpair.lustbox.datastructures.SudokuSolver._

/**
 * Ensure crucial aspects of constraints work
 */
final class SudokuSolverTest extends FlatSpec with Matchers {

  behavior of "A InterviewQuestionTest"

  it should "Solve Easy Sudoku" in {
    val soln = solveSudoku(sudokuTest1)
    soln.isDefined shouldBe true
  }

  it should "Solve Medium Sudoku" in {

    val soln = solveSudoku(sudokuTest2)
    soln.isDefined shouldBe true
  }

  it should "Solve Hard Sudoku" in {
    val soln = solveSudoku(hardSudokuTest)
    soln.isDefined shouldBe true
  }

  it should "Validate full board as Valid" in {
    val board = boardToBinary(solvedSudoku)
    board.isValid shouldBe true
  }

  it should "Validate partial board as Invalid" in {
    val board = boardToBinary(sudokuTest1)
    board.isValid shouldBe false
  }

  implicit def tupToArr(tup: Tuple9[Int, Int, Int, Int, Int, Int, Int, Int, Int]): Array[Int] =
    Array(tup._1, tup._2, tup._3, tup._4, tup._5, tup._6, tup._7, tup._8, tup._9)

  type M = Array[Array[Int]]

  val hardSudokuTest: M = Array(
    (5, 0, 7, 0, 0, 0, 3, 0, 0),
    (0, 8, 0, 0, 1, 7, 0, 0, 0),
    (0, 0, 0, 3, 0, 9, 0, 0, 0),
    (6, 5, 0, 0, 0, 1, 0, 8, 0),
    (0, 4, 0, 0, 0, 0, 0, 1, 0),
    (0, 3, 0, 9, 0, 0, 0, 4, 7),
    (0, 0, 0, 8, 0, 4, 0, 0, 0),
    (0, 0, 0, 2, 5, 0, 0, 3, 0),
    (0, 0, 6, 0, 0, 0, 4, 0, 9))

  val sudokuTest1: M = Array(
    (4, 5, 3, 0, 1, 8, 7, 2, 6),
    (1, 7, 6, 0, 5, 4, 9, 8, 0),
    (2, 8, 9, 7, 3, 6, 5, 1, 4),
    (5, 6, 4, 8, 2, 3, 1, 9, 7),
    (9, 3, 2, 0, 7, 1, 6, 5, 8),
    (7, 1, 8, 5, 6, 9, 3, 4, 2),
    (3, 9, 5, 0, 0, 0, 8, 7, 1),
    (8, 2, 1, 0, 0, 0, 4, 6, 5),
    (6, 4, 7, 0, 0, 0, 2, 3, 9))

  val sudokuTest2: M = Array(
    (0, 8, 0, 0, 0, 0, 2, 0, 0),
    (0, 0, 0, 0, 8, 4, 0, 9, 0),
    (0, 0, 6, 3, 2, 0, 0, 1, 0),
    (0, 9, 7, 0, 0, 0, 0, 8, 0),
    (8, 0, 0, 9, 0, 3, 0, 0, 2),
    (0, 1, 0, 0, 0, 0, 9, 5, 0),
    (0, 7, 0, 0, 4, 5, 8, 0, 0),
    (0, 3, 0, 7, 1, 0, 0, 0, 0),
    (0, 0, 8, 0, 0, 0, 0, 4, 0))

  val solvedSudoku: Array[Array[Int]] = Array(
    (4, 5, 3, 9, 1, 8, 7, 2, 6),
    (1, 7, 6, 2, 5, 4, 9, 8, 3),
    (2, 8, 9, 7, 3, 6, 5, 1, 4),
    (5, 6, 4, 8, 2, 3, 1, 9, 7),
    (9, 3, 2, 4, 7, 1, 6, 5, 8),
    (7, 1, 8, 5, 6, 9, 3, 4, 2),
    (3, 9, 5, 6, 4, 2, 8, 7, 1),
    (8, 2, 1, 3, 9, 7, 4, 6, 5),
    (6, 4, 7, 1, 8, 5, 2, 3, 9))
}
