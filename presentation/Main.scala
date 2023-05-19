import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main:

	@main
	def main: Unit =
		render(
			dom.document.body,
			div(
				height := "100vh",
				width := "100vw",
				NavigationBar.getHtml(
					"test",
					List(SortingAlgorithm.BubbleSort, SortingAlgorithm.BubbleSort)
				),
				Content.getHtml(SortingAlgorithm.BubbleSort)
			)
		)