package mock.modelMock

import core.Util.toValuesWithIndices
import core.model.{NonEmptyListModel, SortableModel}
import mock.ToBeSortedMock

	object SortableModelMock:
	
	val unsorted = SortableModel.from(
		mayBeValuesWithIndices = NonEmptyListModel.fromUnsafe(
			mayBeList = ToBeSortedMock.unsorted
				.toValuesWithIndices
		)
	).toOption.get

	val sortedAscending = SortableModel.from(
		mayBeValuesWithIndices = NonEmptyListModel.fromUnsafe(
			mayBeList = ToBeSortedMock.ascendingOrder
				.sorted
				.toValuesWithIndices
		)
	).toOption.get

	val sortedDescending = SortableModel.from(
		mayBeValuesWithIndices = NonEmptyListModel.fromUnsafe(
			mayBeList = ToBeSortedMock.descendingOrder
				.sorted
				.toValuesWithIndices
		)
	).toOption.get