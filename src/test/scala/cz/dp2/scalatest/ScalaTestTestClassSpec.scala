package cz.dp2.scalatest

import cz.dp2._
import org.scalamock.scalatest.MockFactory
import org.scalatest.exceptions.TestFailedException
import org.scalatest.{Ignore, Spec}

/**
  * Created by kolmicenko on 20. 3. 2016.
  */
class ScalaTestTestClassSpec extends Spec with MockFactory {
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

  object `Počet alb` {
    def `by měl být 1` {
      assert(albums.size == 1)
      assertResult(1) {albums.size}
    }
  }

  object `Žánr na albu` {
    def `by měl být rock` {
        assert(albums.head.genre.name.eq("Rock"))
        assertResult("Rock") {albums.head.genre.name}
    }
  }

  object `První píseň na albu` {
    def `by se měla jmenovat pisen 1` {
      assert(albums.head.tracks.get.head.name.eq("pisen 1"))
    }
  }

  object `Prvni pisen na albu` {
    def `jmenovat pisen 1` {
      assert(albums.head.tracks.get.head.name.eq("pisen 1"))
    }
  }

  object `Nesprávné porovnání` {
    def `vyhodí výjimku` {
      intercept[TestFailedException] {
        assert(albums.size == 2)
      }
    }
  }

  object `Nesprávné porovnání vyhodí` {
    def `výjimku s upresnenim` {
      withClue("this is a clue") {
        intercept[TestFailedException] {
          assert(albums.size == 2)
        }
      }
    }
  }

  object `Ošetřené volání metody assert a fail` {
    def `neskončí chybou`: Unit = {
      try {
        assert(albums.size == 2)
        fail("chyba v metode")
      }
      catch {
        case _: TestFailedException => // Expected, so continue
      }
    }
  }

  object `Kontrola, že kolekce není prázdná` {
    def `neskončí chybou`: Unit = {
      assume(!albums.isEmpty, "Kolekce albums obsahuje alespoň jeden prvek")
    }
  }

  object `Kontrola, že kolekce je prázdná` {
    def `skončí chybou`: Unit = {
      assume(albums.isEmpty, "Kolekce nečekaně obsahuje minimálně jeden prvek")
    }
  }

  object `Porovnání na velikost kolekce albums = 2` {
    @Ignore def `vyhodí výjimku` {
      intercept[TestFailedException] {
        assert(albums.size == 2)
      }
    }
  }

}
