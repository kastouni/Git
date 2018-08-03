package zizkuz_menu.hassebnafssak;

/**
 * Created by zouheir_kastouni on 18/02/2016.
 */
public class UserDbSchema {

    public static  final class UserTable {
        public static final String NAME= "users";

        public static final class Cols {

            public static final String KEY_ID_USER="uuid";
            public static final String KEY_NOM_USER="Nom";
            public static final String KEY_EMAIL_USER="Email";
            public static final String KEY_PHONE_USER="Mobile";
            public static final String KEY_Connexion="Connexion";

        }


    }

    public static  final class FirstConnection {
        public static final String NAME_First= "First";

        public static final class Cols {

            public static final String KEY_Indicateur_Premiere_connexion="KPI";

        }


    }

    public static final class HadafTable {
        public static final String NAME_HADAF = "Hadaf";

        public static final class Cols {

            public static final String KEY_ID_USER_HADAF = "uuid1";
            public static final String KEY_ID_USER_EMAIL_HADAF = "email";
            public static final String KEY_DATE_HADAF = "Date";
            public static final String KEY_SALAT_HADAF = "Salat";
            public static final String KEY_QURAN_HADAF = "Quran";
            public static final String KEY_SIYAM_HADAF = "Siyam";
            public static final String KEY_SADAKA_HADAF = "Sadaka";
            public static final String KEY_ADKARASSABAH_HADAF = "AdkarAssabah";
            public static final String KEY_ADKARALMASA_HADAF = "AdkarAlMasaa";
            public static final String KEY_QIYAM_HADAF = "Qiyam";

        }

    }
        public static final class DataTable {
            public static final String NAME_DATA = "Data";

            public static final class Cols {

                public static final String KEY_ID_USER_DATA = "uuid1";
                public static final String KEY_ID_USER_EMAIL = "email";
                public static final String KEY_DATE = "Date";
                public static final String KEY_SALAT = "Salat";
                public static final String KEY_QURAN = "Quran";
                public static final String KEY_SIYAM = "Siyam";
                public static final String KEY_SADAKA = "Sadaka";
                public static final String KEY_ADKARASSABAH = "AdkarAssabah";
                public static final String KEY_ADKARALMASA = "AdkarAlMasaa";
                public static final String KEY_QIYAM = "Qiyam";
                public static final String KEY_MONTH = "Month";
                public static final String KEY_DAY = "Day";
                public static final String KEY_YEAR= "Year"
                        ;

            }


        }


    }
