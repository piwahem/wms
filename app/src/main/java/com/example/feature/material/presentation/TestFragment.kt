package com.example.feature.material.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.R
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_mode_scan).isVisible = false
        menu.findItem(R.id.action_mode_finish).isVisible =false
        menu.findItem(R.id.action_receive_scan).isVisible =false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(KEY_TYPE).let {
            tvLabel.text = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val KEY_TYPE = "KEY_TYPE"
        fun newInstance(page: String?): TestFragment {
            return TestFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TYPE, page)
                }
            }
        }
    }
}