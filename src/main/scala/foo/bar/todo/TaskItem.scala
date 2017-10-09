package foo.bar.todo

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.{Div, LI}

final case class TaskItem(
  task: Task,
  onChangeStatus: (Task, Boolean) => Callback
) {
  def apply(): Unmounted[_, _, _] = TaskItem.component.withKey(s"${TaskItem.ComponentName}-task-${task.id}")(this)
}

object TaskItem {

  private final val ComponentName = getClass.getSimpleName

  private case class Backend(scope: BackendScope[TaskItem, _]) {

    def render(props: TaskItem): TagOf[LI] = {
      <.li(
        ^.cls := "flex items-center mb2",
        ^.key := s"$ComponentName-task-${props.task.id}",
        <.input(
          ^.cls := "mr2",
          ^.tpe := "checkbox",
          ^.id := s"task-${props.task.id}",
          ^.checked := props.task.done,
          ^.onChange ==> { (e: ReactEventFromInput) =>
            e.extract(_.target.checked) { checked =>
              props.onChangeStatus(props.task, checked)
            }
          }
        ),
        <.label(
          ^.cls := "lh-copy",
          ^.htmlFor := s"task-${props.task.id}",
          props.task.title
        )
      )
    }
  }

  private val component = ScalaComponent.builder[TaskItem](ComponentName)
    .stateless
    .renderBackend[Backend]
    .shouldComponentUpdate { scope =>
      CallbackTo {
        scope.currentProps.task.done != scope.nextProps.task.done
      }
    }
    .build
}
