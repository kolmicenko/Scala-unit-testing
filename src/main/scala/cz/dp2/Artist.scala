package cz.dp2

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
import cz.dp2.Countries.{Value => Country}

class Artist(val firstName: String, val lastName: String, val country: Country,  val albums: List[Album]) {

  def this(firstName: String, lastName: String, country: Country) = this(firstName, lastName,country, Nil)

  def getAlbums = albums

  def addAlbum(album: Album) = new Artist(firstName, lastName,country, album :: albums)

  def fullName = firstName + " " + lastName

  override def hashCode = 123 * (123 + firstName.hashCode) + lastName.hashCode

  override def equals(other: Any) = other match {
    case that: Artist => this.firstName == that.firstName && this.lastName == that.lastName
    case _ => false
  }

  override def toString = "Artist[%s, %s]".format(firstName, lastName)
}
