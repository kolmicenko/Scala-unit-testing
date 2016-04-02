package cz.dp

/**
  * Created by kolmicenko on 30. 1. 2016.
  */
object BruceSpringsteenStatistics {
  def numberOfAlbums = BruceSpringsteenFactory.discography.size

  def averageYear = BruceSpringsteenFactory.discography.map(_.year).sum / numberOfAlbums
}
