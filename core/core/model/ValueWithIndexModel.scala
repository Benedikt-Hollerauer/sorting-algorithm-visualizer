package core.model

case class ValueWithIndexModel(
	value: Int,
	indexModel: IndexModel
)

object ValueWithIndexModel:

	implicit val ordering: Ordering[ValueWithIndexModel] = Ordering.by(_.value)