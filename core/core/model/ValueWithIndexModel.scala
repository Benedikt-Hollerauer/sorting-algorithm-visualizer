package core.model

import error.modelError.ValueWithIndexError

case class ValueWithIndexModel(
	value: Int,
	indexModel: IndexModel
)

object ValueWithIndexModel:
	
	def empty: ValueWithIndexModel =
		ValueWithIndexModel(
			value = 0,
			indexModel = IndexModel.empty
		)
	
	implicit val ordering: Ordering[ValueWithIndexModel] = Ordering.by(_.value)