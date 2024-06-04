package com.atencia.ScreenMatch.models;

public enum Category {

    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    DOCUMENTARY("Documentary"),
    SHORT("Short"),
    BIOGRAPHY("Biography"),
    HISTORY("History");


    private final String categoryOmdb;

    Category(String categoryOmdb) {
        this.categoryOmdb = categoryOmdb;
    }


    public static Category fromString(String text) {

        for (Category category : Category.values()) {

            if (category.categoryOmdb.equalsIgnoreCase(text)) {

                return category;

            }

        }

        throw new IllegalStateException("Not found category");

    }

}
