package com.example.notesmasteer.callback;

import com.example.notesmasteer.base.CBAdapter;
import com.example.notesmasteer.model.Note;

public interface NoteCallBack extends CBAdapter {
    void onNoteClick(Note note);
}
