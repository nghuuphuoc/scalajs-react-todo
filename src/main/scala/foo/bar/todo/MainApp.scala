package foo.bar.todo

import foo.bar.todo.facades.WhyDidYouUpdate
import japgolly.scalajs.react.extra.router.Router
import japgolly.scalajs.react.raw.React
import org.scalajs.dom

object MainApp {
  def main(args: Array[String]): Unit = {
    val container = dom.document.getElementById("root")
    val router = Router(AppRouter.baseUrl, AppRouter.config)

    // Load the `WhyDidYouUpdate` when visit the `DidUpdatePage`
    val hash = dom.window.location.hash
    if (hash == AppRouter.DidUpdateUrl) {
      WhyDidYouUpdate.whyDidYouUpdate(React)
    }

    router().renderIntoDOM(container)
  }
}
