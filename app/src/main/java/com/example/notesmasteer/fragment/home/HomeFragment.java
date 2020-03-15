package com.example.notesmasteer.fragment.home;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import com.example.notesmasteer.R;
import com.example.notesmasteer.base.BaseFragment;
import com.example.notesmasteer.callback.NoteDao;
import com.example.notesmasteer.databinding.FragHomeBinding;
import com.example.notesmasteer.model.AppDatabase;
import com.example.notesmasteer.model.Note;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<FragHomeBinding,HomeViewModel> {
    NoteDao noteDao;
    AppDatabase database;
    private static final int TIME_DELAY = 3000;
    NavController controller;
    @Override
    public Class<HomeViewModel> getViewmodel() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.frag_home;
    }

    @Override
    public void setBindingViewmodel() {
         binding.setViewmodel(viewmodel);
         controller = NavHostFragment.findNavController(this);
         initDatabase();
         initRecyclerviewNote();
    }

    private void initRecyclerviewNote() {
        binding.rvNotes.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        binding.rvNotes.setLayoutManager(staggeredGridLayoutManager);
        binding.rvNotes.setAdapter(viewmodel.noteAdapter);
    }

    private void initDatabase() {
        // init room database
        database = Room.databaseBuilder(getContext(), AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        noteDao = database.getNoteDAO();
        addSampleNoteToDatabase();
    }

    @Override
    public void ViewCreated() {
        // add animation textview and remove splash after 3s
        Animation animationTextView = AnimationUtils.loadAnimation(getContext(),R.anim.bounce_animation);
        binding.tvLogo.startAnimation(animationTextView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               binding.rlSplash.setVisibility(View.GONE);
            }
        },TIME_DELAY);
        event();
        // quan sát listnote trong viewmodel
        viewmodel.getListNote().observe(this, new Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(ArrayList<Note> notes) {
                viewmodel.noteAdapter.setList(notes);
            }
        });
        getNote();
    }

    private void getNote() {
        viewmodel.setListNote((ArrayList<Note>) noteDao.getItems());
    }

    private void event() {
        binding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteDao.deleteAllNote();
            }
        });
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity(), newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    public void addSampleNoteToDatabase(){
        noteDao.insert(new Note("aMục tiêu khi ra trường","Đến khi ra trường phải đạt dc mức lương khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khidc mức lương khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khng khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khi ra trường phải đạt dc mức lương khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khi ra trường phải đạt dc mức lương khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khi ra trư0tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khi ra trường phải đạt dc mức lương khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khiơng khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
        noteDao.insert(new Note("Mục tiêu khi ra trường","Đến khi ra trường phải đạt dc mức lương khởi điểm là 10tr :D chayzo","9:13 PM 15/3/2020","9:13 PM 15/6/2021"));
    }
}
