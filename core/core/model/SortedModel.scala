package core.model

case class SortedModel(
	toBeSorted: SortableModel,
	changes: LazyList[SortingModel]
)