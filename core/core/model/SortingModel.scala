package core.model

sealed class SortingModel private()

object SortingModel:

	case class BubbleSort(
		focusedValues: (ValueWithIndexModel, ValueWithIndexModel),
		alreadySorted: List[ValueWithIndexModel],
		focusedIndicesChanged: Boolean
	) extends SortingModel
	
	case class InsertionSort(
		focusedValues: (ValueWithIndexModel, ValueWithIndexModel),
		currentPivot: ValueWithIndexModel
	) extends SortingModel