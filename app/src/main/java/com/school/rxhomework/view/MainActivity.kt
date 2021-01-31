package com.school.rxhomework.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.school.rxhomework.viewmodel.ActivityViewModel
import com.school.rxhomework.adapter.Adapter
import com.school.rxhomework.util.State
import com.school.rxhomework.databinding.ActivityMainBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ActivityViewModel>()
    private val cd = CompositeDisposable()


    private lateinit var mainAdapter: Adapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainAdapter = Adapter()
        binding.apply {
            setContentView(root)
            recyclerView.adapter = mainAdapter
            viewModel.state.observe(this@MainActivity) { state ->
                when (state) {
                    State.Loading -> root.isRefreshing = true
                    is State.Loaded -> {
                        root.isRefreshing = false
                        mainAdapter.submitList(state.content)
                    }
                }
            }
            viewModel.subject.onNext(Unit)
            cd.add(
                root.refreshes().subscribe(viewModel.subject::onNext)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.dispose()
    }

}
