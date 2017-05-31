package org.catroid.catrobat.newui.db.util;

import android.support.annotation.NonNull;

public class SQLHelper {

    public static String escapeTable(String tableName) {
        return "'" + tableName + "'";
    }

    public static String escapeColumn(String columnName) {
        return "'" + columnName + "'";
    }

    public static String createTableDefinition(String tableName, String[] columnDefinitions) {
        String joinedColumnDefinitions = join(columnDefinitions, ", ");

        return "CREATE TABLE " + escapeTable(tableName) + "(" + joinedColumnDefinitions + ");";
    }

    private static String join(String[] items, String glue) {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < items.length; i++) {
            if (i != 0) {
                b.append(glue);
            }

            b.append(items[i]);
        }

        return b.toString();
    }

    public static String idColumnDefinition(String columnName) {
        return escapeColumn(columnName) + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT";
    }

    public static String stringColumnDefinition(String columnName) {
        return escapeColumn(columnName) + " TEXT NULL";
    }

    public static String booleanColumnDefinition(String columnName) {
        // SQLite duh!
        return escapeColumn(columnName) + " INTEGER";
    }


    public static String dropTableIfExists(String tableName) {
        return "DROP TABLE IF EXISTS " + escapeTable(tableName) + ";";
    }

    public static String integerColumnDefinition(String columnName) {
        return escapeColumn(columnName) + " INTEGER";
    }

    public static String modifierUnique(@NonNull String columnDefinition) {
        return columnDefinition + " UNIQUE";
    }
}
