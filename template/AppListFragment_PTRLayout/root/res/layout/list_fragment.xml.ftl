<LinearLayout 
	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:fitsSystemWindows="true">

    <com.lwkandroid.wings.widget.ptr.PTRLayout
        android:id="@+id/ptr_list"
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rcv_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

    </com.lwkandroid.wings.widget.ptr.PTRLayout>

</LinearLayout>