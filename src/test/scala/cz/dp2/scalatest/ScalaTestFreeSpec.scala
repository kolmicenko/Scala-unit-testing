package cz.dp2.scalatest

/**
  * Created by kolmicenko on 31. 1. 2016.
  */

import org.scalatest.FreeSpec

class ScalaTestFreeSpec extends FreeSpec {
  "A Set" - {
    "when empty" - {
      "should have size 0" in {
        assert(Set.empty.size == 0)
      }

      "should produce NoSuchElementException when head is invoked on empty collection" in {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}

