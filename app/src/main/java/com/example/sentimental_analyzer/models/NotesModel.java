package com.example.sentimental_analyzer.models;

public class NotesModel {
    private final String id;
    private final String notesTitle;
    private final String dateTime;
    private final String uid;
    private final String notesContent;

    public NotesModel(String id, String notesTitle, String uid, String notesContent, String dateTime) {
        this.id = id;
        this.notesTitle = notesTitle;
        this.dateTime = dateTime;
        this.uid = uid;
        this.notesContent = notesContent;
    }

    public String getId() { return id;}

    public String getNotesTitle() {
        return notesTitle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getUid() {
        return uid;
    }

    public String getNotesContent() {
        return notesContent;
    }
}