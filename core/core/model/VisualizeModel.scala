package core.model

case class VisualizeModel(
	notStartedSorting: SortableModel[BarModel], 
	changes: LazyList[SortableModel[BarModel]],
	finishedSorting: SortableModel[BarModel]
)