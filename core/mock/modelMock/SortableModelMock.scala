package mock.modelMock

import core.Util.toValuesWithIndices
import core.model.SortableModel
import mock.ToBeSortedMock

	object SortableModelMock:

		val unsorted = SortableModel.fromUnsafe(
			mayBeList = ToBeSortedMock.unsorted
				.toValuesWithIndices
		)

		val sortedAscending = SortableModel.fromUnsafe(
			mayBeList = ToBeSortedMock.ascendingOrder
				.sorted
				.toValuesWithIndices
		)

		val sortedDescending = SortableModel.fromUnsafe(
			mayBeList = ToBeSortedMock.descendingOrder
				.sorted
				.toValuesWithIndices
		)

		private val useCaseList = List(636, 743, 5, 395, 266, 292, 662, 196, 267, 259)

		val unsortedForUseCases = SortableModel.fromUnsafe(
			mayBeList = useCaseList.toValuesWithIndices
		)

		val sortedAscendingForUseCases = SortableModel.fromUnsafe(
			mayBeList = useCaseList.sorted.toValuesWithIndices
		)

		val sortedDescendingForUseCases = SortableModel.fromUnsafe(
			mayBeList = useCaseList.sorted(Ordering[Int].reverse).toValuesWithIndices
		)
