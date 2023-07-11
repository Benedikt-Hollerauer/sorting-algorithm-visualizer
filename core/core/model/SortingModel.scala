package core.model

case class SortingModel(
	focusedIndices: (ValueWithIndexModel, ValueWithIndexModel),
	focusedIndicesChanged: Boolean
)