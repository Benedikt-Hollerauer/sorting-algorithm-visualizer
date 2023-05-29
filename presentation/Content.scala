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

	private val barArrayId = "barArray"

	def getErrorHtmlDiv(error: GenerateSortableUseCaseError): ReactiveHtmlElement[HTMLDivElement] =
		Error.getHtmlDiv(
			error.toString
		)

	def getHtmlDiv(sortingAlgorithm: SortingAlgorithm, sortable: SortableModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.pageContentStyle,
			getBarArrayDiv(sortable)
		)

	private def getBarArrayDiv(sortable: SortableModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			idAttr := barArrayId,
			ContentStyle.barArrayStyle,
			sortable.valuesWithIndices.list.map: i =>
				div(
					ContentStyle.singleBar(
						barHeight = i.value,
						i.indexModel.index
					)
				)
		)

	def changeBarColors(changes: LazyList[SortingModel], intervalMs: Int): Unit =
		val changeStream = changes.iterator
		setInterval(intervalMs)
			if(changeStream.hasNext)
				val change = changeStream.next
				changeFocusedBars(change)

	private def changeFocusedBars(change: SortingModel): Unit =
		val focusedIds = Seq(
			change.focusedIndices._1,
			change.focusedIndices._2
		)
		focusedIds.foreach: id =>
			val barElement = dom.document.getElementById(id.indexModel.index.toString).asInstanceOf[HTMLDivElement]
			if(barElement != null)
				if(change.focusedIndicesChanged)
					barElement.style.backgroundColor = "green"
				else barElement.style.backgroundColor = "red"
				barElement.style.height = id.value.toString

object ContentStyle:

	def singleBar(barHeight: Int, id: Int) = Seq(
		idAttr := id.toString,
		width := "20px",
		height := s"${barHeight}px",
		backgroundColor.blue,
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