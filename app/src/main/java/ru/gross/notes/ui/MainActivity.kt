package ru.gross.notes.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import ru.gross.notes.R
import ru.gross.notes.common.handle
import ru.gross.notes.databinding.ActivityMainBinding
import ru.gross.notes.di.InjectableViewModelFactory
import ru.gross.notes.notesComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: InjectableViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesComponent().inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.notesPresenter.adapter = NotesAdapter()

        viewModel.notes.observe(this, Observer { state ->
            binding.apply { state.handle(successHandler = { notes = it }) }
        })
    }
}