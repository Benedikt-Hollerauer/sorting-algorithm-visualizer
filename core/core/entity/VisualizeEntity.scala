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
				val newSortable =
					if(change.focusedIndicesChanged) swapSortableValues(acc._1, (change.focusedIndices._1, change.focusedIndices._2))
					else acc._1
				(
					newSortable,
					acc._2 :+ NonEmptyListModel.from(
						newSortable.valuesWithIndices
							.list
							.map: valueWithIndex =>
								getBarModel(valueWithIndex, change)
					).toOption.get
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
		val isCorrectValueWithIndex = valueWithIndex == change.focusedIndices._1 | valueWithIndex == change.focusedIndices._2
		if(change.alreadySorted.contains(valueWithIndex))
			BarModel(valueWithIndex.value, BarStateModel.AlreadySorted)
		else if (isCorrectValueWithIndex && change.focusedIndicesChanged)
			BarModel(valueWithIndex.value, BarStateModel.Swapped)
		else if(isCorrectValueWithIndex)
			BarModel( valueWithIndex.value, BarStateModel.Focused)
		else BarModel(valueWithIndex.value, BarStateModel.Normal)

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
		NonEmptyListModel.from(sortedBars).toOption.get

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
			NonEmptyListModel.from(
				swapped
			).toOption.get
		).toOption.get