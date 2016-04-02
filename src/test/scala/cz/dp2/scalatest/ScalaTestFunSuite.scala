package cz.dp2.scalatest

import org.scalatest.FunSuite

/**
  * Created by kolmicenko on 31. 1. 2016.
  */
class ScalaTestFunSuite extends FunSuite{

  test("Prazdny Set ma velikost 0") {
    assert(Set.empty.size == 0)
  }

  test("Zavolani metody head nad prazdnym setem vyhodi vyjimku NoSuchElementException") {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }
}

