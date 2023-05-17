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
		def getSocialIcon(pathToIcon: String, iconAlt: String, linkTo: String): ReactiveHtmlElement[HTMLLIElement] =
			li(
				NavigationBarStyle.socialIconStyle,
				a(
					href := linkTo,
					target := "_blank",
					img(
						NavigationBarStyle.iconImageStyle,
						src := pathToIcon,
						alt := iconAlt
					)
				)
			)
		ul(
			NavigationBarStyle.socialIconsStyle,
			getSocialIcon(
				pathToIcon = "assets/github-icon.svg",
				iconAlt = "GitHub",
				linkTo = "https://github.com/Benedikt-Hollerauer"
			),
			getSocialIcon(
				pathToIcon = "assets/linkedin-icon.svg",
				iconAlt = "LinkedIn",
				linkTo = "https://www.linkedin.com/in/benedikt-hollerauer-b198b6259/"
			),
			getSocialIcon(
				pathToIcon = "assets/website-icon.svg",
				iconAlt = "Website",
				linkTo = "https://benedikt-hollerauer.com"
			)
		)

	private def getHamburgerMenu(sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.hamburgerMenuStyle,
			onClick --> (_ => menuVisibleVar.update(!_)),
			img(
				NavigationBarStyle.iconImageStyle,
				src := "assets/hamburger-menu-icon.svg",
				alt := "Hamburger Menu"
			),
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

	val socialIconStyle = Seq(
		display.flex,
		alignItems.center,
		marginRight := "10px"
	)

	val iconImageStyle = Seq(
		height := "30px",
		width := "30px"
	)