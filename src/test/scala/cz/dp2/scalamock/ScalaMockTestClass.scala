package cz.dp2.scalamock

import cz.dp2._
import org.junit.{Test, Before}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
class ScalaMockTestClass extends FlatSpec with Matchers with MockFactory {

  var genre: Genre = _
  var artist: Artist = _
  var track: Track = _
  var album: Album = _

  val albumControllerMock = mock[AlbumController]
  val songDatabaseStub = stub[SongDatabase]

  @Before
  def beforeMethod {
    genre = new Genre(1, "Rock")

    artist = new Artist("Jan", "Zpevak", Countries.Germany, Nil)

    track = new Track("1", "pisen", artist)

    album = new Album("albumPlnePisni", 2013, artist, genre)

  }

  @Test
  def testScalaMock: Unit = {

    // set expectations
    (albumControllerMock.addTrackToAlbum _).expects(track, album)

    // configure stubs
    (songDatabaseStub.getTrackById _) when "1" returns track
    val albumsReturn = List(album, album)
    (songDatabaseStub.getAlbumsByGenre _) when 1 returns albumsReturn
    //(userDetailsServiceStub.getTrackById _) when winner.id returns winner

    // run system under test
    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseStub, albumControllerMock)
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    val albums = songDatabaseStub.getAlbumsByGenre(genre.id)

    albums.size shouldBe (2)
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
