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

import com.example.combate_pokemon.databinding.FragmentGameOverBinding;


public class GameOverFragment extends Fragment {
    FragmentGameOverBinding binding;
    NavController navController;

    PokemonViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        return (binding = FragmentGameOverBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar el ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);
        // Inicializar el navController
        navController = Navigation.findNavController(view);

        binding.winnerText.setText(viewModel.getMensaje().getValue().toString());

        // Definir el comportamiento del botón "Volver a jugar"
        binding.volverAJugarButton.setOnClickListener(v -> {
            // Navegar al siguiente fragmento
            navController.navigate(R.id.action_gameOverFragment_to_inicioFragment);
        });
    }
}