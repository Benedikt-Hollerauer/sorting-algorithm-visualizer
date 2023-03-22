package core.entity

import core.value.SortableValue

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(toBeSorted.list)
			.scanLeft(List.empty[Int])((acc, next) =>
				acc.lastOption match
					case Some(last) if last > next => (acc.dropRight(1) :+ next) :+ last
					case Some(last) => acc :+ next
					case None => acc :+ next
			).map(list =>
				list :: toBeSorted.list
					.map(List(_))
					.drop(list.length)
			).map(_.flatten)
				.map(SortableValue.from(_).toOption.get)

	def sortDescendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(toBeSorted.list)
			.scanLeft(List.empty[Int])((acc, next) =>
				acc.lastOption match
					case Some(last) if last < next => (acc.dropRight(1) :+ next) :+ last
					case Some(last) => acc :+ next
					case None => acc :+ next
			).map(list =>
				list :: toBeSorted.list
					.map(List(_))
					.drop(list.length)
			).map(_.flatten)
				.map(SortableValue.from(_).toOption.get)