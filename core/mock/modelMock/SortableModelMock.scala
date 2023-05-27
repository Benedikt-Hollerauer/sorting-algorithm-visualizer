package mock.modelMock

import core.Util.toValuesWithIndices
import core.model.{NonEmptyListModel, SortableModel}
import mock.ToBeSortedMock

object SortableModelMock:
	
	val sortable = SortableModel.from(
		mayBeList = NonEmptyListModel.from(
			mayBeList = ToBeSortedMock.ascendingOrder
				.unsorted
				.toValuesWithIndices
		).toOption.get
	).toOption.get