package com.example.combate_pokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.combate_pokemon.databinding.FragmentDataRequestP1Binding;
import com.example.combate_pokemon.databinding.FragmentDataRequestP2Binding;


public class DataRequestP2Fragment extends Fragment {

    PokemonViewModel viewModel;
    NavController navController;
    private FragmentDataRequestP2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return(binding = FragmentDataRequestP2Binding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);
        navController = Navigation.findNavController(view);

        binding.aceptarButton.setOnClickListener(v -> {
            String nombre = binding.P2NameEditText.getText().toString();
            String hp = binding.P2HPEditText.getText().toString();
            String ataque = binding.P2AtaqueEditText.getText().toString();
            String defensa = binding.P2DefensaEditText.getText().toString();
            String ataqueEspecial = binding.P2AtaqueEspecialEditText.getText().toString();
            String defensaEspecial = binding.P2DefensaEspecialEditText.getText().toString();

            // Validar datos y crear Pokemon
            if (validarDatos(nombre, hp, ataque, defensa, ataqueEspecial, defensaEspecial)) {
                Pokemon pokemon = new Pokemon(
                        nombre,
                        Integer.parseInt(hp),
                        Integer.parseInt(ataque),
                        Integer.parseInt(defensa),
                        Integer.parseInt(ataqueEspecial),
                        Integer.parseInt(defensaEspecial)
                );
                // Actualizar ViewModel con el Pokémon1
                viewModel.getPokemon2().setValue(pokemon);
                // Navegar al siguiente fragmento
                navController.navigate(R.id.action_dataRequestP2Fragment_to_combatFragment);
            } else {
                // Mostrar mensaje de error
                Toast.makeText(requireContext(), "Error: Datos inválidos", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validarDatos(String nombre, String hp, String ataque, String defensa, String ataqueEspecial, String defensaEspecial) {

        return !nombre.isEmpty() && validarPuntos(hp) && validarPuntos(ataque) && validarPuntos(defensa) && validarPuntos(ataqueEspecial) && validarPuntos(defensaEspecial);
    }

    private boolean validarPuntos(String str) {
        boolean puntosValidos = false;

        if(str.matches("\\d+")){
            if(Integer.parseInt(str) > 0 && Integer.parseInt(str) <= 999){
                puntosValidos = true;
            }
        }
        return puntosValidos;
    }



}