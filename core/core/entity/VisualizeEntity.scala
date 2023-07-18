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
								getBar(valueWithIndex, change)
					).toOption.get
				)
			._2
		VisualizeModel(
			notStartedSorting = getNotStartedSortingBars(sortedModel.toBeSorted),
			changes = changes,
			finishedSorting = getFinishedSortingBars(sortedModel.sorted)
		)

	private def getBar(valueWithIndex: ValueWithIndexModel, change: SortingModel): BarModel =
		val isCorrectValueWithIndex = valueWithIndex == change.focusedIndices._1 | valueWithIndex == change.focusedIndices._2
		if(change.alreadySorted.contains(valueWithIndex))
			BarModel(valueWithIndex.value, BarStateModel.AlreadySorted)
		else if (isCorrectValueWithIndex && change.focusedIndicesChanged)
			BarModel(valueWithIndex.value, BarStateModel.Swapped)
		else if(isCorrectValueWithIndex)
			BarModel( valueWithIndex.value, BarStateModel.Focused)
		else BarModel(valueWithIndex.value, BarStateModel.Normal)

	private def getFinishedSortingBars(finishedSorting: SortableModel): NonEmptyListModel[BarModel] = //TODO make the two similar methods to one
		val sortedBars = finishedSorting.valuesWithIndices
			.list
			.map: valueWithIndex =>
				BarModel(
					value = valueWithIndex.value,
					barState = BarStateModel.FinishedSorting
				)
		NonEmptyListModel.from(sortedBars).toOption.get

	private def getNotStartedSortingBars(notStartedSorting: SortableModel): NonEmptyListModel[BarModel] =
		val sortedBars = notStartedSorting.valuesWithIndices
			.list
			.map: valueWithIndex =>
				BarModel(
					value = valueWithIndex.value,
					barState = BarStateModel.Normal
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