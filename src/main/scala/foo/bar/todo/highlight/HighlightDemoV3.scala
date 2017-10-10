package foo.bar.todo.highlight

import foo.bar.todo.{Task, TaskUtils}
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class HighlightDemoV3() {
  def apply(): Unmounted[_, _, _] = HighlightDemoV3.component(this)
}

object HighlightDemoV3 {

  private final val ComponentName = getClass.getSimpleName

  private case class State(tasks: List[Task], expanded: Map[Int, Boolean] = Map.empty)

  private case class Backend(scope: BackendScope[HighlightDemoV3, State]) {

    def render(state: State): TagOf[Div] = {
      <.div(
        <.ul(^.cls := "list pl0 mv2",
          state.tasks.toVdomArray { task =>
            HighlightItemV3(task)()
          }
        )
      )
    }
  }

  private val component = ScalaComponent.builder[HighlightDemoV3](ComponentName)
    .initialState {
      State(tasks = TaskUtils.generateTasks())
    }
    .renderBackend[Backend]
    .build
}
