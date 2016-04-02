package cz.dp2.mockito

import cz.dp2._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec}
import org.mockito.Mockito.verify
import org.mockito.Mockito.when

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
class MockitoTestClass extends FunSpec with MockitoSugar {

  val genre = new Genre(1, "Rock")

  val artist = new Artist("Jan", "Zpevak", Countries.Germany, Nil)

  val track = new Track("1", "pisen", artist)

  val album = new Album("albumPlnePisni", 2013, artist, genre)

  val albumControllerMock = mock[AlbumController]
  val songDatabaseMock = mock[SongDatabase]

  // set expectations
  albumControllerMock.addTrackToAlbum(track, album)
  //(countryLeaderBoardMock.addTrackToAlbum _).expects(track, album)

  when(songDatabaseMock.getTrackById("1")).thenReturn(track)
  val trackObj = songDatabaseMock.getTrackById(track.trackId)
  System.out.print(track.name)
  verify(songDatabaseMock).getTrackById(track.trackId).eq(trackObj)
  // configure stubs
  //(userDetailsServiceStub.getTrackById _) when track.trackId returns track
  //val albumsReturn = List(album, album)
  //(userDetailsServiceMock.getAlbumsByGenre _) when genre.id returns albumsReturn
  //(userDetailsServiceStub.getTrackById _) when winner.id returns winner

  // run system under test
  val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseMock, albumControllerMock)
  when(songDatabaseMock.getAlbumsByGenre(1)).thenReturn(List(album))
  albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))

  val albums = List(album)

  verify(songDatabaseMock).getAlbumsByGenre(1).eq(albums)
}
