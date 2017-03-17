package client.component

import client.component.Menu.MenuItem
import org.scalajs.dom.Event
import org.scalajs.dom.html.{Anchor, Element}

import scalatags.JsDom.all._

/**
  * @author Anton Gnutov
  */
class Menu(brand: String, items: Seq[MenuItem]) {

  private var selectedItem: Option[Element] = None

  private val links: Seq[Anchor] = items.map { item =>
      val link = a(href := item.url, cls := "pure-menu-link")(item.label).render
      link.onclick = (e: Event) => {
        selectedItem.foreach(_.setAttribute("class", "pure-menu-item"))
        selectedItem = Some(link.parentElement)
        selectedItem.foreach(_.setAttribute("class", "pure-menu-item pure-menu-selected"))
        item.action(e)
      }
      link
  }

  def render: Element = {
    val listItems = links.map(anchor => li(cls := "pure-menu-item", anchor).render)
    if (listItems.nonEmpty) {
      selectedItem = Some(listItems.head)
      selectedItem.foreach(_.setAttribute("class", "pure-menu-item pure-menu-selected"))
    }

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
  type MenuAction = Event => Unit

  val NoAction: MenuAction = (_: Event) => ()

  case class MenuItem(label: String, url: String, action: MenuAction)
}