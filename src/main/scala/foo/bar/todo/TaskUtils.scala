package foo.bar.todo

import scala.util.Random

object TaskUtils {

  def generateTasks(numTasks: Int = 1000): List[Task] = {
    val random = new Random
    1.to(numTasks).map { index =>
      val done = random.nextInt(100) % 2 == 0

      // Generate the task title
      val title = 0.to(random.nextInt(10)).map(_ => s"Task $index").mkString(" ")
      Task(index, title, done)
    }.toList
  }
}
