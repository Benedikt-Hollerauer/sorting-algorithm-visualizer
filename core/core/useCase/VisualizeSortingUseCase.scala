package core.useCase

import core.entity.VisualizeEntity
import core.input.VisualizeSortingInput
import core.model.{BarModel, NonEmptyListModel, SortableModel}

object VisualizeSortingUseCase:

	def apply(
		input: VisualizeSortingInput
	): LazyList[NonEmptyListModel[BarModel]] =
		VisualizeEntity.getBarVisualisation(input.sortedModel)