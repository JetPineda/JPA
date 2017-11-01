package businesslogic;

import dataaccess.UserDB;
import domainmodel.Note;
import java.util.Date;

import java.util.List;

public class UserService {

    private UserDB noteDB;

    public UserService() {
        noteDB = new UserDB();
    }

    public Note get(int noteId) throws Exception {
        return noteDB.getNote(noteId);
    }

    public List<Note> getAll() throws Exception {
        return noteDB.getAll();
    }

    public int update(int noteId, String contents) throws Exception {
        Note note = noteDB.getNote(noteId);
        note.setContents(contents);
        return noteDB.update(note);
    }

    public int delete(int noteId) throws Exception {
         Note note = noteDB.getNote(noteId);
        return noteDB.delete(note);
    }

    public int insert(int noteId, String contents) throws Exception {
       Date dateCreated = new Date();
        Note note = new Note(noteId,dateCreated,contents);
        return noteDB.insert(note);
    }
}
