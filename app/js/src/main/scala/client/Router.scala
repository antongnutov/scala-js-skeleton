package client

import org.scalajs.dom._

/**
  * @author Anton Gnutov
  */
object Router {
  type Action = Unit => Unit

  window.onhashchange = (_: HashChangeEvent) => routeChanged()

  private def routeChanged() = {
    val route = window.location.hash
    subscriptions.get(route) match {
      case Some(action) => action(())
      case None =>
    }
  }

  private var subscriptions: Map[String, Action] = Map.empty

  def init(routeMap: Map[String, Action]): Unit = synchronized {
    subscriptions = routeMap
    routeChanged()
  }
}