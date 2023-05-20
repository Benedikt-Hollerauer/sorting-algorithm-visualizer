package core.model

import error.modelError.ValueWithIndexError

case class ValueWithIndex private(
	value: Int,
	index: Int
)

object ValueWithIndex:

	def from(
		value: Int,
		mayBeIndex: Int
	): Either[ValueWithIndexError, ValueWithIndex] =
		if (mayBeIndex < 0) Left(ValueWithIndexError.NegativeIndex(mayBeIndex))
		else Right(
			ValueWithIndex(
				value,
				mayBeIndex
			)
		)