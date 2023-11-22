package com.example.combate_pokemon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.combate_pokemon.databinding.FragmentCombatBinding;


public class CombatFragment extends Fragment {

    FragmentCombatBinding binding;
    PokemonViewModel viewModel;
    NavController navController;
    boolean turnoP1 = true;
    public boolean combateTerminado = false;
    MutableLiveData<String> mensaje = new MutableLiveData<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentCombatBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);
        navController = Navigation.findNavController(view);

        binding.Pokemon1HP.setText(String.valueOf(viewModel.getPokemon1().getValue().getHp()));
        binding.Pokemon2HP.setText(String.valueOf(viewModel.getPokemon2().getValue().getHp()));
        binding.Pokemon1Name.setText(viewModel.getPokemon1().getValue().getNombre());
        binding.Pokemon2Name.setText(viewModel.getPokemon2().getValue().getNombre());
        binding.Pokemon1Ataque.setText(String.valueOf(viewModel.getPokemon1().getValue().getAtaque()));
        binding.Pokemon2Ataque.setText(String.valueOf(viewModel.getPokemon2().getValue().getAtaque()));
        binding.Pokemon1Defensa.setText(String.valueOf(viewModel.getPokemon1().getValue().getDefensa()));
        binding.Pokemon2Defensa.setText(String.valueOf(viewModel.getPokemon2().getValue().getDefensa()));
        binding.Pokemon1AtaqueEspecial.setText(String.valueOf(viewModel.getPokemon1().getValue().getAtaqueEspecial()));
        binding.Pokemon2AtaqueEspecial.setText(String.valueOf(viewModel.getPokemon2().getValue().getAtaqueEspecial()));
        binding.Pokemon1DefensaEspecial.setText(String.valueOf(viewModel.getPokemon1().getValue().getDefensaEspecial()));
        binding.Pokemon2DefensaEspecial.setText(String.valueOf(viewModel.getPokemon2().getValue().getDefensaEspecial()));

        mensaje.setValue("¡Comienza el combate!");
        viewModel.setMensaje(mensaje);
        binding.CombatTextView.setText(viewModel.getMensaje().getValue());

        iniciarCombate(viewModel);
    }

    public void iniciarCombate(PokemonViewModel viewModel) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (turnoP1) {
                    mensaje.setValue("Turno de " + viewModel.getPokemon1().getValue().getNombre());
                    viewModel.setMensaje(mensaje);
                    binding.CombatTextView.setText(viewModel.getMensaje().getValue());

                    viewModel.atacar(viewModel.getPokemon1().getValue(), viewModel.getPokemon2().getValue());
                    binding.CombatTextView.setText(viewModel.getMensaje().getValue());
                    binding.Pokemon2HP.setText(String.valueOf(viewModel.getPokemon2().getValue().getHp()));
                } else {
                    mensaje.setValue("Turno de " + viewModel.getPokemon2().getValue().getNombre());
                    viewModel.setMensaje(mensaje);
                    binding.CombatTextView.setText(viewModel.getMensaje().getValue());

                    viewModel.atacar(viewModel.getPokemon2().getValue(), viewModel.getPokemon1().getValue());
                    binding.CombatTextView.setText(viewModel.getMensaje().getValue());
                    binding.Pokemon1HP.setText(String.valueOf(viewModel.getPokemon1().getValue().getHp()));
                }

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        //Comprobar si alguno de los dos Pokémon se ha quedado sin vida
                        if (viewModel.getPokemon1().getValue().getHp() <= 0) {
                            mensaje.setValue(viewModel.getPokemon2().getValue().getNombre() + " ha ganado el combate");
                            viewModel.setMensaje(mensaje);
                            binding.CombatTextView.setText(viewModel.getMensaje().getValue());
                            combateTerminado = true;
                        } else if (viewModel.getPokemon2().getValue().getHp() <= 0) {
                            mensaje.setValue(viewModel.getPokemon1().getValue().getNombre() + " ha ganado el combate");
                            viewModel.setMensaje(mensaje);
                            binding.CombatTextView.setText(viewModel.getMensaje().getValue());
                            combateTerminado = true;
                        }
                        turnoP1 = !turnoP1;

                        if (!combateTerminado) {
                            iniciarCombate(viewModel);
                        } else{

                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    navController.navigate(R.id.action_combatFragment_to_gameOverFragment);
                                }
                            }, 5000); // Esperar 5 segundos para cambiar de escena
                        }
                    }
                }, 2500); // Esperar 2.5 segundos para comprobar si alguien se ha quedado sin vida
            }
        }, 2500); // Esperar 2.5 segundos hasta el próximo turno
    }
}