import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object Menu:

	def getHtml(logoSrc: String, sortingAlgorithms: List[SortingAlgorithms]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			img(
				cls := "menuLogo",
				src := logoSrc
			),
			div(
				sortingAlgorithms.map: sortingAlgorithm =>
					getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithm)
			)
		)

	private def getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithms: SortingAlgorithms) = div() // TODO new type for MenuItem