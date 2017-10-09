package foo.bar.todo

import japgolly.scalajs.react.extra.router.Router
import org.scalajs.dom

object MainApp {
  def main(args: Array[String]): Unit = {
    val container = dom.document.getElementById("root")
    dom.console.info("Router logging is enabled. Enjoy!")
    val router = Router(AppRouter.baseUrl, AppRouter.config.logToConsole)
    router().renderIntoDOM(container)
  }
}
