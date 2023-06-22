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
		toBeCompared: List[ValueWithIndexModel],
		comparator: OrderModel,
	): List[SortingModel] =
		toBeCompared.scanLeft(SortingModel.empty):
			case (SortingModel((first, second), _), next) =>
				val (newFirst, newSecond, changed) = (first, second) match
					case (f, _) if f == ValueWithIndexModel.empty =>
						(next, f, false)
					case (f, s) if s == ValueWithIndexModel.empty && comparator.getOrdering(f.value, s.value) =>
						(f, next, false)
					case (f, s) if s == ValueWithIndexModel.empty =>
						(next, f, true)
					case (_, s) if comparator.getOrdering(s.value, next.value) =>
						(s, next, false)
					case _ =>
						(next, second, true)
				SortingModel((newFirst, newSecond), changed)
		.filter:
			case SortingModel((ValueWithIndexModel(_, IndexModel(index0)), ValueWithIndexModel(_, IndexModel(index1))), _) =>
				index0 != -1 && index1 != -1