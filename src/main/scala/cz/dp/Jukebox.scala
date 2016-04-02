package cz.dp

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
final class JukeBox private (val albums:Option[List[Album]], val currentTrack:Option[Track]) {
  def this(albums:Option[List[Album]]) = this(albums, None)
  def readyToPlay = albums.isDefined
  def play = new JukeBox(albums, Some(albums.get(0).tracks.get(0)))
}
