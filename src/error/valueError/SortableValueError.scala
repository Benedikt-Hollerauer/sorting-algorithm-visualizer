package error.valueError

enum SortableValueError:

    case EmptyList(list: List[Int])
    case ToFewElements(list: List[Int])