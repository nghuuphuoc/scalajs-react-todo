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

  private case class State(tasks: List[(Int, String, Boolean)])

  private case class Backend(scope: BackendScope[HomePage, State]) {

    def render(state: State): TagOf[Div] = {
      <.div(
        <.ul(^.cls := "list pl0 mb2",
          state.tasks.toTagMod { case (id, title, done) =>
            <.li(^.cls := "flex items-center mb2",
              <.input(
                ^.cls := "mr2",
                ^.tpe := "checkbox",
                ^.id := s"task-$id",
                ^.checked := done,
                ^.onChange ==> { (e: ReactEventFromInput) =>
                  e.extract(_.target.checked) { checked =>
                    val index = state.tasks.indexWhere(_._1 == id)
                    Callback.when(index >= 0) {
                      val tasks = state.tasks.updated(index, state.tasks(index).copy(_3 = checked))
                      scope.modState(_.copy(tasks = tasks))
                    }
                  }
                }
              ),
              <.label(
                ^.cls := "lh-copy",
                ^.htmlFor := s"task-$id",
                title
              )
            )
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
        (index, s"Task $index", r % 2 == 0)
      }.toList
      State(tasks = tasks)
    }
    .renderBackend[Backend]
    .build
}
