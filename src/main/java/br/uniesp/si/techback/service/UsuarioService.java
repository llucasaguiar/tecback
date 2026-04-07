package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Usuarios;
import br.uniesp.si.techback.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuariosRepository usuariosRepository;

    public Usuarios Salvar(Usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }

    public List<Usuarios> listar() {
        return usuariosRepository.findAll();
    }

    public Usuarios atualizar(Long id, Usuarios usuarios) {
        Optional<Usuarios> user = usuariosRepository.findById(id);
        if(user.isEmpty()) {
            throw new RuntimeException("Usuário inexistente.");
        } else {
            return usuariosRepository.save(usuarios);
        }
    }

    public void excluir(Long id) {
        if(usuariosRepository.existsById(id)){
            throw new RuntimeException("Id não encontrado.");
        }
        usuariosRepository.deleteById(id);
    }
}
