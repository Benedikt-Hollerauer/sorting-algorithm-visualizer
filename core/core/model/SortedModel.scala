package core.model

case class SortedModel[T <: SortingModel](
	toBeSorted: SortableModel[ValueWithIndexModel],
	changes: LazyList[T],
	sorted: SortableModel[ValueWithIndexModel]
)