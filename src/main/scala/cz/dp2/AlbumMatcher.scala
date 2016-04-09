package cz.dp2

/**
  * Created by kolmicenko on 30. 1. 2016.
  */

case class AlbumMatcher(track: Track, album: Album)

class AlbumMatcherObserver(
                           songDatabase: SongDatabase,
                           countryLeaderBoard: AlbumController) {

  def recordMatchResult(result: AlbumMatcher): Unit = {
    val track = songDatabase.getTrackById(result.track.trackId)
    countryLeaderBoard.addTrackToAlbum(track, result.album)
    //val albums = songDatabase.getAlbumsByGenre(result.album.genre.id)
  }
}
