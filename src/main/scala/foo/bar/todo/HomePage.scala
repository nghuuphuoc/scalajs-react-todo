package foo.bar.todo

import scala.util.Random

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class HomePage() {
  def apply(): Unmounted[_, _, _] = HomePage.component(this)
}

object HomePage {

  private final val ComponentName = getClass.getSimpleName

  private case class State(tasks: List[Task])

  private case class Backend(scope: BackendScope[HomePage, State]) {

    private def onChangeStatus(task: Task, checked: Boolean) = {
      scope.modState { state =>
        val index = state.tasks.indexWhere(_.id == task.id)
        if (index == -1) {
          state
        } else {
          val newTasks = state.tasks.updated(index, state.tasks(index).copy(done = checked))
          state.copy(tasks = newTasks)
        }
      }
    }

    def render(state: State): TagOf[Div] = {
      <.div(
        <.ul(^.cls := "list pl0 mb2",
          state.tasks.toVdomArray { task =>
            TaskItem(task, onChangeStatus)()
          }
        ),

        <.div(^.cls := "flex items-center",
          <.input(
            ^.cls := "ba b--black-20 pa2",
            ^.tpe := "input"
          ),
          <.button(
            ^.cls := "white bg-blue ba b--near-white pa2",
            ^.tpe := "button",
            "Add"
          )
        )
      )
    }
  }

  private val component = ScalaComponent.builder[HomePage](ComponentName)
    .initialState {
      val random = new Random
      val tasks = 1.to(10).map { index =>
        val r = random.nextInt(100 + 1)
        Task(index, s"Task $index", r % 2 == 0)
      }.toList
      State(tasks = tasks)
    }
    .renderBackend[Backend]
    .build
}
