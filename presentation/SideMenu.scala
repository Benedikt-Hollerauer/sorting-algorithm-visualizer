import NavigationBarStyle.navigationBarHeight
import SortingAlgorithm.BubbleSort
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.KeyFormat.raw
import org.scalajs.dom.{HTMLButtonElement, HTMLDivElement, HTMLInputElement}

import javax.management.Query.attr

object SideMenu:

	private val startStopButtonVar: Var[Boolean] = Var(false)
	val startStopButtonSignal: Signal[Boolean] = startStopButtonVar.signal

	private val newToBeSortedButtonVar: Var[Boolean] = Var(false)
	val newToBeSortedButtonSignal: Signal[Boolean] = newToBeSortedButtonVar.signal

	private val sliderSpeedBus: EventBus[Int] = new EventBus[Int]
	val sliderSpeedSignal: Signal[Int] = sliderSpeedBus.events.startWith(50)

	val sortingAlgorithmRadioButtonsVar: Var[SortingAlgorithm] = Var(SortingAlgorithm.InsertionSort)

	def getHtml(
		startIcon: VisualModel,
		stopIcon: VisualModel,
		newToBeSortedIcon: VisualModel
	): ReactiveHtmlElement[HTMLDivElement] =
		div(
			SideMenuStyle.slidingMenuStyle,
			getStartStopButton(startIcon, stopIcon),
			getCreateNewToBeSortedButton(newToBeSortedIcon),
			getSortingSpeedSlider,
			getSortingAlgorithmSelectionRadioButtons(
				SortingAlgorithm.values.toList,
				SortingAlgorithm.BubbleSort
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
				src <-- startStopButtonSignal.map: started =>
					if(started) stopIcon.src
					else startIcon.src,
				alt <-- startStopButtonSignal.map: started =>
					if(started) startIcon.alt
					else stopIcon.alt
			)
		)

	private def getCreateNewToBeSortedButton(newToBeSortedIcon: VisualModel): ReactiveHtmlElement[HTMLButtonElement] =
		button(
			SideMenuStyle.newToBeSortedButtonStyle,
			onClick --> (_ => startStopButtonVar.update(_ => false)),
			onClick --> (_ => newToBeSortedButtonVar.update(!_)),
			img(
				src := newToBeSortedIcon.src,
				alt := newToBeSortedIcon.alt
			)
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
			child.text <-- sliderSpeedSignal
		)

	private def getSortingAlgorithmSelectionRadioButtons(sortingAlgorithms: List[SortingAlgorithm], standartSortingAlgorithm: SortingAlgorithm) =
		div(
			SideMenuStyle.sortingAlgorithmRadioButtonsStyle,
			label(
				SideMenuStyle.sortingAlgorithmLabelStyle,
				"Choose a Sorting Algorithm"
			),
			form(
				SideMenuStyle.sortingAlgorithmFormStyle,
				sortingAlgorithms.flatMap: sortingAlgorithm =>
					List(
						input(
							nameAttr := "sortingAlgorithmSelection",
							idAttr := sortingAlgorithm.toString,
							value := sortingAlgorithm.getName,
							typ := "radio",
							checked <-- sortingAlgorithmRadioButtonsVar.signal
								.map(_ == sortingAlgorithm),
							onInput.mapTo(sortingAlgorithm) --> sortingAlgorithmRadioButtonsVar.writer
						),
						label(
							forId := sortingAlgorithm.toString,
							sortingAlgorithm.getName
						)
					)
			),
			div(
				child <-- sortingAlgorithmRadioButtonsVar.signal.map(_.getName)
			)
		)

object SideMenuStyle:

	private val subMenuWidth = width.percent := 25

	val slidingMenuStyle = Seq(
		transform <-- NavigationBar.extendCollapseSideMenuSignal
			.map: visible =>
				if (visible) "translateX(0)"
				else "translateX(100%)",
		position.fixed,
		top := navigationBarHeight.value,
		right.px := 0,
		height.percent := 100,
		subMenuWidth,
		backgroundColor := "#ffffff",
		borderLeft := "thin solid black",
		display.flex,
		flexDirection.column,
		alignItems.center
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
		backgroundColor := "#ffffff", color := "#333333",
		fontWeight.bold,
		marginRight.px := 10,
		cursor.pointer
	)

	val startStopButtonStyle = Seq(
		width.percent := 90
	)

	val newToBeSortedButtonStyle = Seq(
		width.percent := 90
	)

	val sortingSpeedSliderStyle = Seq(
		typ := "range",
		stepAttr := "5",
		width.percent := 90,
		cursor.pointer
	)

	val sortingAlgorithmRadioButtonsStyle = Seq(
		width.percent := 90
	)

	val sortingAlgorithmLabelStyle = Seq(
		fontSize.larger
	)

	val sortingAlgorithmFormStyle = Seq(
		height.percent := 100
	)