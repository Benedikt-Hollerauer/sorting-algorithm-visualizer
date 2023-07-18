package core.model

case class VisualizeModel(
	notStartedSorting: NonEmptyListModel[BarModel], 
	changes: LazyList[NonEmptyListModel[BarModel]],
	finishedSorting: NonEmptyListModel[BarModel]
)