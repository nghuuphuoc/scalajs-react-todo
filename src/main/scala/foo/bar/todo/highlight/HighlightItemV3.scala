package foo.bar.todo.highlight

import foo.bar.todo.Task
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.LI

final case class HighlightItemV3(
  task: Task
) {
  def apply(): Unmounted[_, _, _] = HighlightItemV3.component.withKey(s"${HighlightItemV3.ComponentName}-task-${task.id}")(this)
}

object HighlightItemV3 {

  private final val ComponentName = getClass.getSimpleName

  private case class State(expanded: Boolean)

  private case class Backend(scope: BackendScope[HighlightItemV3, State]) {

    def render(props: HighlightItemV3, state: State): TagOf[LI] = {
      val task = props.task

      <.li(
        ^.cls := "flex items-center mb2",
        <.span(^.cls := "mr1", s"#${task.id}: "),
        <.a(
          ^.classSet(
            "link blue pointer lh-copy" -> true,
            "truncate" -> state.expanded
          ),
          ^.onClick --> scope.modState(_.copy(expanded = !state.expanded)),
          task.title
        )
      )
    }
  }

  private val component = ScalaComponent.builder[HighlightItemV3](ComponentName)
    .initialState(State(expanded = true))
    .renderBackend[Backend]
    .build
}
