package core.entity

import core.Contract.SortingAlgorithmEntity
import core.model.*

import scala.annotation.tailrec

object InsertionSortEntity extends SortingAlgorithmEntity:

	override def sortAscending(sortable: SortableModel[ValueWithIndexModel]): SortedModel = ???

	override def sortDescending(sortable: SortableModel[ValueWithIndexModel]): SortedModel = ???
	
	private def sort(
		ordering: OrderModel
	): SortedModel = ???

	def sortSubListOnce(
		subList: SortableModel[ValueWithIndexModel],
		currentPivot: ValueWithIndexModel,
		ordering: OrderModel,
		focusedValuesAcc: List[(ValueWithIndexModel, ValueWithIndexModel)] = List.empty[(ValueWithIndexModel, ValueWithIndexModel)],
	): List[SortingModel.InsertionSort] =
		@tailrec
		def helper(
			subList: List[ValueWithIndexModel],
			ordering: OrderModel,
			focusedValuesAcc: List[(ValueWithIndexModel, ValueWithIndexModel)] = List.empty[(ValueWithIndexModel, ValueWithIndexModel)]
		): List[(ValueWithIndexModel, ValueWithIndexModel)] = //TODO I think this can throw with an empty input
			subList match
				case f :: s :: t if ordering.getOrdering(f.value, s.value) => helper(
					subList = f :: t,
					ordering = ordering,
					focusedValuesAcc = focusedValuesAcc :+ (f, s)
				)
				case f :: t => helper(
					subList = Nil,
					ordering = ordering,
					focusedValuesAcc = focusedValuesAcc :+ (focusedValuesAcc.last._2, f)
				)
				case _ => focusedValuesAcc

		helper(
			subList = subList.list.reverse,
			ordering = ordering
		).map: focusedValues =>
			SortingModel.InsertionSort(
				focusedValues = focusedValues,
				currentPivot = currentPivot
			)