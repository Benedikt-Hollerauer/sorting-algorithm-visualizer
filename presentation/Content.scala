import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.model.{NonEmptyListModel, SortableModel, SortedModel, SortingModel}
import org.scalajs.dom.{HTMLDivElement, console}
import scalajs.js.timers.setTimeout

object Content:

	def getHtmlDiv(sortingAlgorithm: SortingAlgorithm, sorted: SortedModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.pageContentStyle,
			getBarArrayDiv(sorted, 250)
		)

	private def getBarArrayDiv(sortedModel: SortedModel, intervalMs: Int): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.barArrayStyle,
			children <-- EventStream.periodic(intervalMs).map: tick =>
				val barArray = getBarArrayVisualisation(sortedModel)
				if(barArray.lift(tick).isDefined)
					barArray(tick)
				else barArray.last
		)

	private def getBarArrayVisualisation(sortedModel: SortedModel): LazyList[List[ReactiveHtmlElement[HTMLDivElement]]] =
		var sortable = sortedModel.sortableModel
		sortedModel.changes
			.map: change =>
				sortable = swapSortable(sortable, (change.focusedIndices._1.indexModel.index, change.focusedIndices._2.indexModel.index))
				getBars(
					sortable,
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