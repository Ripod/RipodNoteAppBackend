package ru.ripod.restprocessing.notemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ripod.actionprocessing.Authorization;
import ru.ripod.actionprocessing.NoteManagement;
import ru.ripod.utils.dbmodels.NoteData;
import ru.ripod.utils.restmodels.exceptions.CustomException;
import ru.ripod.utils.restmodels.requests.NoteCreateRequest;
import ru.ripod.utils.restmodels.responses.BaseResponse;
import ru.ripod.utils.restmodels.responses.NoteDataResponse;

@RestController
public class NoteManagementController {

    public static final String SESSION_ID = "SessionID";

    @Autowired
    Authorization authorization;
    @Autowired
    NoteManagement noteManagement;

    @PostMapping(path = "note/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse createNote(
            @CookieValue(name = SESSION_ID, defaultValue = "0") String token,
            @RequestPart(name = "noteHeader")NoteCreateRequest noteHeader,
            @RequestPart(name = "noteFile")MultipartFile noteFile
            ){
        try {
            NoteData noteData = noteManagement.noteCreationProcess(noteHeader.getTitle(),
                    noteHeader.isPasswordProtected(),
                    noteHeader.getPassword(),
                    noteHeader.isShared(),
                    noteHeader.getUserId(),
                    token);
            noteManagement.noteFileSave(noteData, noteFile);
            return new NoteDataResponse("Note created successfully", noteData.getNoteId());
        } catch (CustomException exc) {
            return new BaseResponse(exc.getErrorCode(), exc.getMessage());
        }

    }
}
