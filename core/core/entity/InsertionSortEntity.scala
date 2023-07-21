package core.entity

import core.Contract.SortingAlgorithmEntity
import core.model.*

object InsertionSortEntity extends SortingAlgorithmEntity:

	override def sortAscending(sortable: SortableModel[ValueWithIndexModel]): SortedModel = ???

	override def sortDescending(sortable: SortableModel[ValueWithIndexModel]): SortedModel = ???
	
	private def sort(
		ordering: OrderModel
	): SortedModel = ???

	def sortSubListOnce(
		subList: SortableModel[ValueWithIndexModel],
		currentPivot: ValueWithIndexModel,
		focusedValuesAcc: List[(ValueWithIndexModel, ValueWithIndexModel)] = List.empty[(ValueWithIndexModel, ValueWithIndexModel)],
		ordering: OrderModel
	): List[SortingModel.InsertionSort] =
		val test = List(6, 3, 1, 2)
		val test3 = List(2, 1, 3, 6)
		subList.list match
			case f :: s :: t if !ordering.getOrdering(f.value, s.value) =>
				sortSubListOnce(
					SortableModel.fromUnsafe(f +: t),
					currentPivot,
					focusedValuesAcc :+ (f, s),
					ordering
				)
			case _ => focusedValuesAcc.map: focusedValues =>
				SortingModel.InsertionSort(
					focusedValues = focusedValues,
					currentPivot = currentPivot
				)