package core.entity

import core.model.*

object VisualizeEntity:

	def getBarVisualisation(
		sortedModel: SortedModel
	): VisualizeModel =
		val changes = sortedModel.changes
			.foldLeft(
				(sortedModel.toBeSorted, LazyList.empty[SortableModel[BarModel]])
			): (acc, change) =>
				val newSortable = change match
					case SortingModel.BubbleSort(focusedValues, alreadySorted, focusedIndicesChanged) =>
						if(focusedIndicesChanged) swapSortableValues(acc._1, (focusedValues._1, focusedValues._2))
						else acc._1
					case SortingModel.InsertionSort(focusedValues, currentPivot) => ???
				(
					newSortable,
					acc._2 :+ SortableModel.fromUnsafe(
						newSortable.list
							.map: valueWithIndex =>
								getBarModel(valueWithIndex, change)
					)
				)
			._2
		VisualizeModel(
			notStartedSorting = getSpecialBars(sortedModel.toBeSorted, BarStateModel.Normal),
			changes = changes,
			finishedSorting = getSpecialBars(sortedModel.sorted, BarStateModel.FinishedSorting)
		)

	def getBarModel(
		valueWithIndex: ValueWithIndexModel,
		change: SortingModel
	): BarModel =
		change match
			case SortingModel.BubbleSort(focusedValues, alreadySorted, focusedIndicesChanged) =>
				val isCorrectValueWithIndex = valueWithIndex == focusedValues._1 | valueWithIndex == focusedValues._2
				if(alreadySorted.contains(valueWithIndex))
					BarModel(valueWithIndex.value, BarStateModel.AlreadySorted)
				else if(isCorrectValueWithIndex && focusedIndicesChanged)
					BarModel(valueWithIndex.value, BarStateModel.Swapped)
				else if(isCorrectValueWithIndex)
					BarModel(valueWithIndex.value, BarStateModel.Focused)
				else BarModel(valueWithIndex.value, BarStateModel.Normal)
			case SortingModel.InsertionSort(focusedValues, currentPivot) => ???

	def getSpecialBars(
		sortableModel: SortableModel[ValueWithIndexModel],
		barStateModel: BarStateModel
	): SortableModel[BarModel] =
		val sortedBars = sortableModel.list
			.map: valueWithIndex =>
				BarModel(
					value = valueWithIndex.value,
					barState = barStateModel
				)
		SortableModel.fromUnsafe(sortedBars)

	def swapSortableValues(
		toBeUpdated: SortableModel[ValueWithIndexModel],
		swappedValues: (ValueWithIndexModel, ValueWithIndexModel)
	): SortableModel[ValueWithIndexModel] =
		val list = toBeUpdated.list
		val swapped = list.updated(
			list.indexWhere(_ == swappedValues._1), swappedValues._2
		).updated(
			list.indexWhere(_ == swappedValues._2), swappedValues._1
		)
		SortableModel.fromUnsafe(
			swapped
		)