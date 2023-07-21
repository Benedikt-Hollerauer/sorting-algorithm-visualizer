package core.useCase

import core.entity.VisualizeEntity
import core.input.VisualizeSortingInput
import core.model.{BarModel, SortableModel, VisualizeModel}

object VisualizeSortingUseCase:

	def apply(
		input: VisualizeSortingInput
	): VisualizeModel =
		VisualizeEntity.getBarVisualisation(input.sortedModel)