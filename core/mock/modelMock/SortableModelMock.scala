package mock.modelMock

import core.model.{NonEmptyListModel, SortableModel}
import mock.ToBeSortedMock
import core.Util.toValuesWithIndices

object SortableModelMock:
	
	val sortable = SortableModel.from(
		mayBeList = NonEmptyListModel.from(
			mayBeList = ToBeSortedMock.ascendingOrder
				.unsorted
				.toValuesWithIndices
		).toOption.get
	).toOption.get