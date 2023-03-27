package core.entity

import core.model.OrderValue.{Ascending, Descending}
import core.model.{OrderValue, SortableModel}

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortableModel] =
		LazyList.from(toBeSorted.list)
			.foldLeft(LazyList.empty[SortableModel])((acc, _) =>
				acc.lastOption match
					case Some(last) => acc ++ sortOnceWithIntermediateResults(last, OrderValue.Ascending)
					case None => sortOnceWithIntermediateResults(toBeSorted, OrderValue.Ascending)
			)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableModel): LazyList[SortableModel] =
		LazyList.from(toBeSorted.list)
			.foldLeft(LazyList.empty[SortableModel])((acc, _) =>
				acc.lastOption match
					case Some(last) => acc ++ sortOnceWithIntermediateResults(last, OrderValue.Descending)
					case None => sortOnceWithIntermediateResults(toBeSorted, OrderValue.Descending)
			)

	def sortOnceWithIntermediateResults(toBeSorted: SortableModel, ordering: OrderValue): LazyList[SortableModel] =
		LazyList.from(toBeSorted.list)
			.scanLeft(List.empty[Int])((acc, next) =>
				ordering match
					case Ascending =>
						acc.lastOption match
							case Some(last) if last > next => (acc.dropRight(1) :+ next) :+ last
							case _ => acc :+ next
					case Descending =>
						acc.lastOption match
							case Some(last) if last < next => (acc.dropRight(1) :+ next) :+ last
							case _ => acc :+ next
			).map(list =>
				list ++ toBeSorted.list
					.drop(list.length)
			).map(SortableModel.from(_).toOption.get)