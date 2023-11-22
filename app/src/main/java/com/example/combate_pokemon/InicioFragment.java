package com.example.combate_pokemon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.combate_pokemon.databinding.FragmentInicioBinding;

public class InicioFragment extends Fragment {


    FragmentInicioBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        return (binding = FragmentInicioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar el navController
        navController = Navigation.findNavController(view);
        // Definir el comportamiento del botón "Jugar"
        binding.jugarButton.setOnClickListener(v -> {
            // Navegar al siguiente fragmento
            navController.navigate(R.id.action_inicioFragment_to_dataRequestP1Fragment);
        });
    }
}