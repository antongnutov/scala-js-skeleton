package server

import scala.util.Random

/**
  * @author Anton Gnutov 
  */
trait JokesGenerator {
  val jokes: IndexedSeq[String] = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/jokes.txt")).getLines().toIndexedSeq

  def getJoke: String = jokes(Random.nextInt(jokes.size))
}
