import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object NavigationBar:

	def getHtml(logoSrc: String, sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			img(
				cls := "menuLogo",
				src := logoSrc
			),
			div(
				getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithms)
			)
		)

	private def getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithms: List[SortingAlgorithm]) =
		sortingAlgorithms.map: sortingAlgorithm =>
			li(
				cls := "",
				idAttr := sortingAlgorithm.toString.toLowerCase,
				span(
					cls := "",
					sortingAlgorithm.toString,
					span(
						cls := ""
					)
				)
			)