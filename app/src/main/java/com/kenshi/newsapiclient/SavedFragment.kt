package com.kenshi.newsapiclient

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kenshi.newsapiclient.databinding.FragmentSavedBinding
import com.kenshi.newsapiclient.presentation.adapter.NewsAdapter
import com.kenshi.newsapiclient.presentation.viewmodel.NewsViewModel


class SavedFragment : Fragment() {
    private lateinit var fragmentSavedBinding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSavedBinding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            //navigates from newsFragment to the InfoFragment using navController
            findNavController().navigate(
                R.id.action_savedFragment_to_infoFragment,
                bundle
            )
        }

        initRecyclerView()

        viewModel.getSavedNews().observe(viewLifecycleOwner,{
            newsAdapter.differ.submitList(it)
        })

        //we have two parameters for drag directions and for swipe directions.
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //do not need
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticles(article)
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo"){
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
            }
        }
        //attach the itemTouchHelperCallback to the recycler view
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(fragmentSavedBinding.rvSaved)
        }
    }

    private fun initRecyclerView() {
        //instead of constructing separate adapter instances locally, we can actually inject it as singleton dependency
        //newsAdapter = NewsAdapter()
        fragmentSavedBinding.rvSaved.apply {
            //we can use the adapter we just got from the MainActivity
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            //allow to user to delete the news item by swiping it

        }
    }
}