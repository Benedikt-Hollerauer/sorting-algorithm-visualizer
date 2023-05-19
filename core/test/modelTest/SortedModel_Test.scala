package test.modelTest

import core.model.SortableModel
import error.modelError.SortedModelError
import mock.ToBeSortedMock

object SortedModel_Test:

	object from_should_return:

		def `SortedModel`: Unit =
			val correctChangedIndicesMock = List(0, 1)
			for
				res <- core.model.SortedModel.from(
					sortable = SortableModel.from(ToBeSortedMock.ascendingOrder.sorted).toOption.get
				)
				_ = println(res)
			yield
				assert(res.sortable.list == ToBeSortedMock.ascendingOrder.sorted)