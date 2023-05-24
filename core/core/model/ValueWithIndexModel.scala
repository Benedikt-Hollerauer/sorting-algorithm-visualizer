package core.model

import error.modelError.ValueWithIndexError

case class ValueWithIndexModel(
	value: Int,
	indexModel: IndexModel
)