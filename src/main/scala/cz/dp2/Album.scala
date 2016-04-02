package cz.dp2

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
import org.joda.time.Period

class Album(var title: String, var year: Int, var artist: Artist, var genre: Genre, var tracks: Option[List[Track]]) {

  def this(title: String, year: Int, artist: Artist, genre: Genre) = this(title, year, artist,genre, None)

  def ageFrom(now: Int) = now - year

  def period = tracks.getOrElse(Nil).map(_.period).foldLeft(Period.ZERO)(_.plus(_))

  override def toString = ("Album" + "[" + title + "]")
}
