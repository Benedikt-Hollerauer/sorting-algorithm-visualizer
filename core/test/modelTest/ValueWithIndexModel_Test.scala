package test.modelTest

import core.model.ValueWithIndexModel

object ValueWithIndexModel_Test:
	
	object empty_should_return:
		
		def `ValueWithIndexModel - empty`: Unit =
			val res = ValueWithIndexModel.empty
			assert(res.value == 0)
			assert(res.indexModel.index == -1)