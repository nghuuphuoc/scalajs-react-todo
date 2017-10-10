package foo.bar.todo

import foo.bar.todo.didupdate.{DidUpdateDemo, DidUpdateDemoV2}
import foo.bar.todo.highlight.{HighlightDemo, HighlightDemoV2, HighlightDemoV3}
import foo.bar.todo.perf.{PerfDemo, PerfDemoV2, PerfDemoV3}
import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Resolution, RouterConfigDsl, RouterCtl}
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object AppRouter {

  val config = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._

    (trimSlashes
      | staticRoute(root, IndexPage) ~> renderR(router => Index(router)())
      | staticRoute("#highlight", HighlightDemoPage) ~> render(HighlightDemo()())
      | staticRoute("#highlight-v2", HighlightDemoPageV2) ~> render(HighlightDemoV2()())
      | staticRoute("#highlight-v3", HighlightDemoPageV3) ~> render(HighlightDemoV3()())
      | staticRoute("#did-update", DidUpdatePage) ~> render(DidUpdateDemo()())
      | staticRoute("#did-update-v2", DidUpdatePageV2) ~> render(DidUpdateDemoV2()())
      | staticRoute("#perf", PerfPage) ~> render(PerfDemo()())
      | staticRoute("#perf-v2", PerfPageV2) ~> render(PerfDemoV2()())
      | staticRoute("#perf-v3", PerfPageV3) ~> render(PerfDemoV3()())
      | staticRoute("#timeline?react_perf", PerfPage) ~> render(PerfDemo()())
      | staticRoute("#timeline-v2?react_perf", PerfPageV2) ~> render(PerfDemoV2()())
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
