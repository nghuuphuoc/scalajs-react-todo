package foo.bar.todo.didupdate

import foo.bar.todo.{Task, TaskUtils}
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.UList

final case class DidUpdateDemoV2() {
  def apply(): Unmounted[_, _, _] = DidUpdateDemoV2.component(this)
}

object DidUpdateDemoV2 {

  private final val ComponentName = getClass.getSimpleName

  private case class State(tasks: List[Task])

  private case class Backend(scope: BackendScope[DidUpdateDemoV2, State]) {

    private def onChange(task: Task) = {
      scope.modState { state =>
        val index = state.tasks.indexWhere(_.id == task.id)
        if (index == -1) {
          state
        } else {
          state.copy(tasks = state.tasks.updated(index, task))
        }
      }
    }

    def render(state: State): TagOf[UList] = {
      <.ul(^.cls := "list pl0 mv2",
        state.tasks.toVdomArray { task =>
          DidUpdateItemV2(task, onChange)()
        }
      )
    }
  }

  private val component = ScalaComponent.builder[DidUpdateDemoV2](ComponentName)
    .initialState {
      State(tasks = TaskUtils.generateTasks())
    }
    .renderBackend[Backend]
    .build
}
