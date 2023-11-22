package com.example.combate_pokemon;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class PokemonViewModel extends ViewModel {

    private MutableLiveData<Pokemon> pokemon1 = new MutableLiveData<>();
    private MutableLiveData<Pokemon> pokemon2 = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();


    public MutableLiveData<Pokemon> getPokemon1() {
        return pokemon1;
    }

    public MutableLiveData<Pokemon> getPokemon2() {
        return pokemon2;
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public void setMensaje(MutableLiveData<String> mensaje) {
        this.mensaje = mensaje;
    }



    public void atacar(Pokemon atacante, Pokemon defensor) {
        int danyo = 0;
        int ataque = 0;
        int defensa = 0;

        int hpRestante = 0;
        int hp = defensor.getHp();

        if (ataqueEspecial()) {
            ataque = atacante.getAtaqueEspecial();
            defensa = defensor.getDefensaEspecial();
            danyo = calcularDanyo(ataque, defensa);
            if (danyo <= 0) {
                danyo = 0;
                mensaje.setValue(atacante.getNombre() + " realiza un ataque especial.\n " + defensor.getNombre() + " esquiva el ataque.");
            } else {
                mensaje.setValue(atacante.getNombre() + " realiza un ataque especial.\n " + defensor.getNombre() + " recibe " + danyo + " puntos de daño.");
            }

        } else {
            ataque = (int)(atacante.getAtaque()/1.5f);
            defensa = (int)(defensor.getDefensa()/1.5f);
            danyo = calcularDanyo(ataque, defensa);
            if (danyo <= 0) {
                danyo = 0;
                mensaje.setValue(atacante.getNombre() + " ataca.\n " + defensor.getNombre() + " esquiva el ataque.");

            } else {
                mensaje.setValue(atacante.getNombre() + " ataca.\n " + defensor.getNombre() + " recibe " + danyo + " puntos de daño.");
            }
        }
        hpRestante = hp - danyo;
        if (hpRestante <= 0) {
            hpRestante = 0;
        }
        defensor.setHp(hpRestante);
    }

    public boolean ataqueEspecial() {
        boolean ataqueEspecial = false;
        int random = new Random().nextInt(10);
        if (random <= 2) {  //Probabilidad de 30% de que se realice el ataque especial
            ataqueEspecial = true;
        }
        return ataqueEspecial;
    }

    public int calcularDanyo(int ataque, int defensa){
        return  new Random().nextInt(ataque) - new Random().nextInt(defensa);
    }


}
