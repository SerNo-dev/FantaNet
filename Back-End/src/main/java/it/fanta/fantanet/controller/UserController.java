package it.fanta.fantanet.controller;

import it.fanta.fantanet.models.GiocatoriVotiNellePartite;
import it.fanta.fantanet.models.Utente;
import it.fanta.fantanet.repository.GiocatoriVotiNellePartiteRepository;
import it.fanta.fantanet.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GiocatoriVotiNellePartiteRepository giocatoriVotiNellePartiteRepository;

    @GetMapping("/{id}")
    public Utente getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    @GetMapping
    public List<Utente> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{userId}/giocatori")
    public ResponseEntity<Utente> addGiocatoriToUser(@PathVariable Long userId, @RequestBody List<Integer> giocatoriIds) {
        try {
            Utente updatedUser = userService.addGiocatoriToUser(userId, giocatoriIds);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

//    @PostMapping("/{id}/avatar")
//    public Utente updateAvatar(@PathVariable Long id, @RequestBody String avatarUrl) {
//        Optional<Utente> optionalUtente = userService.getUserById(id);
//        if (optionalUtente.isPresent()) {
//            Utente utente = optionalUtente.get();
//            utente.setAvatarUrl(avatarUrl);
//            return userService.saveUser(utente);
//        }
//        return null;
//    }

//    @GetMapping("/{userId}/voti")
//    public List<GiocatoriVotiNellePartite> getVotiPerUtente(@PathVariable Long userId) {
//        Optional<Utente> optionalUtente = userService.getUserById(userId);
//        if (optionalUtente.isPresent()) {
//            Utente utente = optionalUtente.get();
//            return giocatoriVotiNellePartiteRepository.findByGiocatoreIn(utente.getGiocatoriAcquistati());
//        }
//        return null;
//    }
//
//    @GetMapping("/{userId}/voti/{giocatoreId}")
//    public List<GiocatoriVotiNellePartite> getVotiPerUtenteEGiocatore(@PathVariable Long userId, @PathVariable Integer giocatoreId) {
//        Optional<Utente> optionalUtente = userService.getUserById(userId);
//        if (optionalUtente.isPresent()) {
//            Utente utente = optionalUtente.get();
//            return giocatoriVotiNellePartiteRepository.findByGiocatoreIdAndGiocatoreIn(giocatoreId, utente.getGiocatoriAcquistati());
//        }
//        return null;
//    }


    @PatchMapping("/{id}/avatar")
    public ResponseEntity<Map<String, String>> patchAvatarUser(@PathVariable Long id, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        System.out.println("Received PATCH request for user ID: " + id);
        String updatedAvatarUrl = userService.patchAvatarUser(id, avatar);
        if (updatedAvatarUrl != null) {
            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", updatedAvatarUrl);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }
    }



    @PostMapping("/{userId}/carrello/{giocatoreId}")
    public ResponseEntity<Utente> addGiocatoreToCarrello(@PathVariable Long userId, @PathVariable Integer giocatoreId) {
        System.out.println("Received request to add player to cart: userId = " + userId + ", giocatoreId = " + giocatoreId);
        Utente updatedUser = userService.addGiocatoreToCarrello(userId, giocatoreId);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser); // Restituisce l'utente aggiornato
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @DeleteMapping("/{userId}/carrello/{giocatoreId}")
    public ResponseEntity<Utente> removeGiocatoreFromCarrello(@PathVariable Long userId, @PathVariable Integer giocatoreId) {
        Utente updatedUser = userService.removeGiocatoreFromCarrello(userId, giocatoreId);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PostMapping("/{userId}/carrello/conferma")
    public ResponseEntity<Utente> confermaAcquisto(@PathVariable Long userId, @RequestBody List<Integer> giocatoriIds) {
        Utente updatedUser = userService.confermaAcquisto(userId, giocatoriIds);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }




    @PostMapping("/{userId}/deck/add")
    public ResponseEntity<Utente> addToDeck(@PathVariable Long userId, @RequestBody Integer giocatoreId) {
        try {
            Utente updatedUser = userService.addToDeck(userId, giocatoreId);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{userId}/deck/remove")
    public ResponseEntity<Utente> removeFromDeck(@PathVariable Long userId, @RequestBody Integer giocatoreId) {
        try {
            Utente updatedUser = userService.removeFromDeck(userId, giocatoreId);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/randomWithFullDeck")
    public ResponseEntity<Utente> getRandomUserWithFullDeck(@RequestParam Long userId) {
        Utente randomUser = userService.getRandomUserWithFullDeck(userId);
        if (randomUser == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(randomUser);
    }
}
