package core.input

import core.model.{OrderModel, SortedModel, SortingModel}

case class VisualizeSortingInput[T <: SortingModel](
	sortedModel: SortedModel[T]
)