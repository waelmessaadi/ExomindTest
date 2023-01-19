package com.exomind.exomindtest.views;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.exomind.exomindtest.R;
import com.exomind.exomindtest.databinding.FragmentHomeBinding;
import com.exomind.exomindtest.databinding.FragmentWeatherDataBinding;
import com.exomind.exomindtest.models.ResponseWeather;
import com.exomind.exomindtest.viewmodels.WeatherDataViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class WeatherDataFragment extends Fragment {

    private final int INTERVAL_6 = 6000; // 6 seconds
    private final int INTERVAL_10 = 10000; // 6 seconds
    private final int DURATION = 60000; // 1 minute

    FragmentWeatherDataBinding weatherDataBinding;
    WeatherDataViewModel weatherDataViewModel;

    int indexWaitingText = 0;
    int index = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherDataFragment newInstance(String param1, String param2) {
        WeatherDataFragment fragment = new WeatherDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        weatherDataBinding = FragmentWeatherDataBinding.inflate(inflater, container, false);

        return weatherDataBinding.getRoot();
    }

    void startUpdateWaitingMessage() {
        new CountDownTimer(DURATION, INTERVAL_6) {

            public void onTick(long millisUntilFinished) {

                if (indexWaitingText == 0) {
                    weatherDataBinding.tvWait.setText(getString(R.string.first_waiting_text));
                    indexWaitingText = 1;

                } else if (indexWaitingText == 1) {
                    weatherDataBinding.tvWait.setText(getString(R.string.second_waiting_text));
                    indexWaitingText = 2;
                } else {
                    weatherDataBinding.tvWait.setText(getString(R.string.three_waiting_text));
                    indexWaitingText = 0;
                }
            }


            public void onFinish() {

                weatherDataBinding.tvWait.setVisibility(View.GONE);
            }

        }.start();
    }

    void startUpdateProgressbar() {
        List<ResponseWeather> listData = new ArrayList<>();

        new CountDownTimer(DURATION, INTERVAL_10) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {

                int progress = (int) ((DURATION - millisUntilFinished) * 100 / DURATION);


                weatherDataBinding.progressBar.setProgress(progress + 17);
                weatherDataBinding.progressPercentage.setText((progress + 17) + getString(R.string.percentage_symbole));

                if (index == 0) {
                    weatherDataViewModel.getWeatherData("rennes");
                    index = 1;
                } else if (index == 1) {
                    weatherDataViewModel.getWeatherData("paris");
                    index = 2;
                } else if (index == 2) {
                    weatherDataViewModel.getWeatherData("nantes");

                    index = 3;
                } else if (index == 3) {
                    weatherDataViewModel.getWeatherData("bordeaux");
                    index = 4;
                } else if (index == 4) {
                    weatherDataViewModel.getWeatherData("lyon");
                    index = 5;
                }
            }


            @SuppressLint("SetTextI18n")
            public void onFinish() {

                weatherDataBinding.clBottom.setVisibility(View.GONE);

                weatherDataBinding.btnRestart.setVisibility(View.VISIBLE);
                weatherDataBinding.tableLayout.setVisibility(View.VISIBLE);

                listData.addAll(weatherDataViewModel.getWeatherDataList());

                Log.e("TAG", "onFinish: " + listData.get(0));

                // data of rennes
                weatherDataBinding.tvRennesTeperature.setText(listData.get(0).getMain().getTemp().toString());

                switch (listData.get(0).getWeather().get(0).getIcon()) {
                    case "01d":
                        Glide.with(requireActivity()).load(R.drawable.ic_01d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "02d":
                        Glide.with(requireActivity()).load(R.drawable.ic_02d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "03d":
                        Glide.with(requireActivity()).load(R.drawable.ic_03d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "04d":
                        Glide.with(requireActivity()).load(R.drawable.ic_04d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "09d":
                        Glide.with(requireActivity()).load(R.drawable.ic_09d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "10d":
                        Glide.with(requireActivity()).load(R.drawable.ic_10d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "11d":
                        Glide.with(requireActivity()).load(R.drawable.ic_11d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "13d":
                        Glide.with(requireActivity()).load(R.drawable.ic_13d).into(weatherDataBinding.tvRennesCn);
                        break;
                    case "50d":
                        Glide.with(requireActivity()).load(R.drawable.ic_50d).into(weatherDataBinding.tvRennesCn);
                        break;
                    default:
                        break;
                }

                // data of Paris
                weatherDataBinding.tvParisTeperature.setText(listData.get(1).getMain().getTemp().toString());

                switch (listData.get(1).getWeather().get(0).getIcon()) {
                    case "01d":
                        Glide.with(requireActivity()).load(R.drawable.ic_01d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "02d":
                        Glide.with(requireActivity()).load(R.drawable.ic_02d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "03d":
                        Glide.with(requireActivity()).load(R.drawable.ic_03d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "04d":
                        Glide.with(requireActivity()).load(R.drawable.ic_04d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "09d":
                        Glide.with(requireActivity()).load(R.drawable.ic_09d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "10d":
                        Glide.with(requireActivity()).load(R.drawable.ic_10d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "11d":
                        Glide.with(requireActivity()).load(R.drawable.ic_11d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "13d":
                        Glide.with(requireActivity()).load(R.drawable.ic_13d).into(weatherDataBinding.tvParisCn);
                        break;
                    case "50d":
                        Glide.with(requireActivity()).load(R.drawable.ic_50d).into(weatherDataBinding.tvParisCn);
                        break;
                    default:
                        break;
                }

                // data of Nantes
                weatherDataBinding.tvNantesTemperature.setText(listData.get(2).getMain().getTemp().toString());

                switch (listData.get(2).getWeather().get(0).getIcon()) {
                    case "01d":
                        Glide.with(requireActivity()).load(R.drawable.ic_01d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "02d":
                        Glide.with(requireActivity()).load(R.drawable.ic_02d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "03d":
                        Glide.with(requireActivity()).load(R.drawable.ic_03d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "04d":
                        Glide.with(requireActivity()).load(R.drawable.ic_04d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "09d":
                        Glide.with(requireActivity()).load(R.drawable.ic_09d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "10d":
                        Glide.with(requireActivity()).load(R.drawable.ic_10d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "11d":
                        Glide.with(requireActivity()).load(R.drawable.ic_11d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "13d":
                        Glide.with(requireActivity()).load(R.drawable.ic_13d).into(weatherDataBinding.tvNantesCn);
                        break;
                    case "50d":
                        Glide.with(requireActivity()).load(R.drawable.ic_50d).into(weatherDataBinding.tvNantesCn);
                        break;
                    default:
                        break;
                }

                // data of Bordeaux
                weatherDataBinding.tvBrdTemperature.setText(listData.get(3).getMain().getTemp().toString());

                switch (listData.get(3).getWeather().get(0).getIcon()) {
                    case "01d":
                        Glide.with(requireActivity()).load(R.drawable.ic_01d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "02d":
                        Glide.with(requireActivity()).load(R.drawable.ic_02d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "03d":
                        Glide.with(requireActivity()).load(R.drawable.ic_03d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "04d":
                        Glide.with(requireActivity()).load(R.drawable.ic_04d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "09d":
                        Glide.with(requireActivity()).load(R.drawable.ic_09d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "10d":
                        Glide.with(requireActivity()).load(R.drawable.ic_10d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "11d":
                        Glide.with(requireActivity()).load(R.drawable.ic_11d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "13d":
                        Glide.with(requireActivity()).load(R.drawable.ic_13d).into(weatherDataBinding.tvBrdCn);
                        break;
                    case "50d":
                        Glide.with(requireActivity()).load(R.drawable.ic_50d).into(weatherDataBinding.tvBrdCn);
                        break;
                    default:
                        break;
                }

                // data of Lyon
                weatherDataBinding.tvLynTempurateur.setText(listData.get(4).getMain().getTemp().toString());

                switch (listData.get(4).getWeather().get(0).getIcon()) {
                    case "01d":
                        Glide.with(requireActivity()).load(R.drawable.ic_01d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "02d":
                        Glide.with(requireActivity()).load(R.drawable.ic_02d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "03d":
                        Glide.with(requireActivity()).load(R.drawable.ic_03d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "04d":
                        Glide.with(requireActivity()).load(R.drawable.ic_04d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "09d":
                        Glide.with(requireActivity()).load(R.drawable.ic_09d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "10d":
                        Glide.with(requireActivity()).load(R.drawable.ic_10d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "11d":
                        Glide.with(requireActivity()).load(R.drawable.ic_11d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "13d":
                        Glide.with(requireActivity()).load(R.drawable.ic_13d).into(weatherDataBinding.tvLynCn);
                        break;
                    case "50d":
                        Glide.with(requireActivity()).load(R.drawable.ic_50d).into(weatherDataBinding.tvLynCn);
                        break;
                    default:
                        break;
                }

            }

        }.start();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        weatherDataViewModel = new ViewModelProvider(this).get(WeatherDataViewModel.class);

        weatherDataBinding.imgGoBack.setOnClickListener(view1 -> {
            NavController navController = findNavController(this);
            Objects.requireNonNull(navController.getPreviousBackStackEntry());

            navController.popBackStack();
        });

        startUpdateWaitingMessage();
        startUpdateProgressbar();

    }
}