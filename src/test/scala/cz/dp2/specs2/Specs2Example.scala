package cz.dp2.specs2

import org.specs2.Specification

/**
  * Created by kolmicenko on 31. 1. 2016.
  */
class Specs2Example extends Specification {

  def is = s2"""
      Toto je specifikace pro kontrolu textového řetězce Hello world
      The hello world String by měl
      obsahovat 11 znaků              $e1
      začínat řetezcem 'Hello'        $e2
      končit řetězcem 'world'         $e3
      """

  def e1 = "Hello world" must have size(11)
  def e2 = "Hello world" must startWith("Hello")
  def e3 = "Hello world" must endWith("world")
  //System.out.print(1/288)
}

