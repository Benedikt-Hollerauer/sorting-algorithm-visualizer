package core.model

sealed trait SortingModel private(
	focusedValues: (ValueWithIndexModel, ValueWithIndexModel),
):
	def getFocusedValues: (ValueWithIndexModel, ValueWithIndexModel)

object SortingModel:

	case class BubbleSort(
		focusedValues: (ValueWithIndexModel, ValueWithIndexModel),
		alreadySorted: List[ValueWithIndexModel],
		focusedIndicesChanged: Boolean
	) extends SortingModel(focusedValues):
		override def getFocusedValues: (ValueWithIndexModel, ValueWithIndexModel) = focusedValues
	
	case class InsertionSort(
		focusedValues: (ValueWithIndexModel, ValueWithIndexModel),
		currentPivot: ValueWithIndexModel,
		focusedIndicesChanged: Boolean = false,
		alreadySorted: List[ValueWithIndexModel] = List.empty
	) extends SortingModel(focusedValues):
		override def getFocusedValues: (ValueWithIndexModel, ValueWithIndexModel) = focusedValues