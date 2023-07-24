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
		val test = List(1, 3, 6, 2)
		subList.list
			.reverse
			.tail
			.foldLeft(
				List.empty[SortingModel.InsertionSort], subList.list.head
			):
				case ((acc, f), s) if ordering.getOrdering(f.value, s.value) => (
					acc :+ SortingModel.InsertionSort(
						focusedValues = (f, s),
						currentPivot = s
					),
					s
				)
				case ((acc, f), s) => (
					acc :+ SortingModel.InsertionSort(
						focusedValues = (f, s),
						currentPivot = f
					),
					f
				)
			._1