package br.com.cineroom.api.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cineroom.api.entities.Session;
import br.com.cineroom.api.repositories.SessionRepository;

@Controller
@RequestMapping("/sessions")
public class SessionController {
    //Configurando CRUDS básicos
    //Criar sessão
    @Autowired
    private SessionRepository sessionRepository;
    
    //POSTS
    //Criar uma nova sessão
    @PostMapping("/new")
    public ResponseEntity<Session> CreateSession(Session session){
        //pega horario atual para definir a data da criação da sessão
        session.setCreatedAt(LocalDateTime.now());

        //Salva a nova sessão no banco de dados
        Session savedSession = sessionRepository.save(session);

        //Retorna a sessão criada e o status 201
        return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
    }

    //GETS
    //GetAllSessions
    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Session>> GetAllSessions(){
        //Retorna todas as sessões e o status 200
        return new ResponseEntity<>(sessionRepository.findAll(), HttpStatus.OK);
    }

    //GetSessionById
    @GetMapping("/id/{id}")
    public ResponseEntity<Session> GetSessionById(Long id){
        //Retorna a sessão com o id especificado e o status 200
        return new ResponseEntity<>(sessionRepository.findById(id).get(), HttpStatus.OK);
    }

    //GetSessionByUser
    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<Session>> GetSessionByUser(Long userId){
        //Retorna todas as sessões do usuário especificado e o status 200
        return new ResponseEntity<>(sessionRepository.findByUserId(userId), HttpStatus.OK);
    }

    //GetSessionByCode
    @GetMapping("/code/{code}")
    public ResponseEntity<Session> GetSessionByCode(@PathVariable String code){
        //Retorna a sessão com o código especificado e o status 200
        return sessionRepository.findByCode(code)
                .map(session -> new ResponseEntity<>(session, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //UPDATES
    //UpdateSession
    @PutMapping("/update/{id}")
    public ResponseEntity<Session> UpdateSession(@PathVariable Long id, Session session){
        //Verifica se a sessão existe
        if(sessionRepository.existsById(id)){
            //Atualiza a sessão
            session.setId(id);
            Session updatedSession = sessionRepository.save(session);
            //Retorna a sessão atualizada e o status 200
            return new ResponseEntity<>(updatedSession, HttpStatus.OK);
        }
        //Retorna o status 404
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //DELETES
    //DeleteSession
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> DeleteSession(@PathVariable Long id){
        //Verifica se a sessão existe
        if(sessionRepository.existsById(id)){
            //Deleta a sessão
            sessionRepository.deleteById(id);
            //Retorna o status 200
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //Retorna o status 404
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //DeleteAllSessions
    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> DeleteAllSessions(){
        //Deleta todas as sessões
        sessionRepository.deleteAll();
        //Retorna o status 200
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DeleteSessionByUser
    //Essa função irá deletar todas as sessões de um usuário
    @DeleteMapping("/deleteByUser/{userId}")
    public ResponseEntity<HttpStatus> DeleteSessionByUser(Long userId){
        //Deleta todas as sessões do usuário especificado
        sessionRepository.findByUserId(userId).forEach(session -> sessionRepository.deleteById(session.getId()));
        //Retorna o status 200
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
