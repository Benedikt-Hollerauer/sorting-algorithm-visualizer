import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.{ReactiveElement, ReactiveHtmlElement}
import core.input.SortByBubbleSortInput
import core.model.{NonEmptyListModel, OrderModel, SortableModel, SortedModel, SortingModel, ValueWithIndexModel}
import core.useCase.GenerateSortableUseCase
import error.useCaseError.GenerateSortableUseCaseError
import mock.ToBeSortedMock
import mock.inputMock.GenerateSortableInputMock
import org.scalajs.dom
import org.scalajs.dom.HTMLDivElement
import useCase.SortByBubbleSortUseCase

import java.time.Instant
import scala.scalajs.js.timers.setInterval

object Content:

	private def getBars(sortable: SortableModel): Map[Int, Var[Bar]] =
		Map.from(
			sortable.valuesWithIndices
			.list
			.map: valueWithIndex =>
				valueWithIndex.indexModel.index -> Var(
					Bar(
						id = valueWithIndex.indexModel.index,
						height = valueWithIndex.value,
						backgroundColor = "blue"
					)
				)
		)

	def getErrorHtmlDiv(error: GenerateSortableUseCaseError): ReactiveHtmlElement[HTMLDivElement] =
		Error.getHtmlDiv(
			error.toString
		)

	def getHtmlDiv(sortingAlgorithm: SortingAlgorithm, sorted: SortedModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.pageContentStyle,
			{
				val bars = getBars(sorted.sortableModel)
				changeBars(sorted.changes, bars)
				getBarArrayDiv(sorted.sortableModel, bars)
			}
		)

	private def changeBars(changes: LazyList[SortingModel], bars: Map[Int, Var[Bar]]) =
		val changeStream = changes.iterator
		setInterval(250):
			if(changeStream.hasNext)
				val change = changeStream.next
				changeFocusedBars(change, bars)

	private def changeFocusedBars(change: SortingModel, barVar: Map[Int, Var[Bar]]): Unit =
		val focusedIds = Seq(
			change.focusedIndices._1,
			change.focusedIndices._2
		)
		val changeIndices = (
			change.focusedIndices._1.indexModel.index,
			change.focusedIndices._2.indexModel.index
		)
		focusedIds.foreach: id =>
			if(change.focusedIndicesChanged)
				barVar.get(changeIndices._1).get.update(current => current.copy(backgroundColor = "green"))
				barVar.get(changeIndices._2).get.update(current => current.copy(backgroundColor = "green"))
			else
				barVar.get(changeIndices._1).get.update(current => current.copy(backgroundColor = "red"))
				barVar.get(changeIndices._2).get.update(current => current.copy(backgroundColor = "red"))
			barVar.get(changeIndices._1).get.update(current => current.copy(height = id.value))
			barVar.get(changeIndices._2).get.update(current => current.copy(height = id.value))

	private def getBarArrayDiv(sortable: SortableModel, bars: Map[Int, Var[Bar]]): ReactiveHtmlElement[HTMLDivElement] =
		div(
			idAttr := "barArray",
			ContentStyle.barArrayStyle,
			bars.map: i =>
				div(
					ContentStyle.singleBar(i._2)
				)
			.toSeq
		)

object ContentStyle:

	def singleBar(bar: Var[Bar]) = Seq(
			idAttr <-- bar.signal.map(_.id.toString),
			width := "20px",
			height.px <-- bar.signal.map(_.height),
			backgroundColor <-- bar.signal.map(_.backgroundColor),
			margin := "3px"
		)

	val pageContentStyle = Seq(
		position.relative,
		width <-- NavigationBar.menuVisibleVar.signal.map:
			if(_) "75%"
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

case class Bar(
	id: Int,
	height: Int,
	backgroundColor: String
)