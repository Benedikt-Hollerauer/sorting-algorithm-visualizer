import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.model.{SortableModel, SortedModel, SortingModel}
import org.scalajs.dom.{HTMLDivElement, console}

object Content:

	def getHtmlDiv(sortingAlgorithm: SortingAlgorithm, sorted: SortedModel) =
		div(
			ContentStyle.pageContentStyle,
			getBarArrayDiv(sorted)
		)

	private def getBarArrayDiv(sortedModel: SortedModel) =
		div(
			ContentStyle.barArrayStyle,
			getBarArrayVisualisation(sortedModel, 250)
		)

	private def getBarArrayVisualisation(sortedModel: SortedModel, intervalMs: Int): List[ReactiveHtmlElement[HTMLDivElement]] =
		var sorted = sortedModel
		EventStream.periodic(intervalMs).map: tick =>
			if(sortedModel.changes.lift(tick).isDefined)
				sorted = sorted.sortableModel
					.valuesWithIndices
					.list
					.updated()
					.updated()
			else

	private def getBars(sortableModel: SortableModel, backgroundColor: String): List[ReactiveHtmlElement[HTMLDivElement]] =
		sortableModel.valuesWithIndices
			.list
			.map: valueWithIndex =>
				div(
					ContentStyle.singleBar(
						Bar(
							id = valueWithIndex.indexModel.index,
							height = valueWithIndex.value,
							backgroundColor = backgroundColor
						)
					)
				)

object ContentStyle:

	def singleBar(bar: Bar) = Seq(
		idAttr := bar.id.toString,
		width := "20px",
		height.px := bar.height,
		backgroundColor := bar.backgroundColor,
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