package cz.dp

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
object AlbumAccumulator {
  def accumulate(map:Map[String, Int], tuples:Seq[(String, Int)]) = {
    tuples.foldLeft(map)((a,b) => a + b)
  }
}
