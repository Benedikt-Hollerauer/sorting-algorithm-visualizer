import com.raquo.airstream.timing
import com.raquo.airstream.timing.PeriodicStream
import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.entity.VisualizeEntity
import core.input.VisualizeSortingInput
import core.model.*
import core.useCase.VisualizeSortingUseCase
import org.scalajs.dom.{HTMLDivElement, console}

import scala.scalajs.js.timers.{setInterval, setTimeout}

object Content:

	val visualisationStream = EventStream.periodic(1000)

	def getHtml(visualizeModel: VisualizeModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.pageContentStyle,
			child <-- SideMenu.sliderSpeedSignal.map: intervalMs =>
				getBarArray(visualizeModel, intervalMs)
		)

	private def getBarArray(visualizeModel: VisualizeModel, intervalMs: Int): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.barArrayStyle,
			children <-- EventStream.periodic(intervalMs)
				.withCurrentValueOf(SideMenu.startStopButtonSignal)
				.scanLeft((0, List.empty[ReactiveHtmlElement[HTMLDivElement]])):
					case (acc, (tick, false)) => (
						acc._1,
						if(acc._1 == 0) getBars(visualizeModel.notStartedSorting)
						else visualizeModel.changes.lift(acc._1) match
							case Some(bar) => getBars(bar)
							case None => getBars(visualizeModel.finishedSorting)
					)
					case (acc, (tick, true)) => (
						acc._1 + 1,
						if(acc._1 == 0) getBars(visualizeModel.notStartedSorting)
						else visualizeModel.changes.lift(acc._1) match
							case Some(bar) => getBars(bar)
							case None => getBars(visualizeModel.finishedSorting)
					)
				.map(_._2)
	)

	private def getBars(toBeBars: SortableModel[BarModel]): List[ReactiveHtmlElement[HTMLDivElement]] =
		toBeBars.list
			.map: bar =>
				div(
					ContentStyle.singleBar(bar)
				)

object ContentStyle:

	def singleBar(bar: BarModel) = Seq(
		width.px := 20,
		height.px := bar.value,
		backgroundColor := (
			bar.barState match
				case BarStateModel.Normal => "#390099"
				case BarStateModel.Focused => "#c1121f"
				case BarStateModel.Swapped => "#008000"
				case BarStateModel.AlreadySorted => "#4cc9f0"
				case BarStateModel.FinishedSorting => "#f72585"
		),
		margin.px := 3,
		borderRadius.px := 8
	)

	val pageContentStyle = Seq(
		position.relative,
		width.percent <-- NavigationBar.extendCollapseSideMenuVar.signal.map: //TODO make public to fulfill dry principle
			if(_) 75
			else 100,
		height := s"calc(100% - ${NavigationBarStyle.navigationBarHeight.value} - ${LegendStyle.legendHeight.value})",
		display.flex,
		justifyContent.center,
		alignItems.flexEnd
	)

	val barArrayStyle = Seq(
		display.flex,
		flexWrap.wrap,
		alignItems.flexEnd
	)