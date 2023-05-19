import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.{ReactiveElement, ReactiveHtmlElement}
import core.input.SortByBubbleSortInput
import core.model.{OrderModel, SortedModel}
import core.useCase.GenerateSortableUseCase
import error.useCaseError.GenerateSortableUseCaseError
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase
import mock.ToBeSortedMock
import mock.inputMock.GenerateSortableInputMock
import org.scalajs.dom.HTMLDivElement

import java.time.Instant

object Content:

	def getHtml(sortingAlgorithm: SortingAlgorithm): ReactiveHtmlElement[HTMLDivElement] =
		val sortable = GenerateSortableUseCase(
			GenerateSortableInputMock.success
		).left.map:
			case GenerateSortableUseCaseError.InputFailure(value) => value
		SortByBubbleSortUseCase(
			SortByBubbleSortInput(
				sortable,
				OrderModel.Ascending
			)
		) match
			case Left(error) =>
				div(
					h1(
						"there was an error: " + error.toString
					)
				)
			case Right(res) =>
				div(
					ContentStyle.pageContentStyle,
					child <-- EventStream.periodic(50).map: x =>
						if (res.lift(x).isDefined)
							getBarArray(res(x))
						else div(
							getBarArray(res.last)
						)
				)

	private def getBarArray(sorted: SortedModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.barArrayStyle,
			sorted.sortable.list.map: i =>
				div(
					ContentStyle.singleBar(
						barHeight = i,
						barColor =
							if (sorted.changedIndices.contains(i)) "blue"
							else "red"
					)
				)
		)

object ContentStyle:

	def singleBar(barHeight: Int, barColor: String) = Seq(
		width := "20px",
		height := s"${barHeight}px",
		backgroundColor := barColor,
		margin := "0px"
	)

	val pageContentStyle = Seq(
		position.relative,
		transition := "right 0.3s ease-in-out",
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