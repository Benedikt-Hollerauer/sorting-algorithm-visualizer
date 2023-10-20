package core.entity

import core.Contract.SortingAlgorithmEntity
import core.Util
import core.model.*

import scala.annotation.tailrec
import scala.collection.immutable.List

object InsertionSortEntity extends SortingAlgorithmEntity[SortingModel.InsertionSort]:

	override def sortAscending(toBeSorted: SortableModel[ValueWithIndexModel]): SortedModel[SortingModel.InsertionSort] =
		sort(toBeSorted, OrderModel.Ascending)

	override def sortDescending(toBeSorted: SortableModel[ValueWithIndexModel]): SortedModel[SortingModel.InsertionSort] =
		sort(toBeSorted, OrderModel.Descending)

	private def sort(
		toBeSorted: SortableModel[ValueWithIndexModel],
		ordering: OrderModel
	): SortedModel[SortingModel.InsertionSort] =
		case class Acc(
			changesAcc: List[SortingModel.InsertionSort],
			newToBeSortedOnce: List[ValueWithIndexModel]
		)
		val sortedAcc = toBeSorted
			.list
			.drop(2)
			.foldLeft(
				Acc(
					changesAcc = List.empty[SortingModel.InsertionSort],
					newToBeSortedOnce = toBeSorted.list.take(2)
				)
			):
				case (
					Acc(changesAcc, newToBeSortedOnce),
					next
				) =>
					val sortedSubListOnce = sortSubListOnce(
						subList = SortableModel.fromUnsafe(newToBeSortedOnce),
						currentPivot = newToBeSortedOnce.last,
						ordering = ordering
					)
					Acc(
						changesAcc = changesAcc ++ sortedSubListOnce,
						newToBeSortedOnce = Util.toValuesWithIndicesFromSortingModel(sortedSubListOnce).get
					)
		SortedModel(
			toBeSorted = toBeSorted,
			changes = LazyList.from(
				sortedAcc.changesAcc
			),
			sorted = toBeSorted.getSorted(ordering)
		)

	def sortSubListOnce(
		subList: SortableModel[ValueWithIndexModel],
		currentPivot: ValueWithIndexModel,
		ordering: OrderModel,
		focusedValuesAcc: List[(ValueWithIndexModel, ValueWithIndexModel)] = List.empty[(ValueWithIndexModel, ValueWithIndexModel)],
	): List[SortingModel.InsertionSort] =
		@tailrec
		def getFocusedValues(
			subList: List[ValueWithIndexModel],
			ordering: OrderModel,
			focusedValuesAcc: List[(ValueWithIndexModel, ValueWithIndexModel)] = List.empty[(ValueWithIndexModel, ValueWithIndexModel)]
		): List[(ValueWithIndexModel, ValueWithIndexModel)] =
			subList match
				case f :: s :: Nil => getFocusedValues(
					subList = Nil,
					ordering = ordering,
					focusedValuesAcc = focusedValuesAcc :+ (f, s)
				)
				case f :: s :: t if ordering.getOrdering(f.value, s.value) => getFocusedValues(
					subList = f :: t,
					ordering = ordering,
					focusedValuesAcc = focusedValuesAcc :+ (f, s)
				)
				case f :: s :: t => getFocusedValues(
					subList = Nil,
					ordering = ordering,
					focusedValuesAcc = focusedValuesAcc :+ (f, s)
				)
				case f :: t => getFocusedValues(
					subList = Nil,
					ordering = ordering,
					focusedValuesAcc = focusedValuesAcc :+ (focusedValuesAcc.last._1, f)
				)
				case _ => focusedValuesAcc
		getFocusedValues(
			subList = subList.list.reverse,
			ordering = ordering
		).map: focusedValues =>
			SortingModel.InsertionSort(
				focusedValues = focusedValues,
				currentPivot = currentPivot
			)