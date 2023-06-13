package core.model

enum OrderModel(ordering: (Int, Int) => Boolean):
    
    def getOrdering: (Int, Int) => Boolean =
        this.ordering
    
    case Ascending extends OrderModel(_ <= _)
    case Descending extends OrderModel(_ >= _)