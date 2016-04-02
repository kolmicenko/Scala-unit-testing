package cz.dp2.scalacheck

import java.util.NoSuchElementException

import cz.dp2._
import org.joda.time.format.DateTimeFormatterBuilder.Fraction
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.prop.Tables.Table

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
object ScalaCheckTestClass extends Properties("String") with MockitoSugar with ShouldMatchers {

  var genre: Genre = _
  var artist: Artist = _
  var track: Track = _
  var album: Album = _

  val albumControllerMock = mock[AlbumController]
  val songDatabaseMock = mock[SongDatabase]

  val vectGen:Gen[Genre] = for {
    x <- Gen.choose(1, 1000)
    y <- Arbitrary.arbitrary[String]
  } yield Genre(x,y)

  property("startsWith") = forAll { (a: String, b: String) =>
    (a+b).startsWith(a)
  }

  property("ID and name are equal") = forAll (vectGen) { (genre: Genre) =>
    //genre = new Genre(a, b)
    println(genre.id)
    artist = new Artist("Jan", "Zpevak", Countries.Czech_Republic, Nil)
    track = new Track("1", "pisen", artist)
    album = new Album("albumPlnePisni", 2013, artist, genre)

    // run system under test
    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseMock, albumControllerMock)
    when(songDatabaseMock.getAlbumsByGenre(genre.id)).thenReturn(List(album))
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    //var albums = List(album)
    val albums = songDatabaseMock.getAlbumsByGenre(genre.id);

    albums.head.genre.id == genre.id && albums.head.genre.name == genre.name
  }

  property("") = forAll { (a: Int, b: String) =>
    //println(a)
    genre = new Genre(a, b)
    artist = new Artist("Jan", "Zpevak", Countries.Germany, Nil)
    track = new Track("1", "pisen", artist)
    album = new Album("albumPlnePisni", 2013, artist, genre)

    // run system under test
    albumControllerMock.addTrackToAlbum(track, album)
    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseMock, albumControllerMock)
    when(songDatabaseMock.getAlbumsByGenre(a)).thenReturn(List(album))
//    when(songDatabaseMock.getAlbumsByGenre(a).head.genre.name == "").thenThrow(UnsupportedOperationException)
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    //var albums = Seq[Album]
    val albums = songDatabaseMock.getAlbumsByGenre(a);

    albums.head.genre.id == a && albums.head.genre.name == b
  }

  property("") = forAll { (a: Int, b: String) =>
    genre = new Genre(a, b)
    artist = new Artist("Jan", "Zpevak", Countries.Germany, Nil)
    track = new Track("1", "pisen", artist)
    album = new Album("albumPlnePisni", 2013, artist, genre)

    // run system under test
    val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseMock, albumControllerMock)
    when(songDatabaseMock.getAlbumsByGenre(a)).thenReturn(List(album))
    albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
    var albums = List(album)
    albums = songDatabaseMock.getAlbumsByGenre(a);

    albums.head.genre.id == a && albums.head.genre.name == b
  }

}
