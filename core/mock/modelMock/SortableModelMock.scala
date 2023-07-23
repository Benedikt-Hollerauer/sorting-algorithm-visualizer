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