package com.example.sentimental_analyzer.firestore_sefrvices;

import com.example.sentimental_analyzer.models.NotesModel;

import java.util.List;

public interface retrieveData {
    List<NotesModel> getUserNotes(List<NotesModel> list);
}
