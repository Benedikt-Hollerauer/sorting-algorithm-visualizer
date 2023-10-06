package core.useCase

import core.entity.VisualizeEntity
import core.input.VisualizeSortingInput
import core.model.{SortingModel, VisualizeModel}
import core.typeClass.GetBarModel.given
import core.typeClass.GetBarVisualisation.given
import core.typeClass.{GetBarModel, GetBarVisualisation}

object VisualizeSortingUseCase:

	def apply(
		input: VisualizeSortingInput[SortingModel.BubbleSort]
	): VisualizeModel =
		VisualizeEntity().getBarVisualisation(
			sortedModel = input.sortedModel
		)