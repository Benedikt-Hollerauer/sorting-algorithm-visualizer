package core.input

import core.model.{OrderModel, SortableModel}

case class SortingAlgorithmUseCaseInput(
	toBeSorted: SortableModel,
	ordering: OrderModel
)