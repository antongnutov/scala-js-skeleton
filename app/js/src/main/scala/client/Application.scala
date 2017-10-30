package client

import client.page.Layout
import org.scalajs.dom.{Event, window}

/**
  * @author Anton Gnutov
  */
object Application {

  private def onPageLoaded(e: Event): Unit = {
    val layout = Layout()

    Router.init(Map(
      ""       -> (_ => layout.selectHome()),
      "#home"  -> (_ => layout.selectHome()),
      "#about" -> (_ => layout.selectAbout())
    ))
  }

  def main(args: Array[String]): Unit = {
    window.onload = onPageLoaded _
  }
}
