package foo.bar.todo

import japgolly.scalajs.react.ScalaFnComponent
import japgolly.scalajs.react.component.ScalaFn
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

final case class Breadcrumb(router: RouterCtl[Page], page: Page) {
  def apply(): ScalaFn.Unmounted[_] = Breadcrumb.component(this)
}

object Breadcrumb {

  private def render(props: Breadcrumb) = {
    props.page match {
      case HighlightDemoPage | HighlightDemoPageV2 | HighlightDemoPageV3 =>
        <.div(^.cls := "flex items-center mv3",
          props.router.link(IndexPage)(^.cls := "pr2", "Demo"),
          <.span(^.cls := "pr2", " / "),
          <.span(^.cls := "pr2", "React DevTool Highlight: "),
          props.router.link(HighlightDemoPage)(^.cls := "pr2", "V1"),
          props.router.link(HighlightDemoPageV2)(^.cls := "pr2", "V2"),
          props.router.link(HighlightDemoPageV3)(^.cls := "pr2", "V3")
        )

      case DidUpdatePage | DidUpdatePageV2 =>
        <.div(^.cls := "flex items-center mv3",
          props.router.link(IndexPage)(^.cls := "pr2", "Demo"),
          <.span(^.cls := "pr2", " / "),
          <.span(^.cls := "pr2", "Why Did You Update? "),
          props.router.link(DidUpdatePage)(^.cls := "pr2", "V1"),
          props.router.link(DidUpdatePageV2)(^.cls := "pr2", "V2")
        )

      case PerfPage | PerfPageV2 | PerfPageV3 =>
        <.div(^.cls := "flex items-center mv3",
          props.router.link(IndexPage)(^.cls := "pr2", "Demo"),
          <.span(^.cls := "pr2", " / "),
          <.span(^.cls := "pr2", "React Perf Addon: "),
          props.router.link(PerfPage)(^.cls := "pr2", "V1"),
          props.router.link(PerfPageV2)(^.cls := "pr2", "V2"),
          props.router.link(PerfPageV3)(^.cls := "pr2", "V3")
        )

      case TimelinePage | TimelinePageV2 | TimelinePageV3 =>
        <.div(^.cls := "flex items-center mv3",
          props.router.link(IndexPage)(^.cls := "pr2", "Demo"),
          <.span(^.cls := "pr2", " / "),
          <.span(^.cls := "pr2", "Use Chrome Performance Tab: "),
          props.router.link(TimelinePage)(^.cls := "pr2", "V1"),
          props.router.link(TimelinePageV2)(^.cls := "pr2", "V2"),
          props.router.link(TimelinePageV3)(^.cls := "pr2", "V3")
        )

      case IndexPage =>
        <.div(^.cls := "flex items-center mv3",
          props.router.link(IndexPage)("Demo")
        )
    }
  }

  private val component = ScalaFnComponent[Breadcrumb](render)
}

