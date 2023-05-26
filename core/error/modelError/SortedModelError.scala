package error.modelError

enum SortedModelError {
	case ToFewChangedIndices(it: List[Int])
	case NegativeChangedIndices(it: List[Int])
}