package core.typeClass

import core.model.{BarModel, BarStateModel, OrderModel, SortableModel, SortingModel, ValueWithIndexModel}
import core.typeClass

trait GetBarModel[T <: SortingModel]:

	def getBarModel(
		valueWithIndex: ValueWithIndexModel,
		change: T
	): BarModel

object GetBarModel:

	given GetBarModel[SortingModel.BubbleSort] with
		override def getBarModel(
			valueWithIndex: ValueWithIndexModel,
			change: SortingModel.BubbleSort
		): BarModel =
			val isCorrectValueWithIndex = valueWithIndex == change.focusedValues._1 | valueWithIndex == change.focusedValues._2
			if (change.alreadySorted.contains(valueWithIndex))
				BarModel(valueWithIndex.value, BarStateModel.AlreadySorted)
			else if (isCorrectValueWithIndex && change.focusedIndicesChanged)
				BarModel(valueWithIndex.value, BarStateModel.Swapped)
			else if (isCorrectValueWithIndex)
				BarModel(valueWithIndex.value, BarStateModel.Focused)
			else BarModel(valueWithIndex.value, BarStateModel.Normal)