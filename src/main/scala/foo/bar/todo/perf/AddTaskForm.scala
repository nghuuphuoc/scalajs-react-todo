package foo.bar.todo.perf

import foo.bar.todo.facades.ReactPerf
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Div

final case class AddTaskForm(
  onAdd: String => Callback
) {
  def apply(): Unmounted[_, _, _] = AddTaskForm.component(this)
}

object AddTaskForm {

  private final val ComponentName = getClass.getSimpleName

  private case class State(newTask: String = "")

  private case class Backend(scope: BackendScope[AddTaskForm, State]) {

    def render(props: AddTaskForm, state: State): TagOf[Div] = {
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
          ^.onClick --> props.onAdd(state.newTask),
          "Add new task"
        )
      )
    }
  }

  private val component = ScalaComponent.builder[AddTaskForm](ComponentName)
    .initialState {
      State()
    }
    .renderBackend[Backend]
    .build
}


