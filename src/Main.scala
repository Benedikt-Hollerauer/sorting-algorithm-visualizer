import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main:

    def main(args: Array[String]): Unit =
        renderOnDomContentLoaded(
            dom.document.body,
            h1("Sorting Algorithm Visualizer")
        )