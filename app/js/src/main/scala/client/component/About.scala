package client.component

import org.scalajs.dom.html.Element

import scalatags.JsDom.all._

/**
  * @author Anton Gnutov
  */
class About(header: String, text: String) {
  def render: Element = {
    div(
      div(cls := "splash-head",
        p(header)
      ),
      div(cls := "splash-subhead",
        p(text)
      )
    ).render
  }
}
