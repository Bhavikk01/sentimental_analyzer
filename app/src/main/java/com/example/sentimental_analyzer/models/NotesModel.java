package com.example.sentimental_analyzer.models;

import com.google.type.DateTime;

public class NotesModel {
    private final String notesTitle;
    private final String dateTime;
    private final String uid;
    private final String notesContent;

    public NotesModel(String notesTitle, String uid, String notesContent, String dateTime) {
        this.notesTitle = notesTitle;
        this.dateTime = dateTime;
        this.uid = uid;
        this.notesContent = notesContent;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public String getDateTime() {
        return DateTime.getDefaultInstance().toString();
    }

    public String getUid() {
        return uid;
    }

    public String getNotesContent() {
        return notesContent;
    }
}