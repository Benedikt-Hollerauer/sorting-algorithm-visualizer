package error.useCaseError

import error.modelError.SortedModelError

enum SortByBubbleSortUseCaseError:
    
    case InputFailure(sortableValueError: SortedModelError)