package core.entity

import core.Contracts.SortingAlgorithmEntity
import core.model.{OrderModel, SortableModel, SortedModel}

object InsertionSortEntity extends SortingAlgorithmEntity:

	override def sortAscending(sortable: SortableModel): SortedModel = ???

	override def sortDescending(sortable: SortableModel): SortedModel = ???
	
	private def sort(
		ordering: OrderModel
	): SortedModel = ???