package client.page

import client.component.{About, Jokes, Menu}
import client.component.Menu.MenuItem
import org.scalajs.dom.html.Element
import org.scalajs.dom.{Event, Node, document, window}

import scalatags.JsDom.all._

/**
  * @author Anton Gnutov
  */
class StartPage {
  window.onload = onPageLoaded _

  private def onPageLoaded(e: Event) = {
    val about = new About("Scala.js", "Some text about Scala.js").render
    val jokes = new Jokes().render

    val menuItems = Seq(
      MenuItem("Home", "#", (_: Event) => select(jokes)),
      MenuItem("Pure.css", "http://purecss.io", Menu.NoAction),
      MenuItem("About", "#", (_: Event) => select(about))
    )
    val menu = new Menu("Scala.js skeleton", menuItems)

    bodyAppend(menu.render)
    bodyAppend(
      div(cls := "splash-container",
        div(id := "container", cls := "splash")
      )
    )

    select(jokes)
  }

  def select(component: Element): Unit = {
    val container = document.getElementById("container")
    container.innerHTML = ""
    container.appendChild(component.render)
  }

  def bodyAppend(tagToAppend: HtmlTag): Node = bodyAppend(tagToAppend.render)

  def bodyAppend(nodeToAppend: Node): Node = document.body.appendChild(nodeToAppend)

}

object StartPage {
  def apply(): StartPage = new StartPage()
}