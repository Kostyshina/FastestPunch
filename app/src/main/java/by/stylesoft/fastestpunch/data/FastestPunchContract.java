package by.stylesoft.fastestpunch.data;

import android.provider.BaseColumns;

/**
 * Created by User on 19.01.2017.
 * Container for data base
 */

public final class FastestPunchContract {
    private FastestPunchContract(){}

    public static final class CommunityEntry implements BaseColumns {
        public final static String TABLE_NAME = "Community";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_LOGIN = "login";
        public final static String COLUMN_PASSWORD = "password";
    }

    public static final class ParametersEntry implements BaseColumns {
        public final static String TABLE_NAME = "Parameters";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PUNCH_TYPE = "punchType";
        public final static String COLUMN_HAND = "hand";
        public final static String COLUMN_GLOVES = "gloves";
        public final static String COLUMN_GLOVES_WEIGHT = "glovesWeight";
        public final static String COLUMN_MOVES = "moves";
    }

    public static final class HistoryEntry implements BaseColumns {
        public final static String TABLE_NAME = "History";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PARAMETERS = "parameters";
        public final static String COLUMN_PUNCH_SPEED = "punchSpeed";
        public final static String COLUMN_REACTION_SPEED = "reactionSpeed";
        public final static String COLUMN_ACCELERATION = "acceleration";
        public final static String COLUMN_DATE = "date";
    }
}
