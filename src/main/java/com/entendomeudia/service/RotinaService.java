package com.entendomeudia.service;

import com.entendomeudia.model.Rotina;
import java.util.*;

public class RotinaService {
    private final Map<String, Rotina> rotinas = new HashMap<>();

    public void incluirRotina(Rotina rotina) {
        rotinas.put(rotina.getId(), rotina);
    }

    public Collection<Rotina> recuperarRotinas() {
        return rotinas.values();
    }

    public Rotina buscarPorId(String id) {
        return rotinas.get(id);
    }
}