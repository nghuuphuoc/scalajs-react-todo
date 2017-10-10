package foo.bar.todo

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.ScalaFn
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

final case class Index(router: RouterCtl[Page]) {
  def apply(): ScalaFn.Unmounted[_] = Index.component(this)
}

object Index {

  private def render(props: Index) = {
    <.ul(^.cls := "list pl0",
      <.li(^.cls := "mv2", props.router.link(HighlightDemoPage)("React DevTool Highlight")),
      <.li(^.cls := "mv2", props.router.link(DidUpdatePage)("Why Did You Update?")),
      <.li(^.cls := "mv2", props.router.link(PerfPage)("React Perf Addon"))
    )
  }

  private val component = ScalaFnComponent[Index](render)
}
