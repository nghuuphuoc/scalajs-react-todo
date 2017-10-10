package foo.bar.todo

import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Resolution, RouterConfigDsl, RouterCtl}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object AppRouter {

  final val DidUpdateUrl = "#did-update"

  val config = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._

    (trimSlashes
      | staticRoute(root, IndexPage) ~> renderR(router => Index(router)())
      | staticRoute("#highlight", HighlightDemoPage) ~> render(HighlightDemo()())
      | staticRoute("#highlight-v2", HighlightDemoPageV2) ~> render(HighlightDemoV2()())
      | staticRoute("#highlight-v3", HighlightDemoPageV3) ~> render(HighlightDemoV3()())
      | staticRoute(DidUpdateUrl, DidUpdatePage) ~> render(DidUpdate()())
      )
      .notFound(redirectToPage(IndexPage)(Redirect.Replace))
      .renderWith(layout)
  }

  def layout(c: RouterCtl[Page], r: Resolution[Page]) = {
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
