package foo.bar.todo

import japgolly.scalajs.react.extra.router.Router
import org.scalajs.dom

object MainApp {
  def main(args: Array[String]): Unit = {
    val container = dom.document.getElementById("root")
    val router = Router(AppRouter.baseUrl, AppRouter.config)
    router().renderIntoDOM(container)
  }
}
