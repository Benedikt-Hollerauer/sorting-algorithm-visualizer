package core.entity

import core.value.SortableValue

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		def sort(toBeSorted: List[Int], intermediateResults: List[List[Int]]): List[Int] =
			toBeSorted match
				case f :: s :: t if f > s => s +: sort(f :: t, intermediateResults.appended(s :: f :: t))
				case f :: s :: t => f +: sort(s :: t, intermediateResults.appended(f :: s :: t))
				case _ =>
					intermediateResults.foreach(println)
					toBeSorted

		LazyList(SortableValue.from(sort(toBeSorted.list, List.empty[List[Int]])).toOption.get)