package cz.dp2.scalatest

import cz.dp2._
import org.scalamock.scalatest.MockFactory
import org.scalatest.FreeSpec
import org.scalatest.exceptions.TestFailedException
import scodec.bits.Bases.Alphabets.Base64

/**
  * Created by kolmicenko on 20. 3. 2016.
  */
class ScalaTestTestClassFreeSpec extends FreeSpec with MockFactory{

  var genre: Genre = _
  var artist: Artist = _
  var track: Track = _
  var track2: Track = _
  var album: Album = _

  val albumControllerMock = mock[AlbumController]
  val songDatabaseStub = stub[SongDatabase]

  genre = new Genre(1, "Rock")

  artist = new Artist("Jan", "Zpevak", Countries.Germany, Nil)

  track = new Track("1", "pisen 1", artist)
  track2 = new Track("2", "pisen 2", artist)
  val tracks = Option(List(track, track2))
  album = new Album("albumPlnePisni", 2013, artist, genre, tracks)

  (albumControllerMock.addTrackToAlbum _).expects(track, album)
  // configure stubs
  (songDatabaseStub.getTrackById _) when "1" returns track
  val albumsReturn = List(album)
  (songDatabaseStub.getAlbumsByGenre _) when 1 returns albumsReturn
  val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseStub, albumControllerMock)
  albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
  val albums = songDatabaseStub.getAlbumsByGenre(genre.id)

  "Počet alb" - {
    "by měl být 1" in {
      assert(albums.size == 1)
      assertResult(1) {albums.size}
    }
  }

  "Žánr na albu" - {
    "by měl být rock" in {
      assert(albums.head.genre.name.eq("Rock"))
      assertResult("Rock") {albums.head.genre.name}
    }
  }

  "První píseň na albu" - {
    "by se měla jmenovat pisen 1" in {
      assert(albums.head.tracks.get.head.name.eq("pisen 1"))
    }
  }

  "Nesprávné porovnání" - {
    "vyhodí výjimku" in {
      intercept[TestFailedException] {
        assert(albums.size == 2)
      }
    }
  }

  "Nesprávné porovnání" - {
    "vyhodí výjimku s upresnenim" in {
      withClue("this is a clue") {
        intercept[TestFailedException] {
          assert(albums.size == 2)
        }
      }
    }
  }

  "Ošetřené volání metody fail" - {
    "neskončí chybou" in {
      try {
        assert(albums.size == 2)
        fail("chyba v metode")
      }
      catch {
        case _: TestFailedException => // Expected, so continue
      }
    }
  }

  "Kontrola, že kolekce není prázdná" - {
    "neskončí chybou" in {
      assume(!albums.isEmpty, "Kolekce albums obsahuje alespoň jeden prvek")
    }
  }

  "Kontrola, že kolekce je prázdná" - {
    "skončí chybou" in {
      assume(albums.isEmpty, "Kolekce nečekaně obsahuje minimálně jeden prvek")
    }
  }

   "Tato metoda" - {
    "throw Exception" ignore  {
      intercept[TestFailedException] {
        assert(albums.size == 2)
      }
    }
  }
}
