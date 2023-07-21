package core.input

import core.model.{OrderModel, SortableModel, ValueWithIndexModel}

case class SortingAlgorithmUseCaseInput(
	toBeSorted: SortableModel[ValueWithIndexModel],
	ordering: OrderModel
)