package core.model

import core.model.ValueWithIndexModel
import error.modelError.SortableModelError
import core.model.ValueWithIndexModel.ordering

case class SortableModel private(
    valuesWithIndices: NonEmptyListModel[ValueWithIndexModel]
):
    def getSorted(ordering: OrderModel): SortableModel =
        val sorted = valuesWithIndices.list.sortWith: (x, y) =>
            ordering.getOrdering(x.value, y.value)
        SortableModel.from(
            NonEmptyListModel.from(
                sorted
            ).toOption.get
        ).toOption.get

object SortableModel:

    def from(mayBeValuesWithIndices: NonEmptyListModel[ValueWithIndexModel]): Either[SortableModelError, SortableModel] =
        if(mayBeValuesWithIndices.list.length > 500) Left(SortableModelError.ToManyElements(mayBeValuesWithIndices.list.length))
        else if(mayBeValuesWithIndices.list.length == 1) Left(SortableModelError.ToFewElements(mayBeValuesWithIndices.list.length))
        else Right(
            SortableModel(
                valuesWithIndices = mayBeValuesWithIndices
            )
        )