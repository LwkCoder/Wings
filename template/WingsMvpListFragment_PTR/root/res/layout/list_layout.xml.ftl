<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    android:orientation="vertical">

    <com.lwkandroid.lib.common.widgets.ptr.PTRLayout
        android:id="@+id/ptrLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </com.lwkandroid.lib.common.widgets.ptr.PTRLayout>

</LinearLayout>