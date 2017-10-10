package foo.bar.todo.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("React.addons.Perf")
object ReactPerf extends js.Object {
  def start(): Unit = js.native
  def stop(): Unit = js.native
  def printInclusive(): Unit = js.native
  def getLastMeasurements(): js.Object = js.native
  def printWasted(measurements: js.Object): Unit = js.native
}
