package core.model

case class VisualizeModel(
	changes: LazyList[NonEmptyListModel[BarModel]],
	finishedSorting: NonEmptyListModel[BarModel]
)