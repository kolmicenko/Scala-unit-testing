package cz.dp2

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
import org.joda.time.format.PeriodFormatterBuilder

class Track(val trackId: String, val name: String, val artist: Artist, val length: String) {

  require(name.trim().length() != 0, "Track name cannot be blank")

  def this(trackId: String, name: String, artist: Artist) = this(trackId, name, artist, "0:00")

  def period = {
    val fmt = new PeriodFormatterBuilder()
      .printZeroAlways()
      .appendMinutes()
      .appendSeparator(":")
      .printZeroAlways()
      .appendSeconds()
      .toFormatter
    fmt.parsePeriod(length)
  }
}
