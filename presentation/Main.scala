import SortingAlgorithm.{BubbleSort, InsertionSort}
import com.raquo.airstream.core.WritableSignal
import com.raquo.laminar.api.L.{*, given}
import core.input.{SortingAlgorithmUseCaseInput, VisualizeSortingInput}
import core.model.{OrderModel, SortableModel, SortingModel, ValueWithIndexModel, VisualizeModel}
import core.typeClass.GetBarModel.given
import core.typeClass.GetBarVisualisation.given
import core.typeClass.{GetBarModel, GetBarVisualisation}
import core.useCase.{GenerateSortableUseCase, SortByInsertionSortUseCase, VisualizeSortingUseCase}
import mock.inputMock.GenerateSortableInputMock
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase

object Main:

	@main
	def run: Unit =
		val res = GenerateSortableUseCase(
			GenerateSortableInputMock.success
		) match
			case Left(generateSortableError) =>
				render(
					dom.document.body,
					Error.getHtml(generateSortableError.toString)
				)
			case Right(sortable) =>
				val selectedSortingAlgorithm = SideMenu.sortingAlgorithmRadioButtonsVar.now()
				render(
					dom.document.body,
					div(
						height.vh := 100,
						width.vw := 100,
						NavigationBar.getHtml(
							logo = VisualModel("assets/sorting-visualizer-logo.svg", "Site Logo"),
							socialIcons = List(
								VisualModel("assets/github-icon.svg", "GitHub", Some("https://github.com/Benedikt-Hollerauer")),
								VisualModel("assets/linkedin-icon.svg", "LinkedIn", Some("https://www.linkedin.com/in/benedikt-hollerauer-b198b6259/")),
								VisualModel("assets/website-icon.svg", "Website", Some("https://benedikt-hollerauer.com/"))
							),
							retractedIcon = VisualModel("assets/retracted-side-menu.svg", "Retracted Side Menu"),
							extendIcon = VisualModel("assets/extend-side-menu.svg", "Extended Side Menu")
						),
						SideMenu.getHtml(
							startIcon = VisualModel("assets/start-visualisation.svg", "Start Visualisation"),
							stopIcon = VisualModel("assets/stop-visualisation.svg", "Stop Visualisation"),
							newToBeSortedIcon = VisualModel("assets/create-new-to-be-sorted.svg", "Create New ToBeSorted")
						),
						child <-- SideMenu.newToBeSortedButtonSignal.map: clicked =>
							if(clicked)
								val sortable = GenerateSortableUseCase(
									GenerateSortableInputMock.success
								).toOption.get
								Content.getHtml(
									getVisualizeModel(
										selectedSortingAlgorithm,
										SortingAlgorithmUseCaseInput(sortable, OrderModel.Ascending)
									)
								)
							else Content.getHtml(
								getVisualizeModel(
									selectedSortingAlgorithm,
									SortingAlgorithmUseCaseInput(sortable, OrderModel.Ascending)
								)
							),
						Legend.getHtml
					)
				)

	private def getVisualizeModel(selectedSortingAlgorithm: SortingAlgorithm, input: SortingAlgorithmUseCaseInput): VisualizeModel =
		val sortedModel = selectedSortingAlgorithm match
			case BubbleSort => SortByBubbleSortUseCase(input)
			case InsertionSort => SortByInsertionSortUseCase(input)
		VisualizeSortingUseCase(
			VisualizeSortingInput(sortedModel)
		)