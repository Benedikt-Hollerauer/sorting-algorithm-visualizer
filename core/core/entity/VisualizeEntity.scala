package core.entity

import core.model.*
import core.typeClass.{GetBarModel, GetBarVisualisation}
import core.typeClass.GetBarVisualisation.{given, *}
import core.typeClass.GetBarModel.{given, *}

object VisualizeEntity:

	def getBarVisualisation(
		sortedModel: SortedModel
	)(
		using getBarVisualisation: GetBarVisualisation[SortingModel]
	)(
		using getBarModel: GetBarModel[SortingModel]
	): VisualizeModel =
		val changes = sortedModel.changes
			.foldLeft(
				(sortedModel.toBeSorted, LazyList.empty[SortableModel[BarModel]])
			): (acc, change) =>
				getBarVisualisation.getBarVisualisation(
					acc,
					change,
					swapSortableValues,
					getBarModel.getBarModel
				)
			._2
		VisualizeModel(
			notStartedSorting = getSpecialBars(sortedModel.toBeSorted, BarStateModel.Normal),
			changes = changes,
			finishedSorting = getSpecialBars(sortedModel.sorted, BarStateModel.FinishedSorting)
		)

	def getBarModel[T <: SortingModel](
		valueWithIndex: ValueWithIndexModel,
		change: T
	)(using getBarModel: GetBarModel[T]): BarModel =
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