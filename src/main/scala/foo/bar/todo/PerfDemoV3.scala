package foo.bar.todo

import scala.util.Random

import foo.bar.todo.facades.ReactPerf
import foo.bar.todo.perf.AddTaskForm
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class PerfDemoV3() {
  def apply(): Unmounted[_, _, _] = PerfDemoV3.component(this)
}

object PerfDemoV3 {

  private final val ComponentName = getClass.getSimpleName

  private final val InitialNumberOfTasks = 1000

  private case class State(tasks: List[Task], newTask: String = "")

  private case class Backend(scope: BackendScope[PerfDemoV3, State]) {

    private def addNewTask(title: String) = {
      ReactPerf.start()
      scope.modState { state =>
        val newId = state.tasks.length + 1
        val tasks = Task(newId, title) :: state.tasks
        state.copy(tasks = tasks)
      }
    }

    def render(state: State): TagOf[Div] = {
      <.div(
        AddTaskForm(addNewTask)(),

        <.ul(^.cls := "list pl0 mv2",
          state.tasks.toVdomArray { task =>
            PerfDemoItemV2(task)()
          }
        )
      )
    }
  }

  private val component = ScalaComponent.builder[PerfDemoV3](ComponentName)
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

