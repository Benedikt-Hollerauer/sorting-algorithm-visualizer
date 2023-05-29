package core.model

case class SortedModel(
	sortableModel: SortableModel,
	changes: LazyList[SortingModel]
)