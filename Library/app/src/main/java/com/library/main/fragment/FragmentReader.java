package com.library.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;
import com.library.main.fragment.reader.BookSearchAdapter;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.utilities.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FragmentReader extends Fragment {
    private final List<Book> dataStorage = new ArrayList<>();
    private SearchView svSearch;
    private BookSearchAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reader, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControl(view);
        setData();
        setEvent();
    }

    private void setControl(View view) {
        this.svSearch = view.findViewById(R.id.SearchBar);
        RecyclerView rvMainDisplay = view.findViewById(R.id.rvMainDisplay);
        rvMainDisplay.addItemDecoration(new SpaceItemDecoration(50));
        rvMainDisplay.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        this.adapter = new BookSearchAdapter();
        rvMainDisplay.setAdapter(adapter);
    }

    private void setData() {
        // SET DATA ADAPTER.
        BookService.listBook(adapter, dataStorage);
    }

    private void setEvent() {
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String s) {
                String kw = s.toLowerCase();
                adapter.setData(dataStorage.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(kw))
                        .collect(Collectors.toList()));
            }
        });
    }
}