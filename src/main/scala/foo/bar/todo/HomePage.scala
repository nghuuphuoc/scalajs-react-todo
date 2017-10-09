package foo.bar.todo

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

  private case class Backend(scope: BackendScope[HomePage, _]) {

    def render(): TagOf[Div] = {
      <.div("Homepage")
    }
  }

  private val component = ScalaComponent.builder[HomePage](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
