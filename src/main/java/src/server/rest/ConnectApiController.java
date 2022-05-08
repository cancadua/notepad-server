package src.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.server.HtmlHandler;
import src.server.JsonHandler;
import src.server.model.User;
import src.server.model.Note;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConnectApiController {

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) throws FileNotFoundException {
        if (JsonHandler.checkUser(user)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) throws IOException {
        if (!JsonHandler.saveUser(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/post-note/create")
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        HtmlHandler.save(note);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping(value = "/get-note/{user}/{id}")
    public ResponseEntity<?> getNote(@PathVariable("user") String user, @PathVariable("id")String id) {

        Note backnote = HtmlHandler.getData(id, user);
        return new ResponseEntity<>(backnote, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/get-note/{user}/get-all")
    public ResponseEntity<?> getAllNotes(@PathVariable("user") String user) {

        Note[] backnote = HtmlHandler.getList(user);
        return new ResponseEntity<>(backnote, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(value = "/edit-note/{user}/{id}")
    public ResponseEntity<?> editNote(@PathVariable("user") String user, @PathVariable("id")String id, @RequestBody Note note) {
        HtmlHandler.edit(user, id, note);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/delete-note/{user}/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable("user") String user, @PathVariable("id") String id) {
        HtmlHandler.deleteNote(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}