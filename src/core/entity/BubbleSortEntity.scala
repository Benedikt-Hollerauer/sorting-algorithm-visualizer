package core.entity

import core.value.OrderValue.{Ascending, Descending}
import core.value.{OrderValue, SortableValue}

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		sortOnceWithIntermediateResults(toBeSorted, OrderValue.Ascending)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		sortOnceWithIntermediateResults(toBeSorted, OrderValue.Descending)

	def sortOnceWithIntermediateResults(toBeSorted: SortableValue, ordering: OrderValue): LazyList[SortableValue] =
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
				list :: toBeSorted.list
					.map(List(_))
					.drop(list.length)
			).map(_.flatten)
				.map(SortableValue.from(_).toOption.get)