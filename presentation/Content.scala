import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.entity.VisualizeEntity
import core.input.VisualizeSortingInput
import core.model.*
import core.useCase.VisualizeSortingUseCase
import org.scalajs.dom.{HTMLDivElement, console}

import scala.scalajs.js.timers.setTimeout

object Content:

	def getHtml(sortingAlgorithm: SortingAlgorithm, sorted: SortedModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.pageContentStyle,
			getBarArray(sorted, 150)
		)

	private def getBarArray(sortedModel: SortedModel, intervalMs: Int): ReactiveHtmlElement[HTMLDivElement] =
		val barArray = VisualizeSortingUseCase(
			VisualizeSortingInput(sortedModel)
		).map(getBars)
		div(
			ContentStyle.barArrayStyle,
			children <-- EventStream.periodic(intervalMs).map: tick =>
				barArray.lift(tick) match
					case Some(bar) => bar
					case None => barArray.last
		)

	private def getBars(toBeBars: NonEmptyListModel[BarModel]): List[ReactiveHtmlElement[HTMLDivElement]] =
		toBeBars.list
			.map: bar =>
				div(
					ContentStyle.singleBar(bar)
				)

object ContentStyle:

	def singleBar(bar: BarModel) = Seq(
		idAttr := bar.id.toString,
		width := "20px",
		height.px := bar.value,
		backgroundColor := (
			bar.barColor match
				case BarStateModel.Normal => "blue"
				case BarStateModel.Focused => "red"
				case BarStateModel.Swapped => "green"
		),
		margin := "3px"
	)

	val pageContentStyle = Seq(
		position.relative,
		width <-- NavigationBar.menuVisibleVar.signal.map:
			if (_) "75%"
			else "100%",
		height := s"calc(100% - ${NavigationBarStyle.navigationBarHeight})",
		display.flex,
		justifyContent.center,
		alignItems.center
	)

	val barArrayStyle = Seq(
		display.flex,
		flexWrap.wrap,
		alignItems.flexEnd,
	)