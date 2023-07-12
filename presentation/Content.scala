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
		)
		div(
			ContentStyle.barArrayStyle,
			children <-- EventStream.periodic(intervalMs).map: tick =>
				barArray.changes.lift(tick) match
					case Some(bar) => getBars(bar)
					case None => getBars(barArray.finishedSorting)
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
			bar.barState match
				case BarStateModel.Normal => "#390099"
				case BarStateModel.Focused => "#c1121f"
				case BarStateModel.Swapped => "#008000"
				case BarStateModel.AlreadySorted => "#4cc9f0"
				case BarStateModel.FinishedSorting => "#f72585"
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