package foo.bar.todo

import foo.bar.todo.facades.WindowConsole
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.LI

final case class DidUpdateItemV2(
  task: Task,
  onChange: Task => Callback
) {
  def apply(): Unmounted[_, _, _] = DidUpdateItemV2.component.withKey(s"${DidUpdateItemV2.ComponentName}-task-${task.id}")(this)
}

object DidUpdateItemV2 {

  private final val ComponentName = getClass.getSimpleName

  private case class Backend(scope: BackendScope[DidUpdateItemV2, _]) {

    def render(props: DidUpdateItemV2): TagOf[LI] = {
      val task = props.task
      <.li(^.cls := "flex items-center mb2",
        <.input(
          ^.cls := "mr2",
          ^.tpe := "checkbox",
          ^.id := s"task-${task.id}",
          ^.checked := task.done,
          ^.onChange ==> { (e: ReactEventFromInput) =>
            e.extract(_.target.checked) { checked =>
              props.onChange(props.task.copy(done = checked))
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

  private val component = ScalaComponent.builder[DidUpdateItemV2](ComponentName)
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
    .shouldComponentUpdate { scope =>
      CallbackTo(scope.currentProps.task != scope.nextProps.task)
    }
    .build
}
