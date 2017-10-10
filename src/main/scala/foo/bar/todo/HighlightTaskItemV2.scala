package foo.bar.todo

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.LI

final case class HighlightTaskItemV2(
  task: Task,
  expanded: Boolean,
  onToggle: Task => Callback
) {
  def apply(): Unmounted[_, _, _] = HighlightTaskItemV2.component.withKey(s"${HighlightTaskItemV2.ComponentName}-task-${task.id}")(this)
}

object HighlightTaskItemV2 {

  private final val ComponentName = getClass.getSimpleName

  private case class Backend(scope: BackendScope[HighlightTaskItemV2, _]) {

    def render(props: HighlightTaskItemV2): TagOf[LI] = {
      val task = props.task

      <.li(
        ^.cls := "flex items-center mb2",
        ^.key := s"$ComponentName-task-${task.id}",
        <.span(^.cls := "mr1", s"#${task.id}: "),
        <.a(
          ^.classSet(
            "link blue pointer lh-copy" -> true,
            "truncate" -> props.expanded
          ),
          ^.onClick --> props.onToggle(task),
          task.title
        )
      )
    }
  }

  private val component = ScalaComponent.builder[HighlightTaskItemV2](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}

