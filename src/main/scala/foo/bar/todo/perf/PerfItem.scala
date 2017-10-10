package foo.bar.todo.perf

import foo.bar.todo.Task
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.LI

final case class PerfItem(
  task: Task
) {
  def apply(): Unmounted[_, _, _] = PerfItem.component.withKey(s"${PerfItem.ComponentName}-task-${task.id}")(this)
}

object PerfItem {

  private final val ComponentName = getClass.getSimpleName

  private case class Backend(scope: BackendScope[PerfItem, _]) {

    def render(props: PerfItem): TagOf[LI] = {
      val task = props.task
      <.li(^.cls := "flex items-center mb2",
        <.input(
          ^.cls := "mr2",
          ^.tpe := "checkbox",
          VdomAttr("defaultChecked") := task.done
        ),
        <.label(task.title)
      )
    }
  }

  private val component = ScalaComponent.builder[PerfItem](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}

