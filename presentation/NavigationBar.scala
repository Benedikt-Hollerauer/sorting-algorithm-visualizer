import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object NavigationBar:

	import NavigationBarStyle.*

	def getHtml(logoSrc: String, sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			cls := menuBar,
			img(
				cls := menuLogoImg,
				src := logoSrc
			),
			ul(
				cls := menuItemsUl,
				getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithms)
			)
		)

	private def getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithms: List[SortingAlgorithm]) =
		sortingAlgorithms.map: sortingAlgorithm =>
			li(
				cls := menuItemLi,
				idAttr := sortingAlgorithm.toString.toLowerCase,
				span(
					cls := menuItemNameSpan,
					sortingAlgorithm.toString,
				)
			)

object NavigationBarStyle:

	val menuBar = style(
		width := "100%"
	)

	val menuLogoImg = style(

	)

	val menuItemsUl = style(

	)

	val menuItemLi = style(

	)

	val menuItemNameSpan = style(

	)