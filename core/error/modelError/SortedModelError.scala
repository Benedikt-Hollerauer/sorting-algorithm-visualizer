package error.modelError

enum SortedModelError:

	case ToFewChangedIndices(toFewChangedIndices: List[Int])
	case NegativeChangedIndices(negativeChangedIndices: List[Int])