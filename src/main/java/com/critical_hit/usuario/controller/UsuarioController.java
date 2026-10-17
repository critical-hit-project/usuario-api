package com.critical_hit.usuario.controller;

import com.critical_hit.usuario.dto.AtualizarUsuarioRequest;
import com.critical_hit.usuario.dto.DepositoRequest;
import com.critical_hit.usuario.dto.JogoAdquiridoResponse;
import com.critical_hit.usuario.dto.LoginRequest;
import com.critical_hit.usuario.dto.LoginResponse;
import com.critical_hit.usuario.dto.RegistrarCompraRequest;
import com.critical_hit.usuario.dto.UsuarioRequest;
import com.critical_hit.usuario.dto.UsuarioResponse;
import com.critical_hit.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(request));
    }

    @GetMapping
    public List<UsuarioResponse> listar(@RequestParam(required = false) String nome) {
        return usuarioService.listar(nome);
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(
            @PathVariable Long id, @Valid @RequestBody AtualizarUsuarioRequest request) {
        return usuarioService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return usuarioService.login(request);
    }

    @PostMapping("/{id}/carteira")
    public UsuarioResponse depositar(
            @PathVariable Long id, @Valid @RequestBody DepositoRequest request) {
        return usuarioService.depositar(id, request.valor());
    }

    @PostMapping("/{id}/compras")
    public ResponseEntity<List<JogoAdquiridoResponse>> registrarCompra(
            @PathVariable Long id, @Valid @RequestBody RegistrarCompraRequest request) {
        List<JogoAdquiridoResponse> jogos = usuarioService.registrarCompra(
                id, request.valor(), request.jogoIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(jogos);
    }

    @GetMapping("/{id}/jogos-adquiridos")
    public List<JogoAdquiridoResponse> listarJogosAdquiridos(@PathVariable Long id) {
        return usuarioService.listarJogosAdquiridos(id);
    }
}
