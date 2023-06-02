import com.raquo.laminar.api.L.{*, given}
import core.model.{SortableModel, SortedModel}

object NewContent:

	def getHtmlDiv(sortingAlgorithm: SortingAlgorithm, sorted: SortedModel) =
		div(
			NewContentStyle.pageContentStyle,
			getBarArrayDiv(sorted.sortableModel)
		)

	private def getBarArrayDiv(sortableModel: SortableModel) =
		div(
			NewContentStyle.barArrayStyle,
			getBars(sortableModel).map: bar =>
				div(
					NewContentStyle.singleBar(bar)
				)
		)

	private def getBars(sortableModel: SortableModel): List[Bar] =
		sortableModel.valuesWithIndices
			.list
			.map: valueWithIndex =>
				Bar(
					id = valueWithIndex.indexModel.index,
					height = valueWithIndex.value,
					backgroundColor = "blue"
				)

object NewContentStyle:

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