package error.modelError

enum SortableModelError:

    case EmptyList(list: List[Int])
    case ToFewElements(list: List[Int])