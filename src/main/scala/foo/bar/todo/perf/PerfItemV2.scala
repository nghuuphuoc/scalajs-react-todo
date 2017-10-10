package foo.bar.todo.perf

import foo.bar.todo.Task
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.LI

final case class PerfItemV2(
  task: Task
) {
  def apply(): Unmounted[_, _, _] = PerfItemV2.component.withKey(s"${PerfItemV2.ComponentName}-task-${task.id}")(this)
}

object PerfItemV2 {

  private final val ComponentName = getClass.getSimpleName

  private case class Backend(scope: BackendScope[PerfItemV2, _]) {

    def render(props: PerfItemV2): TagOf[LI] = {
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

  private val component = ScalaComponent.builder[PerfItemV2](ComponentName)
    .stateless
    .renderBackend[Backend]
    .shouldComponentUpdate { scope =>
      CallbackTo(scope.currentProps.task != scope.nextProps.task)
    }
    .build
}
