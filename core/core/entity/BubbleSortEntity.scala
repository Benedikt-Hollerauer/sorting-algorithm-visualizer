package core.entity

import core.Contracts.SortingAlgorithmEntity
import core.model.*
import core.model.OrderModel.{Ascending, Descending}
import mock.modelMock.SortableModelMock

import scala.annotation.tailrec
import scala.util.Try

object BubbleSortEntity extends SortingAlgorithmEntity:

	override def sortAscending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Ascending)

	override def sortDescending(sortable: SortableModel): SortedModel =
		sort(sortable.valuesWithIndices.list, sortable, OrderModel.Descending)

	@tailrec
	private def sort(
		valuesWithIndices: List[ValueWithIndexModel],
		sortable: SortableModel,
		ordering: OrderModel,
		changes: LazyList[SortingModel.BubbleSort] = LazyList.empty[SortingModel.BubbleSort],
		alreadySorted: List[ValueWithIndexModel] = List.empty[ValueWithIndexModel],
		firstIteration: Boolean = true
	): SortedModel =
		valuesWithIndices match
			case Nil => SortedModel(
				sortable,
				changes,
				sortable.getSorted(ordering)
			)
			case valuesWithIndices =>
				val sortedOnce = sortOnce(valuesWithIndices, alreadySorted, ordering)
				val newAcc = sortedOnce match
					case Some(it) => changes ++ it
					case None => changes
				val newValuesWithIndices = sortedOnce match
					case Some(it) =>
						if(it.isEmpty) Nil
						else
							val newIt = it.map(_.focusedValues._1) :+ it.last.focusedValues._2
							ordering match
								case OrderModel.Ascending => newIt.filterNot(_ == valuesWithIndices.max)
								case OrderModel.Descending => newIt.filterNot(_ == valuesWithIndices.min)
					case None => Nil
				val newAlreadySorted = alreadySorted :+ (
					ordering match
						case OrderModel.Ascending => valuesWithIndices.max
						case OrderModel.Descending => valuesWithIndices.min
				)
				sort(
					newValuesWithIndices,
					sortable,
					ordering,
					newAcc,
					newAlreadySorted,
					false
				)

	def sortOnce(
		toBeCompared: List[ValueWithIndexModel],
		alreadySorted: List[ValueWithIndexModel],
		ordering: OrderModel
	): Option[List[SortingModel.BubbleSort]] =
		Try(
			toBeCompared.tail
				.foldLeft(
					(List.empty[SortingModel.BubbleSort], toBeCompared.head)
				):
					case ((acc, f), s) if ordering.getOrdering(f.value, s.value) =>
						(acc :+ SortingModel.BubbleSort((f, s), alreadySorted, false), s)
					case ((acc, f), s) =>
						(acc :+ SortingModel.BubbleSort((s, f), alreadySorted, true), f)
				._1
		).toOption