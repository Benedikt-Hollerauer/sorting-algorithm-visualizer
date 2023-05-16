import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLDivElement, HTMLImageElement, HTMLLIElement, HTMLUListElement}

object NavigationBar:

	val menuVisibleVar = Var(false)
	val menuVisibleSignal: Signal[Boolean] = menuVisibleVar.signal

	def getHtml(logoSrc: String, sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.navigationBarStyle,
			getSocialIcons(),
			getLogo(logoSrc),
			getHamburgerMenu(sortingAlgorithms)
		)

	private def getLogo(logoSrc: String): ReactiveHtmlElement[HTMLImageElement] =
		img(
			NavigationBarStyle.logoStyle,
			src := logoSrc
		)

	private def getSocialIcons(): ReactiveHtmlElement[HTMLUListElement] =
		ul(
			NavigationBarStyle.socialIconsStyle,
			li("Social Icon 1"),
			li("Social Icon 2"),
			li("Social Icon 3")
		)

	private def getHamburgerMenu(sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.hamburgerMenuStyle,
			onClick --> (_ => menuVisibleVar.update(!_)),
			span("Hamburger Icon"),
			div(
				NavigationBarStyle.pageContentStyle,
				div(
					NavigationBarStyle.slidingMenuStyle,
					transform <-- menuVisibleSignal
						.map: visible =>
							if (visible) "translateX(0)" else "translateX(100%)",
					ul(
						NavigationBarStyle.menuItemsStyle,
						sortingAlgorithms.map: sortingAlgorithm =>
							li(
								NavigationBarStyle.sortingAlgorithmMenuItemStyle,
								sortingAlgorithm.toString
							)
					)
				)
			)
		)

object NavigationBarStyle:


	val navigationBarStyle = Seq(
		height := "20%",
		width := "100%",
		backgroundColor := "#f5f5f5",
		display.flex,
		alignItems.center,
		justifyContent.spaceBetween,
		boxShadow := "0 2px 5px rgba(0, 0, 0, 0.1)",
		outline := "thin solid black"
	)

	val logoStyle = Seq(
		height := "40px",
		marginRight := "20px"
	)

	val socialIconsStyle = Seq(
		listStyle := "none",
		display.flex,
		alignItems.flexStart,
		paddingLeft := "0",
		margin := "0"
	)

	val hamburgerMenuStyle = Seq(
		display.flex,
		alignItems.center,
		cursor.pointer
	)

	val pageContentStyle = Seq(
		display.flex,
		width := "100%",
		height := "100%"
	)

	val slidingMenuStyle = Seq(
		position.fixed,
		top := "0",
		right := "0",
		height := "100%",
		width := "500px",
		backgroundColor := "#ffffff",
		zIndex := "1",
		transform := "translateX(100%)",
		transition := "transform 0.3s ease-in-out",
		width := "500px",
		height := "100%",
		backgroundColor := "#ffffff",
		zIndex := "1",
		transition := "transform 0.3s ease-in-out",
		outline := "thin solid black"
	)

	val menuItemsStyle = Seq(
		listStyle := "none",
		padding := "0",
		margin := "0",
		marginTop := "10px"
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

	val contentWrapperStyle = Seq(
		flex := "1",
		padding := "20px",
		transition := "transform 0.3s ease-in-out"
		//transform <-- NavigationBarStyle.menuVisibleSignal.map(visible => if (visible) "translateX(-500px)" else "translateX(0)")
	)