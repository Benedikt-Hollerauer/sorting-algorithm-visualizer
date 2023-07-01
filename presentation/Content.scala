import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.entity.VisualizeEntity
import core.model.{BarColorModel, BarModel, NonEmptyListModel, SortableModel, SortedModel, SortingModel, ValueWithIndexModel}
import org.scalajs.dom.{HTMLDivElement, console}

import scala.scalajs.js.timers.setTimeout

object Content:

	def getHtmlDiv(sortingAlgorithm: SortingAlgorithm, sorted: SortedModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.pageContentStyle,
			getBarArrayDiv(sorted, 100)
		)

	private def getBarArrayDiv(sortedModel: SortedModel, intervalMs: Int): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.barArrayStyle,
			children <-- EventStream.periodic(intervalMs).map: tick =>
				val barArray = VisualizeEntity.getBarVisualisation(sortedModel)
					.map(this.getBars)
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
				case BarColorModel.Blue => "blue"
				case BarColorModel.Red => "red"
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