package core.entity

import core.SortingAlgorithm
import core.model.*
import core.model.OrderModel.{Ascending, Descending}
import mock.modelMock.SortableModelMock

object BubbleSortEntity extends SortingAlgorithm:

	override def sortAscending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Ascending)

	override def sortDescending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Descending)

	private def sort(
		valuesWithIndices: List[ValueWithIndexModel],
		sortable: SortableModel,
		comparator: OrderModel,
		acc: LazyList[SortingModel] = LazyList.empty[SortingModel],
		firstIteration: Boolean = true
	): SortedModel =
		valuesWithIndices match
			case Nil => SortedModel(
				sortable,
				acc
			)
			case valuesWithIndices =>
				val newValuesWithIndices =
					if(firstIteration) valuesWithIndices
					else comparator match
						case OrderModel.Ascending => valuesWithIndices.filterNot(_ == valuesWithIndices.max)
						case OrderModel.Descending => valuesWithIndices.filterNot(_ == valuesWithIndices.min)
				sort(
					newValuesWithIndices,
					sortable,
					comparator,
					acc ++ sortOnce(newValuesWithIndices, comparator),
					false
				)

	def sortOnce(
		toBeCompared: List[ValueWithIndexModel], //TODO use NonEmptyListModel here (here non empty list would have to have at least 2 values)
		comparator: OrderModel,
	): List[SortingModel] =
		toBeCompared.tail
			.foldLeft(
				(List.empty[SortingModel], toBeCompared.head)
			):
				case ((acc, f), s) if comparator.getOrdering(f.value, s.value) =>
					(acc :+ SortingModel((f, s), false), s)
				case ((acc, f), s) => (acc :+ SortingModel((s, f), true), f)
			._1