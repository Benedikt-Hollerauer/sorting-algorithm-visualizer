package core.typeClass

import core.model.*
import core.typeClass

trait GetBarModel[T]:

	def apply(
		valueWithIndex: ValueWithIndexModel,
		change: T
	): BarModel

object GetBarModel:

	given GetBarModel[SortingModel.BubbleSort] with
		override def apply(
			valueWithIndex: ValueWithIndexModel,
			change: SortingModel.BubbleSort
		): BarModel =
			val isCorrectValueWithIndex = valueWithIndex == change.focusedValues._1 | valueWithIndex == change.focusedValues._2
			if(change.alreadySorted.contains(valueWithIndex))
				BarModel(valueWithIndex.value, BarStateModel.AlreadySorted)
			else if(isCorrectValueWithIndex && change.focusedIndicesChanged)
				BarModel(valueWithIndex.value, BarStateModel.Swapped)
			else if(isCorrectValueWithIndex)
				BarModel(valueWithIndex.value, BarStateModel.Focused)
			else BarModel(valueWithIndex.value, BarStateModel.Normal)

	given GetBarModel[SortingModel.InsertionSort] with
		override def apply(
			valueWithIndex: ValueWithIndexModel,
			change: SortingModel.InsertionSort
		): BarModel =
			val isCorrectValueWithIndex = valueWithIndex == change.focusedValues._1 | valueWithIndex == change.focusedValues._2
			if(isCorrectValueWithIndex)
				BarModel(valueWithIndex.value, BarStateModel.Focused)
			else if(change.currentPivot == valueWithIndex)
				BarModel(valueWithIndex.value, BarStateModel.Swapped) // TODO: there has to be a custom current pivon barstatemodel
			else BarModel(valueWithIndex.value, BarStateModel.Normal)
