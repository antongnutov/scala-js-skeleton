package client

import client.page.Layout
import org.scalajs.dom.{Event, window}

import scala.scalajs.js.JSApp

/**
  * @author Anton Gnutov
  */
object Application extends JSApp {

  private def onPageLoaded(e: Event) = {
    val layout = Layout()

    Router.init(Map(
      ""       -> (_ => layout.selectHome()),
      "#home"  -> (_ => layout.selectHome()),
      "#about" -> (_ => layout.selectAbout())
    ))
  }

  override def main(): Unit = {
    window.onload = onPageLoaded _
  }
}
