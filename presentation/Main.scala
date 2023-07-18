import com.raquo.laminar.api.L.{*, given}
import core.input.{SortByBubbleSortInput, VisualizeSortingInput}
import core.model.{OrderModel, SortableModel}
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
				render(
					dom.document.body,
					div(
						height.vh := 100,
						width.vw := 100,
						NavigationBar.getHtml(
							"assets/sorting-visualizer-logo.svg"
						),
						SideMenu.getHtml(
							List(SortingAlgorithm.BubbleSort, SortingAlgorithm.BubbleSort)
						),
						child <-- SideMenu.newToBeSortedButtonSignal.map: clicked =>
							if(clicked)
								val sortable = GenerateSortableUseCase(
									GenerateSortableInputMock.success
								).toOption.get
								Content.getHtml(
									getVisualizeModel(sortable, OrderModel.Ascending)
								)
							else Content.getHtml(
								getVisualizeModel(sortable, OrderModel.Ascending)
							),
						Legend.getHtml
					)
				)

	private def getVisualizeModel(toBeSorted: SortableModel, ordering: OrderModel) =
		val sorted = SortByBubbleSortUseCase(
			SortByBubbleSortInput(
				toBeSorted,
				ordering
			)
		)
		VisualizeSortingUseCase(
			VisualizeSortingInput(sorted)
		)