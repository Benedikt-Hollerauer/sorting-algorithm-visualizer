package core.model

import error.modelError.SortableModelError
import core.model.ValueWithIndexModel

case class SortableModel private(
    valuesWithIndices: List[ValueWithIndexModel]
)

object SortableModel:

    def from(mayBeList: List[Int]): Either[SortableModelError, SortableModel] =
        if(mayBeList.isEmpty) Left(SortableModelError.EmptyList)
        else if(mayBeList.length == 1) Left(SortableModelError.ToFewElements(mayBeList.length))
        else if(mayBeList.length > 500) Left(SortableModelError.ToManyElements(mayBeList.length))
        else Right(
            SortableModel(
                mayBeList.zipWithIndex
                    .map: (value, index) =>
                        ValueWithIndexModel(
                            value = value,
                            indexModel = IndexModel.from(index)
                                .toOption
                                .get
                        )
            )
        )