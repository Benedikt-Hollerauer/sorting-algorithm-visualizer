package mock.modelMock

import core.Util.toValuesWithIndices
import core.model.{SortableModel, SortableModelOld}
import mock.ToBeSortedMock

	object SortableModelMock:
	
	val unsorted = SortableModelOld.from(
		mayBeValuesWithIndices = SortableModel.fromUnsafe(
			mayBeList = ToBeSortedMock.unsorted
				.toValuesWithIndices
		)
	).toOption.get

	val sortedAscending = SortableModelOld.from(
		mayBeValuesWithIndices = SortableModel.fromUnsafe(
			mayBeList = ToBeSortedMock.ascendingOrder
				.sorted
				.toValuesWithIndices
		)
	).toOption.get

	val sortedDescending = SortableModelOld.from(
		mayBeValuesWithIndices = SortableModel.fromUnsafe(
			mayBeList = ToBeSortedMock.descendingOrder
				.sorted
				.toValuesWithIndices
		)
	).toOption.get