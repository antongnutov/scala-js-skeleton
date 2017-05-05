package client.component

import client.component.Menu.MenuItem
import org.scalajs.dom.html.{Anchor, Element}
import org.scalajs.dom.window

import scalatags.JsDom.all._

/**
  * @author Anton Gnutov
  */
class Menu(brand: String, items: Seq[MenuItem]) {

  private var selectedItem: Option[Element] = None

  private val links: Seq[Anchor] = items.map(item => a(href := item.url, cls := "pure-menu-link")(item.label).render)

  private def select(anchor: Anchor): Unit = {
    selectedItem.foreach(_.setAttribute("class", "pure-menu-item"))
    selectedItem = Some(anchor.parentElement)
    selectedItem.foreach(_.setAttribute("class", "pure-menu-item pure-menu-selected"))
  }

  def routeChanged(): Unit = {
    if (window.location.hash.isEmpty && links.nonEmpty) {
      select(links.head)
    } else {
      links.find(_.hash == window.location.hash).foreach(select)
    }
  }

  def render: Element = {
    val listItems = links.map(anchor => li(cls := "pure-menu-item", anchor).render)

    div(cls := "header",
      div(cls := "home-menu pure-menu pure-menu-horizontal pure-menu-fixed",
        a(cls := "pure-menu-heading", href := "")(brand),

        ul(cls := "pure-menu-list",
          listItems
        )
      )
    ).render
  }
}

object Menu {
  case class MenuItem(label: String, url: String)
}