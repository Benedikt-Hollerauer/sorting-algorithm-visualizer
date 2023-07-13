import com.raquo.laminar.api.L.{*, given}
import core.input.{SortByBubbleSortInput, VisualizeSortingInput}
import core.model.OrderModel
import core.useCase.{GenerateSortableUseCase, VisualizeSortingUseCase}
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
					Error.getHtml(generateSortableError.toString)
				)
			case Right(sortable) =>
				val sorted = SortByBubbleSortUseCase(
					SortByBubbleSortInput(
						sortable,
						OrderModel.Ascending
					)
				)
				val visualizeModel = VisualizeSortingUseCase(
					VisualizeSortingInput(sorted)
				)
				render(
					dom.document.body,
					div(
						height.vh := 100,
						width.vw := 100,
						NavigationBar.getHtml(
							"assets/sorting-visualizer-logo.png"
						),
						SideMenu.getHtml(
							List(SortingAlgorithm.BubbleSort, SortingAlgorithm.BubbleSort)
						),
						Content.getHtml(
							SortingAlgorithm.BubbleSort,
							visualizeModel
						),
						Legend.getHtml
					)
				)