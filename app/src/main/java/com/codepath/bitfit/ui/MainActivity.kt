package com.codepath.bitfit.ui

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.bitfit.R
import com.codepath.bitfit.adapter.SleepAdapter
import com.codepath.bitfit.databinding.ActivityMainBinding
import com.codepath.bitfit.model.SleepItem
import kotlinx.coroutines.flow.collectLatest
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val viewModel :MainViewModel by viewModels()

    private val sleepAdapter :SleepAdapter by lazy {
        SleepAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding  =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpUI()
        getSleepData()
        insertSleepData()

    }

    private fun setUpUI(){

        binding.sleepRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = sleepAdapter
        }


    }



    private fun getSleepData() {

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sleepData.collectLatest {sleepData->
                    var averageSleepTime =0.0
                    var averageFeeling =0.0

                    if(sleepData.isNotEmpty()){
                        averageSleepTime =sleepData.map { it.sleepTime }.average()
                        averageFeeling = sleepData.map { it.feeling }.average()
                    }

                    binding.averageSleepValueTv.text = String.format("%.1f",averageSleepTime)
                    binding.averageFeelingValueTv.text = String.format("%.1f",averageFeeling)

                    sleepAdapter.submitList(sleepData)


                }
            }

        }


    }

    private fun insertSleepData(){
        binding.saveBtn.setOnClickListener(){
            val currentDate = Date()
            val sleepTime = binding.sleepTimeSlider.value
            val feeling = binding.feelingSlider.value.toInt()
            val notes = binding.notesEditText.text.toString()

            if(sleepTime <= 0){
                Toast.makeText(this,getString(R.string.enter_valid_sleep_time),Toast.LENGTH_SHORT).show()

            }else if(feeling <= 0){
                Toast.makeText(this,getString(R.string.enter_valid_feeling),Toast.LENGTH_SHORT).show()
            }else{
                val sleepItem = SleepItem(currentDate,sleepTime,feeling,notes)
                viewModel.insertSleepItem(sleepItem)
                binding.sleepTimeSlider.value = 0f
                binding.feelingSlider.value = 0f
                binding.notesEditText.setText("")
                binding.addSleepLayout.visibility = View.GONE
            }
        }
    }






}