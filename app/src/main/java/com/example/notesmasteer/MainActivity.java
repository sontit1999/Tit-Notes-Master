package com.example.notesmasteer;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.notesmasteer.base.BaseActivity;
import com.example.notesmasteer.callback.CircleMenuCallback;
import com.example.notesmasteer.databinding.ActivityMainBinding;
import com.hanks.passcodeview.PasscodeView;
import com.ramotion.circlemenu.CircleMenuView;


public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> implements CircleMenuCallback {
    SharedPreferences preferences;
    NavController controller;
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
        preferences = getSharedPreferences("sontit",MODE_PRIVATE);
        controller = Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment);
       binding.setViewmodel(viewmodel);
       checkLogin();
       event();

    }

    private void checkLogin() {
        Boolean isfirst = preferences.getBoolean("first",true);
        if(isfirst){
            preferences.edit().putBoolean("first",false).apply();
            binding.passcodeView.setPasscodeType(PasscodeView.PasscodeViewType.TYPE_SET_PASSCODE);
        }else{
            String localpass = preferences.getString("pass","9999");
            binding.passcodeView.setLocalPasscode(localpass);
            binding.passcodeView.setPasscodeType(PasscodeView.PasscodeViewType.TYPE_CHECK_PASSCODE);
        }
    }

    private void event() {

        binding.circleMenu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationStart");
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationEnd");
            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationStart");
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationEnd");
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationStart| index: " + index);
                switch (index){
                    case 0 :
                        controller.navigate(R.id.AddFragment);
                        break;
                    case 1 :
                        Toast.makeText(MainActivity.this, "Voice search", Toast.LENGTH_SHORT).show();
                        break;
                    case 2 :
                        controller.navigate(R.id.SettingFragment);
                        break;
                    case 3 :
                        //controller.popBackStack(R.id.HomeFragment,true);
                        break;
                    case 4 :
                        Toast.makeText(MainActivity.this, "navigate fragment list notification", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        binding.passcodeView.setPasscodeType(PasscodeView.PasscodeViewType.TYPE_SET_PASSCODE);
                        binding.passcodeView.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd| index: " + index);
            }
        });
        binding.passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess(String number) {
                preferences.edit().putString("pass",number).apply();
                binding.passcodeView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void hiddenMenuCircle(Boolean isHident) {
        if(isHident){
            binding.circleMenu.setVisibility(View.GONE);
        }else{
            binding.circleMenu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(controller.getCurrentDestination().getId()==R.id.HomeFragment){
            super.onBackPressed();
        }
    }
}
