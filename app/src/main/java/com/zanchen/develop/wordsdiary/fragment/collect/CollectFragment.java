package com.zanchen.develop.wordsdiary.fragment.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zanchen.develop.wordsdiary.databinding.FragmentCollectBinding;

public class CollectFragment extends Fragment {
    private FragmentCollectBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CollectViewModel collectViewModel =
                new ViewModelProvider(this).get(CollectViewModel.class);

        binding = FragmentCollectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCollect;
        collectViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
