package core.entity

import core.value.SortableValue

object BubbleSortEntity:

	def sortAscendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		def sort(toBeSorted: List[Int], intermediateResults: List[List[Int]]): List[Int] =
			toBeSorted match
				case f :: s :: t if f > s => s +: sort(f :: t, intermediateResults.appended(s :: f :: t))
				case f :: s :: t => f +: sort(s :: t, intermediateResults.appended(f :: s :: t))
				case _ => toBeSorted

		LazyList(SortableValue.from(sort(toBeSorted.list, List.empty[List[Int]])).toOption.get)
		
	def sortDescendingWithIntermediateResults(toBeSorted: SortableValue): LazyList[SortableValue] =
		LazyList.from(
			toBeSorted.list
				.scanLeft(toBeSorted.list)((x, _) =>
					x match
						case f :: s :: t if f < s => f :: s :: t
						case f :: s :: t => f :: s :: t
						case it => it
				).map(x => SortableValue.from(x).toOption.get)
		)