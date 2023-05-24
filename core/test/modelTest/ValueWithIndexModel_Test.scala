package test.modelTest

import core.model.ValueWithIndexModel
import error.modelError.ValueWithIndexError

import scala.util.Random

object ValueWithIndexModel_Test:

	object from_should_return:

		def `ValidIndex`: Unit =
			for
				res <- ValueWithIndexModel.from(
					value = 10,
					mayBeIndex = 3
				)
			yield assert(res.index == 3)

		def `NegativeIndex`: Unit =
			for
				res <- ValueWithIndexModel.from(
					value = 10,
					mayBeIndex = -1
				).left
			yield assert(res == ValueWithIndexError.NegativeIndex(-1))