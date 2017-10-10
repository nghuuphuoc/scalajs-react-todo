package foo.bar.todo

sealed trait Page
case object IndexPage extends Page
case object HighlightDemoPage extends Page
