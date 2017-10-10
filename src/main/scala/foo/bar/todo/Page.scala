package foo.bar.todo

sealed trait Page
case object IndexPage extends Page
case object HighlightDemoPage extends Page
case object HighlightDemoPageV2 extends Page
case object HighlightDemoPageV3 extends Page
case object DidUpdatePage extends Page
case object DidUpdatePageV2 extends Page
case object PerfPage extends Page
case object PerfPageV2 extends Page
