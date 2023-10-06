package core.input

import core.model.{SortedModel, SortingModel}

case class VisualizeSortingInput[T <: SortingModel](
	sortedModel: SortedModel[T]
)