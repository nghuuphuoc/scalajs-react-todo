package foo.bar.todo.didupdate

import foo.bar.todo.Task
import foo.bar.todo.facades.WindowConsole
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.LI

final case class DidUpdateItem(
  task: Task,
  onMarkDone: Task => Callback
) {
  def apply(): Unmounted[_, _, _] = DidUpdateItem.component.withKey(s"${DidUpdateItem.ComponentName}-task-${task.id}")(this)
}

object DidUpdateItem {

  private final val ComponentName = getClass.getSimpleName

  private case class Backend(scope: BackendScope[DidUpdateItem, _]) {

    def render(props: DidUpdateItem): TagOf[LI] = {
      val task = props.task
      <.li(^.cls := "flex items-center mb2",
        <.input(
          ^.cls := "mr2",
          ^.tpe := "checkbox",
          ^.id := s"task-${task.id}",
          ^.checked := task.done,
          ^.onChange ==> { (e: ReactEventFromInput) =>
            e.extract(_.target.checked) { checked =>
              props.onMarkDone(props.task.copy(done = checked))
            }
          }
        ),
        <.label(
          ^.cls := "lh-copy",
          ^.htmlFor := s"task-${task.id}",
          task.title
        )
      )
    }
  }

  private val component = ScalaComponent.builder[DidUpdateItem](ComponentName)
    .stateless
    .renderBackend[Backend]
    .componentDidUpdate { scope =>
      Callback {
        WindowConsole.group(ComponentName)
        if (scope.prevProps.task == scope.currentProps.task) {
          WindowConsole.warn("Value did not change. Avoidable re-render")
        }
        WindowConsole.log(s"Before: ${scope.prevProps.task}")
        WindowConsole.log(s"After: ${scope.currentProps.task}")
        WindowConsole.groupEnd()
      }
    }
    .build
}

