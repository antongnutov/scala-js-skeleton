package client.page

import client.component.Menu.MenuItem
import client.component.{About, Jokes, Menu}
import org.scalajs.dom.html.Element
import org.scalajs.dom.{Node, document}

import scalatags.JsDom.all._

/**
  * @author Anton Gnutov
  */
class Layout {
  private val about = new About("Scala.js", "Some text about Scala.js").render
  private val jokes = new Jokes().render

  val menuItems = Seq(
    MenuItem("Home", "#home"),
    MenuItem("Pure.css", "http://purecss.io"),
    MenuItem("About", "#about")
  )
  val menu = new Menu("Scala.js skeleton", menuItems)

  bodyAppend(menu.render)
  bodyAppend(
    div(cls := "splash-container",
      div(id := "container", cls := "splash")
    )
  )

  def select(component: Element): Unit = {
    val container = document.getElementById("container")
    container.innerHTML = ""
    container.appendChild(component.render)
  }

  def bodyAppend(tagToAppend: HtmlTag): Node = bodyAppend(tagToAppend.render)

  def bodyAppend(nodeToAppend: Node): Node = document.body.appendChild(nodeToAppend)

  def selectHome(): Unit = {
    menu.routeChanged()
    select(jokes)
  }

  def selectAbout(): Unit = {
    menu.routeChanged()
    select(about)
  }
}

object Layout {
  def apply(): Layout = new Layout()
}