package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public Funcionario salvar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }

    public Funcionario atualizar(Long id, Funcionario funcionario) {
        Optional<Funcionario> func = funcionarioRepository.findById(id);
        if (func.isEmpty()){
            throw new RuntimeException("Funcionário inexistente");
        } else {
            return funcionarioRepository.save(funcionario);
        }
    }

    public void excluir(Long id) {
        if(funcionarioRepository.existsById(id)){
            throw new RuntimeException("Id not found");
        }
        funcionarioRepository.deleteById(id);
    }
}
