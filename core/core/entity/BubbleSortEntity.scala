package core.entity

import core.SortingAlgorithm
import core.model.OrderModel.{Ascending, Descending}
import core.model.{IndexModel, NonEmptyListModel, OrderModel, SortableModel, SortedModel, SortedModel2, SortingModel, ValueWithIndexModel}
import mock.modelMock.SortableModelMock

object BubbleSortEntity extends SortingAlgorithm:

	override def sortAscending(sortable: SortableModel): SortedModel =
		val valueWithIndexModelMock = ValueWithIndexModel(
			value = 0,
			indexModel = IndexModel.from(
				mayBeIndex = 0
			).toOption.get
		)
		val res = sortable.valuesWithIndices
			.list
			.scanLeft(
				SortingModel(
					focusedIndices = (
						valueWithIndexModelMock,
						valueWithIndexModelMock
					),
					focusedIndicesChanged = false
				)
			): (first, second) =>
				if(first.focusedIndices._2.value <= second.value) SortingModel(
					focusedIndices = (
						first.focusedIndices._2,
						second
					),
					focusedIndicesChanged = false
				)
				else SortingModel(
					focusedIndices = (
						second,
						first.focusedIndices._2
					),
					focusedIndicesChanged = true
				)
		res.foreach(println)
		SortedModel.from(
			sortable = SortableModelMock.sortable,
			focusedIndicesChanged = false,
			mayBeFocusedIndices = List(1, 3)
		).toOption.get


	override def sortDescending(sortable: SortableModel): SortedModel = ???

	def sortAscendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Ascending)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortedModel] =
		sortWithIntermediateResults(toBeSorted, OrderModel.Descending)

	def sortWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted.valuesWithIndices.list)
			.foldLeft(
				LazyList.empty[SortedModel]
			): (acc, _) =>
				acc.lastOption match
					case Some(last) => acc ++ sortOnceWithIntermediateResults(last.sortableWithIndex, ordering)
					case None => sortOnceWithIntermediateResults(toBeSorted.valuesWithIndices.list, ordering) // ToDo return a error if this fails

	def sortOnceWithIntermediateResults(toBeSorted: List[ValueWithIndexModel], ordering: OrderModel): LazyList[SortedModel] =
		LazyList.from(toBeSorted)
			.scanLeft(
				SortedModel.from(
					List.empty[ValueWithIndexModel],
					List(0),
					false
				).toOption.get
			): (acc, next) =>
				swapByOrdering(acc, next, ordering)
			.map: sortedModel =>
				SortedModel.from(
					sortedModel.sortableWithIndex
						.++(toBeSorted.drop(sortedModel.sortableWithIndex.length)),
					sortedModel.focusedIndices,
					sortedModel.focusedIndicesChanged
				).toOption.get

	def swapByOrdering(acc: SortedModel, next: ValueWithIndexModel, ordering: OrderModel): SortedModel =
		def swapNeeded(last: ValueWithIndexModel): Boolean = ordering match
			case Ascending => last.value > next.value
			case Descending => last.value < next.value

		acc.sortableWithIndex.lastOption match
			case Some(last) if swapNeeded(last) =>
				SortedModel.from(
					(acc.sortableWithIndex.dropRight(1) :+ next) :+ last,
					List(last.indexModel.index, next.indexModel.index),
					true
				).toOption.get
			case Some(last) =>
				SortedModel.from(
					acc.sortableWithIndex :+ next,
					List(last.indexModel.index, next.indexModel.index),
					false
				).toOption.get
			case _ =>
				SortedModel.from(
					acc.sortableWithIndex :+ next,
					List(next.indexModel.index),
					false
				).toOption.get