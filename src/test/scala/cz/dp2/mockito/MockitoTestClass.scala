package cz.dp2.mockito

import java.util.NoSuchElementException

import cz.dp2._
import org.junit.{Before, Test}
import org.mockito.Mockito.{verify, when}
import org.scalatest.ShouldMatchers
import org.scalatest.mock.MockitoSugar

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
class MockitoTestClass extends MockitoSugar with ShouldMatchers {

  var genre: Genre = _
  var genre2: Genre = _
  var artist: Artist = _
  var track: Track = _
  var album: Album = _
  var albumWithoutGenre: Album = _

  val albumControllerMock = mock[AlbumController]
  val songDatabaseMock = mock[SongDatabase]

  @Before
  def beforeMethod: Unit = {
    genre = new Genre(1, "Rock")
    genre2 = new Genre(2, "")

    artist = new Artist("Jan", "Zpevak", Countries.Germany, Nil)

    track = new Track("1", "pisen", artist)

    album = new Album("albumPlnePisni", 2013, artist, genre)
    albumWithoutGenre = new Album("Album bez zanru", 2001, artist, genre2)

  }

  @Test
  def testMockitoTrackObject: Unit = {
    // set expectations
    albumControllerMock.addTrackToAlbum(track, album)

    when(songDatabaseMock.getTrackById("1")).thenReturn(track)
    val trackObj = songDatabaseMock.getTrackById(track.trackId)
    System.out.print(track.name)
    verify(songDatabaseMock).getTrackById(track.trackId).eq(trackObj)
  }

  @Test
  def testMockitoAlbums: Unit = {

    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseMock, albumControllerMock)
    when(songDatabaseMock.getAlbumsByGenre(1)).thenReturn(List(album))
    when(songDatabaseMock.getAlbumsByGenre(2)).thenThrow(new NoSuchElementException)
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    val albums = songDatabaseMock.getAlbumsByGenre(1);

    verify(songDatabaseMock).getAlbumsByGenre(1).eq(albums)
  }

  @Test
  def testMockitoCatchException1: Unit = {
    when(songDatabaseMock.getAlbumsByGenre(2)).thenThrow(new NoSuchElementException)
    //intercept[NoSuchElementException] {songDatabaseMock.getAlbumsByGenre(2)}
    try {
      songDatabaseMock.getAlbumsByGenre(2)
    } catch {
      case e: NoSuchElementException => {
        println("jak ocekvana")
      }
    }
  }

  @Test
  def testMockitoCatchException2: Unit = {
    when(songDatabaseMock.getAlbumsByGenre(2)).thenThrow(new NoSuchElementException)

    the[NoSuchElementException] thrownBy {
      songDatabaseMock.getAlbumsByGenre(2)
    }

  }

}
