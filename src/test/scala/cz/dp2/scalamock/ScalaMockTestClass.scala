package cz.dp2.scalamock

import cz.dp2._
import org.junit.{Before, Test}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
class ScalaMockTestClass extends FlatSpec with Matchers with MockFactory {

  var genre: Genre = _
  var genre2: Genre = _
  var artist: Artist = _
  var track: Track = _
  var album: Album = _
  var albumWithoutGenre: Album = _

  val albumControllerMock = mock[AlbumController]
  val songDatabaseStub = stub[SongDatabase]

  @Before
  def beforeMethod {
    genre = new Genre(1, "Rock")
    genre2 = new Genre(2, "")

    artist = new Artist("Jan", "Zpevak", Countries.Germany, Nil)

    track = new Track("1", "pisen", artist)

    album = new Album("albumPlnePisni", 2013, artist, genre)
    albumWithoutGenre = new Album("Album bez zanru", 2001, artist, genre2)
  }

  @Test
  def testScalaMock: Unit = {

    // set expectations
    (albumControllerMock.addTrackToAlbum _).expects(track, album)
    (albumControllerMock.addTrackToAlbum _).expects(track, albumWithoutGenre).throwing(new RuntimeException("Chyba, žánr není vyplněn."))
    // configure stubs
    (songDatabaseStub.getTrackById _) when "1" returns track
    val albumsReturn = List(album, album)
    (songDatabaseStub.getAlbumsByGenre _) when 1 returns albumsReturn
    //(songDatabaseStub.getAlbumsByGenre _).expects(2).throwing(new NoSuchElementException)
    //(userDetailsServiceStub.getTrackById _) when winner.id returns winner

    // run system under test
    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseStub, albumControllerMock)
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    //albumControllerMock.addTrackToAlbum(track, album)

    val albums = songDatabaseStub.getAlbumsByGenre(genre.id)

    albums.size shouldBe (2)

    intercept[RuntimeException] {albumControllerMock.addTrackToAlbum(track, albumWithoutGenre) }
  }

  @Test
  def scalaMockInSequence: Unit = {

    System.out.print(track)
    inSequence {
      (songDatabaseStub.getTrackById _) when "1" returns track
      (albumControllerMock.addTrackToAlbum _).expects(track, album)
      val albumsReturn = List(album, album)
      (songDatabaseStub.getAlbumsByGenre _) when 1 returns albumsReturn

    }
    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseStub, albumControllerMock)
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    val albums = songDatabaseStub.getAlbumsByGenre(genre.id)

    albums.size shouldBe (2)
  }

  @Test
  def scalaMock: Unit = {
    inAnyOrder {
      val albumsReturn = List(album, album)
      (songDatabaseStub.getAlbumsByGenre _) when 1 returns albumsReturn
      (songDatabaseStub.getTrackById _) when "1" returns track
      (albumControllerMock.addTrackToAlbum _).expects(track, album)
    }
    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseStub, albumControllerMock)
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    val albums = songDatabaseStub.getAlbumsByGenre(genre.id)

    albums.size shouldBe (2)
  }

}
