package foo.bar.todo

sealed trait Page
case object IndexPage extends Page
case object HighlightDemoPage extends Page
case object HighlightDemoPageV2 extends Page
