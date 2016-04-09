package cz.dp2

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
import cz.dp2.Countries.{Value => Country}

case class AlbumControllerEntry(country: Country, points: Int)

trait AlbumController {
  def addVictoryForCountry(country: Country): Unit
  def addTrackToAlbum(track: Track, album: Album): Unit
}
