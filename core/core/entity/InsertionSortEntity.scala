package core.entity

import core.Contracts.SortingAlgorithmEntity
import core.model.{OrderModel, SortableModel, SortedModel, SortingModel, ValueWithIndexModel}

object InsertionSortEntity extends SortingAlgorithmEntity:

	override def sortAscending(sortable: SortableModel): SortedModel = ???

	override def sortDescending(sortable: SortableModel): SortedModel = ???
	
	private def sort(
		ordering: OrderModel
	): SortedModel = ???

	def sortSubListOnce(
		subList: List[ValueWithIndexModel],
		acc: List[(ValueWithIndexModel, ValueWithIndexModel)] = List.empty[(ValueWithIndexModel, ValueWithIndexModel)],
		ordering: OrderModel
	): List[SortingModel.InsertionSort] = ???
