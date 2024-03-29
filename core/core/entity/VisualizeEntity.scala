package core.entity

import core.model.*
import core.typeClass.{GetBarModel, GetBarVisualisation}

case class VisualizeEntity[T <: SortingModel]():

	def getBarVisualisation(
		sortedModel: SortedModel[T]
	)(
		using getBarVisualisation: GetBarVisualisation[T]
	)(
		using getBarModel: GetBarModel[T]
	): VisualizeModel =
		val changes = sortedModel.changes
			.foldLeft(
				(sortedModel.toBeSorted, LazyList.empty[SortableModel[BarModel]])
			): (acc, change) =>
				getBarVisualisation(
					acc,
					change,
					swapSortableValues,
					getBarModel.apply
				)
			._2
		VisualizeModel(
			notStartedSorting = getSpecialBars(
				sortedModel.toBeSorted,
				BarStateModel.Normal
			),
			changes = changes,
			finishedSorting = getSpecialBars(
				sortedModel.sorted,
				BarStateModel.FinishedSorting
			)
		)

	def getBarModel[T <: SortingModel](
		valueWithIndex: ValueWithIndexModel,
		change: T
	)(using getBarModel: GetBarModel[T]): BarModel =
		getBarModel(
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