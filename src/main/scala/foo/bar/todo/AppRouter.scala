package foo.bar.todo

import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Resolution, RouterConfigDsl, RouterCtl}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object AppRouter {

  sealed trait AppPage
  case object Home extends AppPage

  val config = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._

    (trimSlashes
      | staticRoute(root, Home) ~> render(HomePage()())
      )
      .notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(layout)
  }

  def layout(c: RouterCtl[AppPage], r: Resolution[AppPage]) = {
    <.div(
      r.render()
    )
  }

  val baseUrl = dom.window.location.hostname match {
    case "localhost" | "127.0.0.1" | "0.0.0.0" =>
      BaseUrl.fromWindowUrl(_.takeWhile(_ != '#'))
    case _ =>
      BaseUrl.fromWindowOrigin / "todo/"
  }
}
