import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.model.{NonEmptyListModel, SortableModel, SortedModel, SortingModel}
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
				val barArray = getBarArrayVisualisation(sortedModel)
				barArray.lift(tick) match
					case Some(bar) => bar
					case None => barArray.last
		)

	private def getBarArrayVisualisation(sortedModel: SortedModel): LazyList[List[ReactiveHtmlElement[HTMLDivElement]]] =
		sortedModel.changes
			.foldLeft(
				(sortedModel.sortableModel, LazyList.empty[List[ReactiveHtmlElement[HTMLDivElement]]])
			): (acc, change) =>
				val newSortable = swapSortable(acc._1, (change.focusedIndices._1.indexModel.index, change.focusedIndices._2.indexModel.index))
				(
					newSortable,
					acc._2 :+ getBars(
						newSortable,
						"#009FFD"
					)
				)
			._2

	private def swapSortable(toBeUpdated: SortableModel, swappedIndices: (Int, Int)): SortableModel =
		val list = toBeUpdated.valuesWithIndices.list
		val element1 = list.find(_.indexModel.index == swappedIndices._1)
		val element2 = list.find(_.indexModel.index == swappedIndices._2)
		if (element1.isEmpty || element2.isEmpty)
			throw new IllegalArgumentException(s"Could not find elements with indices ${swappedIndices._1} and ${swappedIndices._2}")
		val swapped = list.map:
			case v if v == element1.get => element2.get
			case v if v == element2.get => element1.get
			case other => other
		SortableModel.from(
			NonEmptyListModel.from(
				swapped
			).toOption.get
		).toOption.get

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