package foo.bar.todo.perf

import foo.bar.todo.facades.ReactPerf
import foo.bar.todo.{Task, TaskUtils}
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class PerfDemo() {
  def apply(): Unmounted[_, _, _] = PerfDemo.component(this)
}

object PerfDemo {

  private final val ComponentName = getClass.getSimpleName

  private case class State(tasks: List[Task], newTask: String = "")

  private case class Backend(scope: BackendScope[PerfDemo, State]) {

    private def addNewTask = {
      ReactPerf.start()
      scope.modState { state =>
        val newId = state.tasks.length + 1
        val tasks = Task(newId, state.newTask) :: state.tasks
        state.copy(tasks = tasks)
      }
    }

    def render(state: State): TagOf[Div] = {
      <.div(
        <.div(
          ^.cls := "flex items-center",
          <.input(
            ^.cls := "ba b--near-gray pa2 w-100",
            ^.tpe := "text",
            ^.onChange ==> { (e: ReactEventFromInput) =>
              e.extract(_.target.value) { value =>
                ReactPerf.start()
                scope.modState(_.copy(newTask = value))
              }
            }
          ),
          <.button(
            ^.cls := "ba white bg-blue pa2",
            ^.onClick --> addNewTask,
            "Add new task"
          )
        ),

        <.ul(^.cls := "list pl0 mv2",
          state.tasks.toVdomArray { task =>
            PerfItem(task)()
          }
        )
      )
    }
  }

  private val component = ScalaComponent.builder[PerfDemo](ComponentName)
    .initialState {
      State(tasks = TaskUtils.generateTasks())
    }
    .renderBackend[Backend]
    .componentDidUpdate { _ =>
      Callback {
        ReactPerf.stop()

        val measurements = ReactPerf.getLastMeasurements()
        ReactPerf.printInclusive()
        ReactPerf.printWasted(measurements)
      }
    }
    .build
}

