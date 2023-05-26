import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main:

	def main(args: Array[String]): Unit =
		render(
			dom.document.body,
			div(
				height := "100vh",
				width := "100vw",
				NavigationBar.getHtmlDiv(
					"test",
					List(SortingAlgorithm.BubbleSort, SortingAlgorithm.BubbleSort)
				),
				Content.getHtmlDiv(SortingAlgorithm.BubbleSort)
			)
		)