package com.example.abdelrahmanayman.simplenote;

public class NoteItem {

    private String notetext ;
    private String Date ;
    private String id ;

    public String getId() {
       return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NoteItem(String notetext, String date , String id ) {
        this.id = id ;
        this.notetext = notetext;
        Date = date;
    }

    public String getNotetext() {
        return notetext;
    }

    public void setNotetext(String notetext) {
        this.notetext = notetext;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return notetext + " " + Date;

    }
}

