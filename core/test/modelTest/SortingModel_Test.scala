package test.modelTest

import core.model.{SortingModel, ValueWithIndexModel}

object SortingModel_Test:

	object empty_should_return:

		def `SortingModel - empty`: Unit =
			val res = SortingModel.empty
			assert(res.focusedIndices ==
				(ValueWithIndexModel.empty, ValueWithIndexModel.empty)
			)
			assert(res.focusedIndicesChanged == false)