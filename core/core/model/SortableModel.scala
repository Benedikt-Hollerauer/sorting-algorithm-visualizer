package core.model

import core.model.ValueWithIndexModel
import error.modelError.SortableModelError

case class SortableModel private(
    valuesWithIndices: NonEmptyListModel[ValueWithIndexModel]
)

object SortableModel:

    def from(mayBeValuesWithIndices: NonEmptyListModel[ValueWithIndexModel]): Either[SortableModelError, SortableModel] =
        if(mayBeValuesWithIndices.list.length > 500) Left(SortableModelError.ToManyElements(mayBeValuesWithIndices.list.length))
        else if(mayBeValuesWithIndices.list.length == 1) Left(SortableModelError.ToFewElements(mayBeValuesWithIndices.list.length))
        else Right(
            SortableModel(
                valuesWithIndices = mayBeValuesWithIndices
            )
        )