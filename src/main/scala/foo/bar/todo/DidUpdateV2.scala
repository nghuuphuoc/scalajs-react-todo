package foo.bar.todo

import scala.util.Random

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.UList

final case class DidUpdateV2() {
  def apply(): Unmounted[_, _, _] = DidUpdateV2.component(this)
}

object DidUpdateV2 {

  private final val ComponentName = getClass.getSimpleName

  private final val InitialNumberOfTasks = 1000

  private case class State(tasks: List[Task])

  private case class Backend(scope: BackendScope[DidUpdateV2, State]) {

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

  private val component = ScalaComponent.builder[DidUpdateV2](ComponentName)
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
