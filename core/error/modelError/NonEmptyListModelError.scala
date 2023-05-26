package error.modelError

enum NonEmptyListModelError:

	case EmptyList
	case ToFewElements(toFewElements: Int)