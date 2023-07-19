package core.model

sealed trait SortingModel

object SortingModel:

	case class BubbleSort(
		focusedValues: (ValueWithIndexModel, ValueWithIndexModel),
		alreadySorted: List[ValueWithIndexModel],
		focusedIndicesChanged: Boolean
	) extends SortingModel
	
	case class InsertionSort(
		focusedValues: (ValueWithIndexModel, ValueWithIndexModel),
	) extends SortingModel