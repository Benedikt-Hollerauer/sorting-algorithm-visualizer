import com.raquo.laminar.api.L.{*, given}
import core.input.SortByBubbleSortInput
import core.model.OrderModel
import core.useCase.GenerateSortableUseCase
import mock.inputMock.GenerateSortableInputMock
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase

import scala.scalajs.js.timers.setTimeout

object Main:

	def main(args: Array[String]): Unit =
		val res = GenerateSortableUseCase(
			GenerateSortableInputMock.success
		) match
			case Left(generateSortableError) =>
				render(
					dom.document.body,
					Error.getHtmlDiv(generateSortableError.toString)
				)
			case Right(sortable) =>
				val sorted = SortByBubbleSortUseCase(
					SortByBubbleSortInput(
						sortable,
						OrderModel.Ascending
					)
				)
				render(
					dom.document.body,
					div(
						height := "100vh",
						width := "100vw",
						NavigationBar.getHtmlDiv(
							"test",
							List(SortingAlgorithm.BubbleSort, SortingAlgorithm.BubbleSort)
						),
						Content.getHtmlDiv(
							SortingAlgorithm.BubbleSort,
							sorted
						)
					)
				)