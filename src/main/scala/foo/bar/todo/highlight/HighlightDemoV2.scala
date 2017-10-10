package foo.bar.todo.highlight

import foo.bar.todo.{Task, TaskUtils}
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class HighlightDemoV2() {
  def apply(): Unmounted[_, _, _] = HighlightDemoV2.component(this)
}

object HighlightDemoV2 {

  private final val ComponentName = getClass.getSimpleName

  private case class State(tasks: List[Task], expanded: Map[Int, Boolean] = Map.empty)

  private case class Backend(scope: BackendScope[HighlightDemoV2, State]) {

    private def toggle(task: Task) = {
      scope.modState { state =>
        val currentExpanded = state.expanded
        val expanded = if (currentExpanded.contains(task.id)) {
          currentExpanded.updated(task.id, !currentExpanded(task.id))
        } else {
          currentExpanded.updated(task.id, false)
        }
        state.copy(expanded = expanded)
      }
    }

    def render(state: State): TagOf[Div] = {
      <.div(
        <.ul(^.cls := "list pl0 mv2",
          state.tasks.toVdomArray { task =>
            HighlightItemV2(
              task,
              !state.expanded.contains(task.id) || (state.expanded.contains(task.id) && state.expanded(task.id)),
              toggle
            )()
          }
        )
      )
    }
  }

  private val component = ScalaComponent.builder[HighlightDemoV2](ComponentName)
    .initialState {
      State(tasks = TaskUtils.generateTasks())
    }
    .renderBackend[Backend]
    .build
}
