package cz.dp2.scalatest

import org.scalatest.WordSpec

/**
  * Created by kolmicenko on 31. 1. 2016.
  */
class ScalaTestWordSpec extends WordSpec {

  "A Set" when {
    "empty" should {
      "have size 0" in {
        assert(Set.empty.isEmpty)
      }

      "produce NoSuchElementException when head is invoked" in {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}

