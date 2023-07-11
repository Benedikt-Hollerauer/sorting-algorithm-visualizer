package core.model

case class SortingModel(
	focusedIndices: (ValueWithIndexModel, ValueWithIndexModel),
	alreadySorted: List[ValueWithIndexModel],
	focusedIndicesChanged: Boolean
)