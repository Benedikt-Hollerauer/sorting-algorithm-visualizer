package core.entity

import core.SortingAlgorithm
import core.model.*
import core.model.OrderModel.{Ascending, Descending}
import mock.modelMock.SortableModelMock

import scala.annotation.tailrec
import scala.util.Try

object BubbleSortEntity extends SortingAlgorithm:

	override def sortAscending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Ascending)

	override def sortDescending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Descending)

	@tailrec
	private def sort(
		valuesWithIndices: List[ValueWithIndexModel],
		sortable: SortableModel,
		comparator: OrderModel,
		changes: LazyList[SortingModel] = LazyList.empty[SortingModel],
		firstIteration: Boolean = true
	): SortedModel =
		valuesWithIndices match
			case Nil => SortedModel(
				sortable,
				changes
			)
			case valuesWithIndices =>
				val sortedOnce = sortOnce(valuesWithIndices, comparator)
				val newAcc = sortedOnce match
					case Some(it) => changes ++ it
					case None => changes
				val newValuesWithIndices = sortedOnce match
					case Some(it) =>
						if(it.isEmpty) Nil
						else
							val newIt = it.map(_.focusedIndices._1) :+ it.last.focusedIndices._2
							comparator match
								case OrderModel.Ascending => newIt.filterNot(_ == valuesWithIndices.max)
								case OrderModel.Descending => newIt.filterNot(_ == valuesWithIndices.min)
					case None => Nil
				sort(
					newValuesWithIndices,
					sortable,
					comparator,
					newAcc,
					false
				)

	def sortOnce(
		toBeCompared: List[ValueWithIndexModel],
		comparator: OrderModel,
	): Option[List[SortingModel]] =
		Try(
			toBeCompared.tail
				.foldLeft(
					(List.empty[SortingModel], toBeCompared.head)
				):
					case ((acc, f), s) if comparator.getOrdering(f.value, s.value) =>
						(acc :+ SortingModel((f, s), false), s)
					case ((acc, f), s) => (acc :+ SortingModel((s, f), true), f)
				._1
		).toOption