package foo.bar.todo.perf

import foo.bar.todo.Task
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.LI

final case class PerfDemoItem(
  task: Task
) {
  def apply(): Unmounted[_, _, _] = PerfDemoItem.component.withKey(s"${PerfDemoItem.ComponentName}-task-${task.id}")(this)
}

object PerfDemoItem {

  private final val ComponentName = getClass.getSimpleName

  private case class Backend(scope: BackendScope[PerfDemoItem, _]) {

    def render(props: PerfDemoItem): TagOf[LI] = {
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

  private val component = ScalaComponent.builder[PerfDemoItem](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}

