import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLDivElement, HTMLImageElement, HTMLLIElement, HTMLUListElement}

object NavigationBar:

	val menuVisibleVar = Var(false)
	val menuVisibleSignal: Signal[Boolean] = menuVisibleVar.signal

	def getHtml(logoSrc: String): ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.navigationBarStyle,
			getSocialIcons,
			getLogo(logoSrc),
			getHamburgerMenu
		)

	private def getLogo(logoSrc: String): ReactiveHtmlElement[HTMLImageElement] =
		img(
			NavigationBarStyle.logoStyle,
			src := logoSrc,
			alt := "Logo"
		)

	private def getSocialIcons: ReactiveHtmlElement[HTMLUListElement] =
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

	private def getHamburgerMenu: ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.hamburgerMenuStyle,
			onClick --> (_ => menuVisibleVar.update(!_)),
			img(
				NavigationBarStyle.iconImageStyle,
				src := "assets/hamburger-menu-icon.svg",
				alt := "Hamburger Menu"
			)
		)

object NavigationBarStyle:

	val navigationBarHeight = height.percent := 7

	val navigationBarStyle = Seq(
		navigationBarHeight,
		width.percent := 100,
		backgroundColor := "white",
		display.flex,
		alignItems.center,
		justifyContent.spaceBetween,
		boxShadow := "0 2px 5px rgba(0, 0, 0, 0.1)",
		outline := "thin solid black"
	)

	val logoStyle = Seq(
		height.px := 40,
		marginRight.px := 20
	)

	val socialIconsStyle = Seq(
		listStyleType.none,
		display.flex,
		alignItems.flexStart,
		paddingLeft.px := 0,
		margin.px := 0
	)

	val hamburgerMenuStyle = Seq(
		display.flex,
		alignItems.center,
		cursor.pointer
	)

	val socialIconStyle = Seq(
		display.flex,
		alignItems.center,
		marginRight.px := 10
	)

	val iconImageStyle = Seq(
		height := "30px",
		width := "30px"
	)