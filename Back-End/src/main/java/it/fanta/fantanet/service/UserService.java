package it.fanta.fantanet.service;

import com.cloudinary.Cloudinary;
import it.fanta.fantanet.Dto.UserDto;
import it.fanta.fantanet.exception.UtenteNonTrovatoException;
import it.fanta.fantanet.models.Giocatore;
import it.fanta.fantanet.models.Role;
import it.fanta.fantanet.models.Utente;
import it.fanta.fantanet.repository.GiocatoreRepository;
import it.fanta.fantanet.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    public String saveUser(UserDto userDto){
        Utente utente = new Utente();
        utente.setUsername(userDto.getUsername());
        utente.setEmail(userDto.getEmail());
        utente.setRole(Role.USER);
        utente.setPassword(passwordEncoder.encode(userDto.getPassword()));
        sendMail(utente.getEmail(),utente.getUsername());
        userRepository.save(utente);
        return "Utente con id = " + utente.getId() + "salvato correttamente";

    }

    private void sendMail(String email,String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione nuovo Utente");
        message.setText("Bevenuto nella nostra azienda, il tuo username per accedere alla nostra piattaforma é " + username);

        javaMailSender.send(message);
    }
    public Utente updateUser(Long id, UserDto userDto){
        Optional<Utente> utenteOptional = getUserById(id);
        if(utenteOptional.isPresent()){
            Utente utente= utenteOptional.get();
            utente.setUsername(userDto.getUsername());
            utente.setEmail(userDto.getEmail());
            utente.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(utente);
            return utente;

        } else{
            throw new UtenteNonTrovatoException("Utente con id = " +id+ "non trovato");
        }

    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String patchAvatarUser(Long id, MultipartFile foto) throws IOException {
        logger.info("Received request to update avatar for user with ID: {}", id);

        Optional<Utente> utenteOpt = getUserById(id);
        if (utenteOpt.isPresent()) {
            String url = (String) cloudinary.uploader().upload(foto.getBytes(), Collections.emptyMap()).get("url");
            Utente utente = utenteOpt.get();
            utente.setAvatarUrl(url);
            userRepository.save(utente);
            logger.info("Updated avatar URL to: {}", url);
            logger.info("Updated avatar URL in database for user ID: {}", id);
            return url;  // Restituisci direttamente l'URL dell'avatar aggiornato
        } else {
            logger.error("User with ID: {} not found", id);
            throw new UtenteNonTrovatoException("Utente con id = " + id + " non trovato");
        }
    }

    public Optional<Utente> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Utente getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Utente> getAllUsers() {
        return userRepository.findAll();
    }

    public Utente getUserByEmail(String email) {
        Optional<Utente> dipendenteOptional = userRepository.findByEmail(email);
        if(dipendenteOptional.isPresent()){
            return dipendenteOptional.get();
        } else {
            throw new UtenteNonTrovatoException("Utente non trovato");
        }


    }


    public Utente addGiocatoriToUser(Long userId, List<Integer> giocatoriIds) {
        Optional<Utente> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Utente user = optionalUser.get();
            List<Giocatore> selectedGiocatori = giocatoreRepository.findAllById(giocatoriIds);

            // Calcola il totale dei prezzi dei giocatori
            int totale = selectedGiocatori.stream().mapToInt(Giocatore::getPrezzo).sum();

            if (user.getCrediti() >= totale) {
                user.getGiocatoriAcquistati().addAll(selectedGiocatori);
//                user.getDeck().addAll(selectedGiocatori);
                user.getCarrello().removeAll(selectedGiocatori); // Rimuove i giocatori dal carrello
                user.setCrediti(user.getCrediti() - totale); // Detrae il totale dai crediti dell'utente
                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Not enough credits");
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }



    public Utente getRandomUserWithFullDeck(Long userId) {
        List<Utente> users = userRepository.findAll();
        List<Utente> validUsers = users.stream()
                .filter(user -> !user.getId().equals(userId) && user.getDeck().size() == 7)
                .collect(Collectors.toList());

        if (validUsers.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(validUsers.size());
        return validUsers.get(randomIndex);
    }





    public Utente addGiocatoreToCarrello(Long userId, Integer giocatoreId) {
        Optional<Utente> optionalUtente = userRepository.findById(userId);
        Giocatore giocatore = giocatoreRepository.findById(giocatoreId).orElse(null);

        if (optionalUtente.isPresent() && giocatore != null) {
            Utente utente = optionalUtente.get();
            utente.getCarrello().add(giocatore);
            return userRepository.save(utente); // Salva e ritorna l'utente aggiornato
        }
        return null;
    }





    public Utente removeGiocatoreFromCarrello(Long userId, Integer giocatoreId) {
        Optional<Utente> optionalUtente = userRepository.findById(userId);

        if (optionalUtente.isPresent()) {
            Utente utente = optionalUtente.get();
            Iterator<Giocatore> iterator = utente.getCarrello().iterator();
            while (iterator.hasNext()) {
                Giocatore giocatore = iterator.next();
                if (giocatore.getId() == giocatoreId) {
                    iterator.remove();
                }
            }
            return userRepository.save(utente);
        }
        return null;
    }







    public Utente confermaAcquisto(Long userId, List<Integer> giocatoriIds) {
        Optional<Utente> optionalUtente = userRepository.findById(userId);

        if (optionalUtente.isPresent()) {
            Utente utente = optionalUtente.get();
            int totale = utente.getCarrello().stream().mapToInt(Giocatore::getPrezzo).sum();
            if (utente.getCrediti() >= totale) {
                List<Giocatore> selectedGiocatori = giocatoreRepository.findAllById(giocatoriIds);
                utente.getDeck().addAll(selectedGiocatori);
                utente.getCarrello().clear();
                utente.setCrediti(utente.getCrediti() - totale);
                return userRepository.save(utente);
            }
        }
        return null;
    }


    public Utente addToDeck(Long userId, Integer giocatoreId) {
        Optional<Utente> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Utente user = optionalUser.get();
            Optional<Giocatore> optionalGiocatore = giocatoreRepository.findById(giocatoreId);
            if (optionalGiocatore.isPresent()) {
                Giocatore giocatore = optionalGiocatore.get();
                if (user.getDeck().contains(giocatore)) {
                    throw new IllegalArgumentException("Player is already in the deck");
                }
                user.getDeck().add(giocatore);
                return userRepository.save(user);
            } else {
                throw new EntityNotFoundException("Giocatore not found");
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public Utente removeFromDeck(Long userId, Integer giocatoreId) {
        Optional<Utente> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Utente user = optionalUser.get();
            Optional<Giocatore> optionalGiocatore = giocatoreRepository.findById(giocatoreId);
            if (optionalGiocatore.isPresent()) {
                Giocatore giocatore = optionalGiocatore.get();
                user.getDeck().remove(giocatore);
                return userRepository.save(user);
            } else {
                throw new EntityNotFoundException("Giocatore not found");
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }}






