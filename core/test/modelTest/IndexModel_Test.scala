package test.modelTest

import core.model.IndexModel
import error.modelError.IndexModelError

object IndexModel_Test:

	object from_should_return:

		def `IndexModel(0)`: Unit =
			val mayBeIndexMock: Int = 0
			for
				res <- IndexModel.from(
					mayBeIndex = mayBeIndexMock
				)
			yield
				assert(res.index == mayBeIndexMock)

		def `IndexModel(100)`: Unit =
			val mayBeIndexMock: Int = 100
			for
				res <- IndexModel.from(
					mayBeIndex = mayBeIndexMock
				)
			yield
				assert(res.index == mayBeIndexMock)

		def `NegativeIndex`: Unit =
			val negativeIndexMock: Int = -1
			for
				res <- IndexModel.from(
					mayBeIndex = negativeIndexMock
				).left
			yield
				assert(res ==
					IndexModelError.NegativeIndex(negativeIndexMock)
				)