package com.example.notesmasteer;
import android.util.Log;

import androidx.room.Room;

import com.example.notesmasteer.base.BaseActivity;
import com.example.notesmasteer.callback.NoteDao;
import com.example.notesmasteer.databinding.ActivityMainBinding;
import com.example.notesmasteer.model.AppDatabase;
import com.example.notesmasteer.model.Note;

import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> {
    @Override
    public Class<MainViewModel> getViewmodel() {
        return MainViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void setBindingViewmodel() {
       binding.setViewmodel(viewmodel);

    }

}
