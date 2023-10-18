import SortingAlgorithm.{BubbleSort, InsertionSort}
import com.raquo.laminar.api.L.{*, given}
import core.input.{SortingAlgorithmUseCaseInput, VisualizeSortingInput}
import core.model.{OrderModel, SortableModel, ValueWithIndexModel, VisualizeModel}
import core.typeClass.{GetBarModel, GetBarVisualisation}
import core.useCase.{GenerateSortableUseCase, SortByInsertionSortUseCase, VisualizeSortingUseCase}
import mock.inputMock.GenerateSortableInputMock
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase

import scala.util.{Failure, Success, Try}

object Main:

	private def appOnSuccess(sortable: SortableModel[ValueWithIndexModel], ordering: OrderModel) =
		div(
			height.vh := 100,
			width.vw := 100,
			h1(
				child <-- SideMenu.sortingAlgorithmRadioButtonsVar.signal.map(_.getName)
			),
			NavigationBar.getHtml(
				logo = VisualModel("assets/sorting-visualizer-logo.svg", "Site Logo"), socialIcons = List(VisualModel("assets/github-icon.svg", "GitHub", Some("https://github.com/Benedikt-Hollerauer")), VisualModel("assets/linkedin-icon.svg", "LinkedIn", Some("https://www.linkedin.com/in/benedikt-hollerauer-b198b6259/")), VisualModel("assets/website-icon.svg", "Website", Some("https://benedikt-hollerauer.com/"))),
				retractedIcon = VisualModel("assets/retracted-side-menu.svg", "Retracted Side Menu"),
				extendIcon = VisualModel("assets/extend-side-menu.svg", "Extended Side Menu")
			),
			SideMenu.getHtml(
				startIcon = VisualModel("assets/start-visualisation.svg", "Start Visualisation"),
				stopIcon = VisualModel("assets/stop-visualisation.svg", "Stop Visualisation"),
				newToBeSortedIcon = VisualModel("assets/create-new-to-be-sorted.svg", "Create New ToBeSorted")
			),
			child <-- SideMenu.newToBeSortedButtonSignal.flatMap: clicked =>
				if(clicked)
					val sortable = GenerateSortableUseCase(
						GenerateSortableInputMock.success
					).toOption.get
					getVisualizeModel(
						SideMenu.sortingAlgorithmRadioButtonsVar.signal,
						SortingAlgorithmUseCaseInput(
							sortable,
							ordering
						)
					).map:
						_ match
							case Success(value) => Content.getHtml(value)
							case Failure(exception) => Error.getHtml(exception.getMessage)
				else getVisualizeModel(
					SideMenu.sortingAlgorithmRadioButtonsVar.signal,
					SortingAlgorithmUseCaseInput(
						sortable,
						ordering
					)
				).map:
					_ match
						case Success(value) => Content.getHtml(value)
						case Failure(exception) => Error.getHtml(exception.getMessage)
					,
			Legend.getHtml
		)

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
				render(
					dom.document.body,
					appOnSuccess(
						sortable = sortable,
						ordering = OrderModel.Descending
					)
				)

	private def getVisualizeModel(
		selectedSortingAlgorithmSignal: Signal[SortingAlgorithm],
		input: SortingAlgorithmUseCaseInput
	): Signal[Try[VisualizeModel]] =
		selectedSortingAlgorithmSignal.map: selectedSortingAlgorithm =>
			Try(
				selectedSortingAlgorithm match
					case BubbleSort => VisualizeSortingUseCase.getVisualizeModelBubbleSort(
						VisualizeSortingInput(
							SortByBubbleSortUseCase(input)
						)
					)
					case InsertionSort => VisualizeSortingUseCase.getVisualizeModelInsertionSort(
						VisualizeSortingInput(
							SortByInsertionSortUseCase(input)
						)
					)
			)