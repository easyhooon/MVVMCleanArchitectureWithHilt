package com.kenshi.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kenshi.newsapiclient.databinding.FragmentNewsBinding
import com.kenshi.newsapiclient.presentation.adapter.NewsAdapter
import com.kenshi.newsapiclient.presentation.viewmodel.NewsViewModel

class NewsFragment : Fragment() {

    private lateinit var viewModel :  NewsViewModel
    private lateinit var newsAdapter : NewsAdapter
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private var country = "us"
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    //Why we use onViewCreated?
    //Because onViewCreated will be called immediately after all the views has been created
    //it is much safer to use this function to avoid unexpected errors that can be happen as result of partially created views

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()
        viewNewsList()
    }

    private fun viewNewsList() {
        //invoke the getNewsHeadlines function of the view model
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner, {response->
            when(response) {
                is com.kenshi.newsapiclient.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{
                        newsAdapter.differ.submitList(it.articles.toList())
                    }
                }

                is com.kenshi.newsapiclient.data.util.Resource.Error -> {
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_SHORT).show()
                    }
                }

                is com.kenshi.newsapiclient.data.util.Resource.Loading -> {
                    showProgressBar()
                }

            }
        })
    }

    private fun initRecyclerView() {
        //instead of constructing separate adapter instances locally, we can actually inject it as singleton dependency
        //newsAdapter = NewsAdapter()
        fragmentNewsBinding.apply {
            rvNews.adapter = newsAdapter
            rvNews.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar(){
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }
}