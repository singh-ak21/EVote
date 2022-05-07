package in.cuchd.android.database;

public class VoterDbSchema
{
    public static final class VoterTable
    {
        public static final String NAME = "voter";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String AADHAAR_NUMBER = "aadhaar_number";
            public static final String NAME = "name";
            public static final String DATE_OF_BIRTH = "date_of_birth";
            public static final String PHONE = "phone";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String PARTY_ID = "party_id";
        }
    }
}
