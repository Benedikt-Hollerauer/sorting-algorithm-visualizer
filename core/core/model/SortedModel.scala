package core.model

case class SortedModel(
	toBeSorted: SortableModel[ValueWithIndexModel],
	changes: LazyList[SortingModel],
	sorted: SortableModel[ValueWithIndexModel]
)