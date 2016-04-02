package cz.dp2.scalatest

import org.scalatest.Spec

/**
  * Created by kolmicenko on 31. 1. 2016.
  */
class ScalaTestSpec extends Spec {

    object `Kolekce Set` {
      object `pokud je prázdná` {
        def `má počet prvků 0` {
          assert(Set.empty.size == 0)
        }

        def `pokud je zavolána metoda head na prázdnou kolekcí, volání skončí výjimkou NoSuchElementException` {
          intercept[NoSuchElementException] {
            Set.empty.head
          }
        }
      }
    }
  }

