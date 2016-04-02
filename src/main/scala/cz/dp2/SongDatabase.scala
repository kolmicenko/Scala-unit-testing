package cz.dp2

/**
  * Created by kolmicenko on 30. 1. 2016.
  */


trait SongDatabase {
  def getTrackById(trackId: String): Track
  def getAlbumsByGenre(genreId : Int): List[Album]
}
