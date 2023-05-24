package core.model

import error.modelError.ValueWithIndexError

case class ValueWithIndexModel private(
	value: Int,
	index: Int
)

object ValueWithIndexModel:

	def from(
		value: Int,
		mayBeIndex: Int
	): Either[ValueWithIndexError, ValueWithIndexModel] =
		if (mayBeIndex < 0) Left(ValueWithIndexError.NegativeIndex(mayBeIndex))
		else Right(
			ValueWithIndexModel(
				value,
				mayBeIndex
			)
		)