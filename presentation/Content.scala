import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.model.{SortableModel, SortedModel}
import org.scalajs.dom.HTMLDivElement

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

	private def getBarArrayVisualisation(sortedModel: SortedModel, intervalMs: Int) =
		getBars(sortedModel.sortableModel)

	private def getBars(sortableModel: SortableModel): List[ReactiveHtmlElement[HTMLDivElement]] =
		sortableModel.valuesWithIndices
			.list
			.map: valueWithIndex =>
				div(
					ContentStyle.singleBar(
						Bar(
							id = valueWithIndex.indexModel.index,
							height = valueWithIndex.value,
							backgroundColor = "blue"
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