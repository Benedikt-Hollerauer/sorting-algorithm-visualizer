import test.modelTest.{IndexModel_Test, NonEmptyListModel_Test, SortableModel_Test}

class Model_TestImpl extends TestUtil:

    implementTest(SortableModel_Test)
    implementTest(IndexModel_Test)
    implementTest(NonEmptyListModel_Test)