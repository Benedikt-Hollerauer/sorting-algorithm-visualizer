package test.modelTest

import core.model.ValueWithIndex
import error.modelError.ValueWithIndexError

import scala.util.Random

object ValueWithIndex_Test:

	object from_should_return:

		def `ValidIndex`: Unit =
			for
				res <- ValueWithIndex.from(
					value = 10,
					mayBeIndex = 3
				)
			yield assert(res.index == 3)

		def `NegativeIndex`: Unit =
			for
				res <- ValueWithIndex.from(
					value = 10,
					mayBeIndex = -1
				).left
			yield assert(res == ValueWithIndexError.NegativeIndex(-1))