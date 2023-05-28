import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.{ReactiveElement, ReactiveHtmlElement}
import core.input.SortByBubbleSortInput
import core.model.{OrderModel, SortedModel}
import core.useCase.GenerateSortableUseCase
import error.useCaseError.GenerateSortableUseCaseError
import mock.ToBeSortedMock
import mock.inputMock.GenerateSortableInputMock
import org.scalajs.dom
import org.scalajs.dom.HTMLDivElement
import useCase.SortByBubbleSortUseCase

import java.time.Instant

object Content:

	def getHtmlDiv(sortingAlgorithm: SortingAlgorithm): ReactiveHtmlElement[HTMLDivElement] =
		GenerateSortableUseCase(
			GenerateSortableInputMock.success
		) match
			case Left(error) =>
				Error.getHtmlDiv(
					error.toString
				)
			case Right(sortableModel) =>
				div(
					ContentStyle.pageContentStyle,
					child <-- EventStream.periodic(250).map { x =>
						val res = SortByBubbleSortUseCase(
							SortByBubbleSortInput(
								sortableModel,
								OrderModel.Ascending
							)
						)
						if (res.changes.lift(x).isDefined)
							getBarArrayDiv(res)
						else div(
							getBarArrayDiv(res)
						)
					}
				)

	private def getBarArrayDiv(sorted: SortedModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.barArrayStyle,
			sorted.sortableModel.valuesWithIndices.list.map: i =>
				div(
					ContentStyle.singleBar(
						barHeight = i.value,
						barColor =
							if(sorted.changes(i.indexModel.index).focusedIndicesChanged && sorted.changes.contains(i.indexModel.index)) "green"
							else if(sorted.changes.contains(i.indexModel.index)) "red"
							else "blue"
					)
				)
		)

object ContentStyle:

	def singleBar(barHeight: Int, barColor: String) = Seq(
		width := "20px",
		height := s"${barHeight}px",
		backgroundColor := barColor,
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