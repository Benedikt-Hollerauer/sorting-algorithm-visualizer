import NavigationBarStyle.navigationBarHeight
import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLButtonElement, HTMLDivElement}

object SideMenu:

	val startStopButtonVar: Var[Boolean] = Var(false)
	val startStopButtonSignal: Signal[Boolean] = startStopButtonVar.signal
	
	def getHtml(sortingAlgorithms: List[SortingAlgorithm]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			SideMenuStyle.slidingMenuStyle,
			transform <-- NavigationBar.menuVisibleSignal
				.map: visible =>
					if(visible) "translateX(0)"
					else "translateX(100%)",
			getStartStopButton,
			ul(
				SideMenuStyle.menuItemsStyle,
				sortingAlgorithms.map: sortingAlgorithm =>
					li(
						SideMenuStyle.sortingAlgorithmMenuItemStyle,
						sortingAlgorithm.toString
					)
			)
		)

	private def getStartStopButton: ReactiveHtmlElement[HTMLButtonElement] =
		button(
			SideMenuStyle.startStopButtonStyle,
			onClick --> (_ => startStopButtonVar.update(!_)),
			img(
				src <-- startStopButtonSignal.map: started =>
					if(started) "assets/github-icon.svg"
					else "assets/linkedin-icon.svg",
				alt := "Start / Stop Symbol"
			)
		)

object SideMenuStyle:

	private val subMenuWidth = width.percent := 25

	val slidingMenuStyle = Seq(
		position.fixed,
		top := navigationBarHeight.value,
		right.px := 0,
		height.percent := 100,
		subMenuWidth,
		backgroundColor := "#ffffff",
		transform := "translateX(100%)",
		transition := "transform 0.3s ease-in-out",
		borderLeft := "thin solid black"
	)

	val menuItemsStyle = Seq(
		listStyleType.none,
		padding.px := 0,
		margin.px := 0,
		marginTop.px := 10
	)

	val sortingAlgorithmMenuItemStyle = Seq(
		padding := "8px 12px",
		borderRadius.px := 4,
		backgroundColor := "#ffffff",
		color := "#333333",
		fontWeight.bold,
		marginRight.px := 10,
		cursor.pointer
	)

	val startStopButtonStyle = Seq(
		width.percent := 80
	)