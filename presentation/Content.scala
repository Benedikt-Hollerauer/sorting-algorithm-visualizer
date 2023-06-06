import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.model.{NonEmptyListModel, SortableModel, SortedModel, SortingModel}
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
			children <-- getBarArrayVisualisation(sortedModel, 250)
		)

	private def getBarArrayVisualisation(sortedModel: SortedModel, intervalMs: Int): EventStream[List[ReactiveHtmlElement[HTMLDivElement]]] =
		EventStream.periodic(intervalMs).map: tick =>
			getBars(
				changeSorted(sortedModel, tick).sortableModel,
				"blue"
			)

	private def swapSortable(tobeUpdated: SortableModel, swappedIndices: (Int, Int)): SortableModel =
		val swapped = tobeUpdated.valuesWithIndices
			.list
			.updated(swappedIndices._1, tobeUpdated.valuesWithIndices.list(swappedIndices._2))
			.updated(swappedIndices._2, tobeUpdated.valuesWithIndices.list(swappedIndices._1))
		SortableModel.from(
			NonEmptyListModel.from(
				swapped
			).toOption.get
		).toOption.get

	private def changeSorted(sortedModel: SortedModel, tick: Int): SortedModel =
		if(sortedModel.changes.lift(tick).isDefined)
			val changedPositions = sortedModel.sortableModel
				.valuesWithIndices
				.list
				.updated(sortedModel.changes(tick).focusedIndices._1.indexModel.index, sortedModel.sortableModel.valuesWithIndices.list(sortedModel.changes(tick).focusedIndices._1.value))
				.updated(sortedModel.changes(tick).focusedIndices._2.indexModel.index, sortedModel.sortableModel.valuesWithIndices.list(sortedModel.changes(tick).focusedIndices._2.value))
			sortedModel.copy(
				sortableModel = SortableModel.from(
					NonEmptyListModel.from(
						changedPositions
					).toOption.get
				).toOption.get
			)
		else sortedModel

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