package core.entity

import core.model.*
import core.typeClass.GetBarModel

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
	)(using getBarModel: GetBarModel[SortingModel]): BarModel =
		getBarModel.getBarModel(
			valueWithIndex = valueWithIndex,
			change = change
		)

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