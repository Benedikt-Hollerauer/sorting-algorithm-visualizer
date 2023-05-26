package core.model

case class SortingModel(
	focusedIndices: NonEmptyListModel[ValueWithIndexModel],
	focusedIndicesChanged: Boolean
)