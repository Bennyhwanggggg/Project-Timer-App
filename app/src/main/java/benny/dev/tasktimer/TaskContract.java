package benny.dev.tasktimer;

import android.provider.BaseColumns;

public class TaskContract {
    static final String TABLE_NAME = "Tasks";

    // Tasks fields
    // Java allow top level classes to be inside a class as long as its static
    public static class Columns {
        // Columns class is static, static class only means the class is embedded, only real difference is we have to use TaskContract.Columns... to access it. It is a top level class packaged inside another class so we don't have to create an instance of it.
        public static final String _ID = BaseColumns._ID;
        public static final String TASK_NAME = "Name";
        public static final String TASK_DESCRIPTION = "Description";
        public static final String TASK_SORTORDER = "SortOrder";

        private Columns() {
            // private constructor to prevent instantiation.
        }
    }
}
