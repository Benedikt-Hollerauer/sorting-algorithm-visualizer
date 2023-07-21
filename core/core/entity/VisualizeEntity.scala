package core.entity

import core.model.*

object VisualizeEntity:

	def getBarVisualisation(
		sortedModel: SortedModel
	): VisualizeModel =
		val changes = sortedModel.changes
			.foldLeft(
				(sortedModel.toBeSorted, LazyList.empty[NonEmptyListModel[BarModel]])
			): (acc, change) =>
				val newSortable = change match
					case SortingModel.BubbleSort(focusedValues, alreadySorted, focusedIndicesChanged) =>
						if(focusedIndicesChanged) swapSortableValues(acc._1, (focusedValues._1, focusedValues._2))
						else acc._1
					case SortingModel.InsertionSort(focusedValues, currentPivot) => ???
				(
					newSortable,
					acc._2 :+ NonEmptyListModel.fromUnsafe(
						newSortable.valuesWithIndices
							.list
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
		sortableModel: SortableModel,
		barStateModel: BarStateModel
	): NonEmptyListModel[BarModel] =
		val sortedBars = sortableModel.valuesWithIndices
			.list
			.map: valueWithIndex =>
				BarModel(
					value = valueWithIndex.value,
					barState = barStateModel
				)
		NonEmptyListModel.fromUnsafe(sortedBars)

	def swapSortableValues(
		toBeUpdated: SortableModel,
		swappedValues: (ValueWithIndexModel, ValueWithIndexModel)
	): SortableModel =
		val list = toBeUpdated.valuesWithIndices.list
		val swapped = list.updated(
			list.indexWhere(_ == swappedValues._1), swappedValues._2
		).updated(
			list.indexWhere(_ == swappedValues._2), swappedValues._1
		)
		SortableModel.from(
			NonEmptyListModel.fromUnsafe(
				swapped
			)
		).toOption.get