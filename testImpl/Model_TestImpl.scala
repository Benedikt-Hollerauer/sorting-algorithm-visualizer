import test.modelTest.{IndexModel_Test, NonEmptyListModel_Test, SortableModel_Test, SortingModel_Test, ValueWithIndexModel_Test}

class Model_TestImpl extends TestUtil:

    implementTest(SortableModel_Test)
    implementTest(SortingModel_Test)
    implementTest(ValueWithIndexModel_Test)
    implementTest(IndexModel_Test)
    implementTest(NonEmptyListModel_Test)