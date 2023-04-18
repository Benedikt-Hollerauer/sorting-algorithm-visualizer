package error.useCaseError

import error.modelError.SortableModelError

enum SortByBubbleSortUseCaseError:
    
    case InputFailure(sortableValueError: SortableModelError)