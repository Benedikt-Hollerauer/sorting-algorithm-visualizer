package mock.modelMock

import core.Util.toValuesWithIndices
import core.model.{NonEmptyListModel, SortableModel}
import mock.ToBeSortedMock

	object SortableModelMock:
	
	val unsorted = SortableModel.from(
		mayBeValuesWithIndices = NonEmptyListModel.from(
			mayBeList = ToBeSortedMock.unsorted
				.toValuesWithIndices
		).toOption.get
	).toOption.get

	val sortedAscending = SortableModel.from(
		mayBeValuesWithIndices = NonEmptyListModel.from(
			mayBeList = ToBeSortedMock.ascendingOrder
				.sorted
				.toValuesWithIndices
		).toOption.get
	).toOption.get

	val sortedDescending = SortableModel.from(
		mayBeValuesWithIndices = NonEmptyListModel.from(
			mayBeList = ToBeSortedMock.descendingOrder
				.sorted
				.toValuesWithIndices
		).toOption.get
	).toOption.get