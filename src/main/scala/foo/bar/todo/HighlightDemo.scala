package foo.bar.todo

import scala.util.Random

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class HighlightDemo() {
  def apply(): Unmounted[_, _, _] = HighlightDemo.component(this)
}

object HighlightDemo {

  private final val ComponentName = getClass.getSimpleName

  private final val InitialNumberOfTasks = 1000

  private case class State(tasks: List[Task], expanded: Map[Int, Boolean] = Map.empty)

  private case class Backend(scope: BackendScope[HighlightDemo, State]) {

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
            <.li(
              ^.cls := "flex items-center mb2",
              ^.key := s"$ComponentName-task-${task.id}",
              <.span(^.cls := "mr1", s"#${task.id}: "),
              <.a(
                ^.classSet(
                  "link blue pointer lh-copy" -> true,
                  "truncate" -> (!state.expanded.contains(task.id) || (state.expanded.contains(task.id) && state.expanded(task.id))),
                ),
                ^.onClick --> toggle(task),
                task.title
              )
            )
          }
        )
      )
    }
  }

  private val component = ScalaComponent.builder[HighlightDemo](ComponentName)
    .initialState {
      val random = new Random
      val tasks = 1.to(InitialNumberOfTasks).map { index =>
        val done = random.nextInt(100) % 2 == 0

        // Generate the task title
        val title = 0.to(random.nextInt(10)).map(_ => s"Task $index").mkString(" ")
        Task(index, title, done)
      }.toList
      State(tasks = tasks)
    }
    .renderBackend[Backend]
    .build
}
