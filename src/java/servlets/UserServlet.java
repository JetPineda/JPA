package servlets;

import businesslogic.UserService;
import domainmodel.Note;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            String selectedNoteId = request.getParameter("selectedNoteId");
            int noteId = Integer.parseInt(selectedNoteId);
            try {
                Note note = us.get(noteId);
                request.setAttribute("selectedNote", note);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        List<Note> notes = null;        
        try {
            notes = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("notes", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String selectedNoteId = request.getParameter("selectedNoteId");
        String contents = request.getParameter("contents");
        UserService us = new UserService();
        int noteId;
        
         if(selectedNoteId == null ||selectedNoteId.isEmpty()){
            noteId = 0;
        } else {
            noteId = Integer.parseInt(selectedNoteId);
        }


        try {
            if (action.equals("delete")) {
                us.delete(noteId);
            } else if (action.equals("edit")) {
                us.update(noteId,contents);
            } else if (action.equals("add")) {
                us.insert(noteId,contents);
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Whoops.  Could not perform that action.");
        }
        
        List<Note> notes = null;
        try {
            notes = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("notes", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }
}
