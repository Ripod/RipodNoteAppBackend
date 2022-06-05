package ru.ripod.actionprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.ripod.db.repositories.NoteDataRepository;
import ru.ripod.db.repositories.UserNotesRepository;
import ru.ripod.utils.dbmodels.LoginData;
import ru.ripod.utils.dbmodels.NoteData;
import ru.ripod.utils.dbmodels.UserNotes;
import ru.ripod.utils.restmodels.exceptions.CustomException;
import ru.ripod.utils.restmodels.exceptions.NoteCreationException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static ru.ripod.utils.dbmodels.UserNotes.*;

@Service
public class NoteManagement {
    @Autowired
    private NoteDataRepository noteDataRepository;
    @Autowired
    private UserNotesRepository userNotesRepository;
    @Autowired
    private Authorization authorization;

    public NoteData noteCreationProcess
            (String title, boolean passwordProtected, String password, boolean shared, long userId, String token)
            throws CustomException {
        LoginData loginData = authorization.validateToken(userId, token);
        NoteData noteData = new NoteData();
        synchronized (NoteDataRepository.class) {
            noteData.setTitle(title);
            noteData.setPasswordProtected(passwordProtected);
            if (passwordProtected) {
                if (password.length() < 6) {
                    throw new NoteCreationException("Пароль для заметки должен быть длиннее 6 символов");
                }
                String encodedPassword = Authorization.encodePassword(password);
                noteData.setPassword(encodedPassword);
            }
            noteData.setShared(shared);
            Calendar curTime = Calendar.getInstance();
            String curTimeString = curTime.toString();
            String noteFileName = DigestUtils.md5DigestAsHex((curTimeString + loginData.getLogin()).getBytes(StandardCharsets.UTF_8));
            noteData.setAddress("storage/" + noteFileName + ".txt");

            noteDataRepository.save(noteData);
        }
        synchronized (UserNotesRepository.class) {
            UserNotes userNotes = new UserNotes(loginData.getUserId(), noteData.getNoteId(), ADMIN);
            userNotesRepository.save(userNotes);
        }
        return noteData;
    }

    public void noteFileSave(NoteData noteData, MultipartFile file) {
        File outputFile = new File(noteData.getAddress());
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
