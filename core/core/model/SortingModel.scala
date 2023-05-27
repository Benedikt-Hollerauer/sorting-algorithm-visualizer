package core.model

case class SortingModel(
	focusedIndices: (ValueWithIndexModel, ValueWithIndexModel),
	focusedIndicesChanged: Boolean
)

object SortingModel:
	
	def empty: SortingModel =
		SortingModel(
			focusedIndices = (
				ValueWithIndexModel.empty,
				ValueWithIndexModel.empty
			),
			focusedIndicesChanged = false
		)