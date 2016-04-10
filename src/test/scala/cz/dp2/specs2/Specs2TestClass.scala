package cz.dp2.specs2

import java.util.NoSuchElementException

import cz.dp2._
import org.scalamock.specs2.IsolatedMockFactory
import org.specs2.mutable.Specification

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
class Specs2TestClass extends Specification with IsolatedMockFactory {
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
  songDatabaseStub.getTrackById _ when "1" returns track
  val albumsReturn = List(album)
  songDatabaseStub.getAlbumsByGenre _ when 1 returns albumsReturn
  //(songDatabaseStub.getAlbumsByGenre _).expects(2).throwing(new NoSuchElementException)
  val albumMatcherObserver = new AlbumMatcherObserver(songDatabaseStub, albumControllerMock)
  albumMatcherObserver.recordMatchResult(AlbumMatcher(track, album))
  var albums = songDatabaseStub.getAlbumsByGenre(genre.id)

  "Počet alb" >> {
    "by měl být 1" >> {
      albums.size must_== 1
    }
  }

  "Žánr na albu" >> {
    "by měl být rock" >> {
      albums.head.genre.name.eq("Rock")
    }
  }

  "První píseň na albu" >> {
    "by se měla jmenovat pisen 1" >> {
      albums.head.tracks.get.head.name.eq("pisen 1")
    }
  }

  "this example fails for now" in {
    albums.size must_== 2
  }.pendingUntilFixed("Počet alb by neměl být roven 2")

}
