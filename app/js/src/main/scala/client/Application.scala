package client

import client.page.StartPage

import scala.scalajs.js.JSApp

/**
  * @author Anton Gnutov
  */
object Application extends JSApp {

  @scala.scalajs.js.annotation.JSExport
  override def main(): Unit = {
    StartPage()
  }
}
