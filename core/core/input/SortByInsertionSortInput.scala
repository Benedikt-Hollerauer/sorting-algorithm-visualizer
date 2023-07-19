package core.input

import core.model.{OrderModel, SortableModel}

case class SortByInsertionSortInput( //TODO abstract this for all sorting algorithms
	toBeSorted: SortableModel,
	ordering: OrderModel
)