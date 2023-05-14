import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLDivElement, HTMLImageElement, HTMLLIElement, HTMLUListElement}

object NavigationBar:

	def getHtml(logoSrc: String, sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.navigationBarStyle,
			getLogo(logoSrc),
			getNavigationItems(sortingAlgorithms)
		)

	private def getLogo(logoSrc: String): ReactiveHtmlElement[HTMLImageElement] =
		img(
			NavigationBarStyle.logoStyle,
			src := logoSrc
		)

	private def getNavigationItems(sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLUListElement] =
		ul(
			NavigationBarStyle.navigationItemsStyle,
			getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithms)
		)

	private def getSelectSortingAlgorithmMenuItemHtml(sortingAlgorithms: List[SortingAlgorithm]): List[ReactiveHtmlElement[HTMLLIElement]] =
		sortingAlgorithms.map: sortingAlgorithm =>
			li(
				NavigationBarStyle.sortingAlgorithmMenuItemStyle,
				sortingAlgorithm.toString
			)

object NavigationBarStyle:

	val navigationBarStyle = Seq(
		height := "60px",
		width := "100%",
		backgroundColor := "#f5f5f5",
		display.flex,
		alignItems.center,
		padding := "0 20px",
		boxShadow := "0 2px 5px rgba(0, 0, 0, 0.1)"
	)

	val logoStyle = Seq(
		height := "40px",
		marginRight := "20px"
	)

	val navigationItemsStyle = Seq(
		listStyle := "none",
		display.flex,
		alignItems.center,
		margin := "0"
	)

	val sortingAlgorithmMenuItemStyle = Seq(
		padding := "8px 12px",
		borderRadius := "4px",
		backgroundColor := "#ffffff",
		color := "#333333",
		fontWeight.bold,
		marginRight := "10px",
		cursor.pointer
	)