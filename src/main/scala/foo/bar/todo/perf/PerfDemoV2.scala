package foo.bar.todo.perf

import scala.util.Random

import foo.bar.todo.facades.ReactPerf
import foo.bar.todo.Task
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class PerfDemoV2() {
  def apply(): Unmounted[_, _, _] = PerfDemoV2.component(this)
}

object PerfDemoV2 {

  private final val ComponentName = getClass.getSimpleName

  private final val InitialNumberOfTasks = 1000

  private case class State(tasks: List[Task], newTask: String = "")

  private case class Backend(scope: BackendScope[PerfDemoV2, State]) {

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
            PerfDemoItemV2(task)()
          }
        )
      )
    }
  }

  private val component = ScalaComponent.builder[PerfDemoV2](ComponentName)
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

