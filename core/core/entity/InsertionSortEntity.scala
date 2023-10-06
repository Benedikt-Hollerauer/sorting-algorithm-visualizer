package core.entity

import core.Contract.SortingAlgorithmEntity
import core.model.*

import scala.annotation.tailrec

object InsertionSortEntity extends SortingAlgorithmEntity[SortingModel.InsertionSort]:

	override def sortAscending(sortable: SortableModel[ValueWithIndexModel]): SortedModel[SortingModel.InsertionSort] =
		sort(sortable, OrderModel.Ascending)

	override def sortDescending(sortable: SortableModel[ValueWithIndexModel]): SortedModel[SortingModel.InsertionSort] =
		sort(sortable, OrderModel.Descending)

	private def sort(
		sortable: SortableModel[ValueWithIndexModel],
		ordering: OrderModel,
		subListLength: Int = 2,
		sortedAcc: LazyList[SortingModel.InsertionSort] = LazyList.empty[SortingModel.InsertionSort],
		firstIteration: Boolean = true
	): SortedModel[SortingModel.InsertionSort] =
		if(sortable.list.length == subListLength)
			SortedModel(
				toBeSorted = sortable,
				changes = sortedAcc,
				sorted = sortable.getSorted(ordering)
			)
		else
			val newBaseForSortingOnce =
				if (firstIteration) sortable.list.take(subListLength)
				else (sortedAcc.map(_.focusedValues._1)
					:+ sortedAcc.last.focusedValues._2)
					++ sortable.list
					.takeRight(sortable.list.length - subListLength)
			val newSubList = newBaseForSortingOnce.take(subListLength)
			val sortedSubListOnce = sortSubListOnce(
				subList = SortableModel.fromUnsafe(newSubList.toList), // TODO here needs to be some error handling probably
				currentPivot = newSubList.takeRight(2).head,
				ordering = ordering
			)
			sort(
				sortable = sortable,
				ordering = ordering,
				subListLength = subListLength + 1,
				sortedAcc = sortedAcc ++ sortedSubListOnce,
				firstIteration = false
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