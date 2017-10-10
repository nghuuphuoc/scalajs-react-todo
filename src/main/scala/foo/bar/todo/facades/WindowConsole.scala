package foo.bar.todo.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("console")
object WindowConsole extends js.Object {
  def group(string: String): Unit = js.native
  def groupEnd(): Unit = js.native
  def log(params: js.Any): Unit = js.native
  def warn(params: js.Any): Unit = js.native
}
