import NavigationBarStyle.navigationBarHeight
import SortingAlgorithm.BubbleSort
import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLButtonElement, HTMLDivElement}

object SideMenu:

	private val startStopButtonVar: Var[Boolean] = Var(false)
	val startStopButtonSignal: Signal[Boolean] = startStopButtonVar.signal

	private val newToBeSortedButtonVar: Var[Boolean] = Var(false)
	val newToBeSortedButtonSignal: Signal[Boolean] = newToBeSortedButtonVar.signal

	private val sliderSpeedBus: EventBus[Int] = new EventBus[Int]
	val sliderSpeedSignal: Signal[Int] = sliderSpeedBus.events.startWith(50)

	val sortingAlgorithmRadioButtonsVar: Var[SortingAlgorithm] = Var(SortingAlgorithm.BubbleSort)

	def getHtml(
		startIcon: VisualModel,
		stopIcon: VisualModel,
		newToBeSortedIcon: VisualModel
	): ReactiveHtmlElement[HTMLDivElement] =
		div(
			SideMenuStyle.slidingMenuStyle,
			ul(
				SideMenuStyle.menuListStyle,
				li(
					className := "menu-section",
					SideMenuStyle.menuSectionStyle,
					div(SideMenuStyle.menuSectionTitle, "Controls"),
					div(SideMenuStyle.menuItemsRow,
						getStartStopButton(startIcon, stopIcon),
						getCreateNewToBeSortedButton(newToBeSortedIcon)
					)
				),
				li(
					className := "menu-section",
					SideMenuStyle.menuSectionStyle,
					div(SideMenuStyle.menuSectionTitle, "Speed"),
					getSortingSpeedSlider
				),
				li(
					className := "menu-section",
					SideMenuStyle.menuSectionStyle,
					div(SideMenuStyle.menuSectionTitle, "Algorithm"),
					getSortingAlgorithmSelectionButtons()
				)
			)
		)

	private def getStartStopButton(
		startIcon: VisualModel,
		stopIcon: VisualModel
	): ReactiveHtmlElement[HTMLButtonElement] =
		button(
			SideMenuStyle.startStopButtonStyle,
			onClick --> (_ => startStopButtonVar.update(!_)),
			img(
				className := "icon-img",
				src <-- startStopButtonSignal.map: started =>
					if(started) stopIcon.src
					else startIcon.src,
				alt <-- startStopButtonSignal.map: started =>
					if(started) startIcon.alt
					else stopIcon.alt
			),
			child.text <-- startStopButtonSignal.map(s => if s then "Stop" else "Start")
		)

	private def getCreateNewToBeSortedButton(newToBeSortedIcon: VisualModel): ReactiveHtmlElement[HTMLButtonElement] =
		button(
			SideMenuStyle.newToBeSortedButtonStyle,
			onClick --> (_ => startStopButtonVar.update(_ => false)),
			onClick --> (_ => newToBeSortedButtonVar.update(!_)),
			img(
				className := "icon-img",
				src := newToBeSortedIcon.src,
				alt := newToBeSortedIcon.alt
			),
			"New"
		)

	private def getSortingSpeedSlider: ReactiveHtmlElement[HTMLDivElement] =
		div(
			input(
				SideMenuStyle.sortingSpeedSliderStyle,
				inContext: thisNode =>
					thisNode.ref.addEventListener(
						"input",
						_ =>
							val speed = thisNode.ref.value.toInt
							sliderSpeedBus.writer.onNext(speed)
					)
					thisNode
			),
			span(className := "speed-badge", child.text <-- sliderSpeedSignal.map(v => s"${v}ms"))
		)

	private def getSortingAlgorithmSelectionButtons() =
		div(
			SideMenuStyle.sortingAlgorithmButtonsStyle,
			SortingAlgorithm.values.toList.map: algo =>
				button(
					className <-- sortingAlgorithmRadioButtonsVar.signal.map: selected =>
						if selected == algo then "algo-btn algo-btn-active" else "algo-btn",
					onClick --> (_ => sortingAlgorithmRadioButtonsVar.set(algo)),
					algo.getName
				)
		)

object SideMenuStyle:

	private val subMenuWidth = width.px := 280

	val gap: StyleProp[String] = new StyleProp[String]("gap")

	val slidingMenuStyle = Seq(
		className := "side-menu",
		transform <-- NavigationBar.extendCollapseSideMenuSignal
			.map: visible =>
				if (visible) "translateX(0)" else "translateX(100%)",
		position.fixed,
		top := navigationBarHeight.value,
		right.px := 0,
		height.percent := 100,
		subMenuWidth,
		zIndex := "100",
		backgroundColor := "var(--surface)",
		display.flex,
		flexDirection.column,
		alignItems.stretch,
		padding.px := 20
	)

	val menuListStyle = Seq(
		listStyleType.none,
		padding.px := 0,
		margin.px := 0,
		display.flex,
		flexDirection.column,
		gap := "0px"
	)

	val menuSectionStyle = Seq(
		display.flex,
		flexDirection.column,
		gap := "10px",
		paddingBottom.px := 20
	)

	val menuSectionTitle = Seq(
		className := "section-title"
	)

	val menuItemsRow = Seq(
		display.flex,
		flexDirection.column,
		gap := "8px"
	)

	val startStopButtonStyle = Seq(
		className := "btn-primary",
		width.percent := 100
	)

	val newToBeSortedButtonStyle = Seq(
		className := "btn-secondary",
		width.percent := 100
	)

	val sortingSpeedSliderStyle = Seq(
		typ := "range",
		minAttr := "10",
		maxAttr := "500",
		stepAttr := "5",
		width.percent := 100,
		cursor.pointer
	)

	val sortingAlgorithmButtonsStyle = Seq(
		width.percent := 100,
		display.flex,
		flexDirection.row,
		gap := "6px"
	)
