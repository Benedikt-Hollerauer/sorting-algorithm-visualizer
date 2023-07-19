import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLDivElement, HTMLImageElement, HTMLLIElement, HTMLUListElement}

object NavigationBar:

	val extendCollapseSideMenuVar: Var[Boolean] = Var(false)
	val extendCollapseSideMenuSignal: Signal[Boolean] = extendCollapseSideMenuVar.signal

	def getHtml(
		logo: VisualModel,
		socialIcons: List[VisualModel],
		retractedIcon: VisualModel,
		extendIcon: VisualModel
	): ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.navigationBarStyle,
			getSocialIcons(socialIcons),
			getLogo(logo),
			getExtendCollapseSideMenuIcon(extendIcon, retractedIcon)
		)

	private def getLogo(logo: VisualModel): ReactiveHtmlElement[HTMLImageElement] =
		img(
			NavigationBarStyle.logoStyle,
			src := logo.src,
			alt := logo.alt
		)

	private def getSocialIcons(socialIcons: List[VisualModel]): ReactiveHtmlElement[HTMLUListElement] =
		def getSocialIcon(socialIcon: VisualModel): ReactiveHtmlElement[HTMLLIElement] =
			li(
				NavigationBarStyle.socialIconStyle,
				a(
					href := socialIcon.link.getOrElse("https://benedikt-hollerauer.com"),
					target := "_blank",
					img(
						NavigationBarStyle.iconImageStyle,
						src := socialIcon.src,
						alt := socialIcon.alt
					)
				)
			)
		ul(
			NavigationBarStyle.socialIconsStyle,
			socialIcons.map: socialIcon =>
				getSocialIcon(socialIcon)
		)

	private def getExtendCollapseSideMenuIcon(extendIcon: VisualModel, retractedIcon: VisualModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			NavigationBarStyle.extendCollapseSideMenuIconStyle,
			onClick --> (_ => extendCollapseSideMenuVar.update(!_)),
			img(
				NavigationBarStyle.iconImageStyle,
				src <-- extendCollapseSideMenuSignal.map: extended =>
					if(extended) retractedIcon.src
					else extendIcon.src,
				alt <-- extendCollapseSideMenuSignal.map: extended =>
					if(extended) extendIcon.alt
					else retractedIcon.alt
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

	val extendCollapseSideMenuIconStyle = Seq(
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