package error.modelError

enum SortedModelError:

    case EmptyList
    case ToFewElements(amount: Int)
    case ToManyElements(amount: Int)
    case ToFewChangedIndices(changedIndices: List[Int])