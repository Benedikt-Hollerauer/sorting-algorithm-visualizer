package error.modelError

enum SortedModelError:

	case ToFewChangedIndices(changedIndices: List[Int])