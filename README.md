## NewUi

This is a test UI for multiselection ListViews in Android. It provides a sample ListItem containing a name, details and an image. Android provides two different ways of implementing ListViews: ListView and RecyclerView. The Activities containing the different implementations can be found in the packages listviewlist and recycleviewlist respectively.

More general information regarding the creation of lists in Android can be found [here](https://developer.android.com/training/material/lists-cards.html). Further reading on RecyclerViews can be found [here](https://developer.android.com/guide/topics/ui/layout/recyclerview.html) and [here](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html).

The aim is to provide two different implementations that support the following operations:
* A contextual ActionBar that allows the user to execute contextual actions (more information [here](https://developer.android.com/guide/topics/ui/menus.html#context-menu)).
* Multiselection of items within the list.
* Batch operations on selected items.
* (Drag & Drop functionality to reorder items in the list).
