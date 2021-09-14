package com.kenshi.newsapiclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.newsapiclient.databinding.FragmentNewsBinding
import com.kenshi.newsapiclient.presentation.adapter.NewsAdapter
import com.kenshi.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var viewModel :  NewsViewModel
    private lateinit var newsAdapter : NewsAdapter
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private var country = "us"
    private var page = 1

    //Paging
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

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

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            //navigates from newsFragment to the InfoFragment using navController
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }

        initRecyclerView()
        viewNewsList()
        setSearchView()
    }

    private fun viewNewsList() {
        //invoke the getNewsHeadlines function of the view model
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner, {response->
            when(response) {
                is com.kenshi.newsapiclient.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{
                        Log.i("MYTAG", "came here ${it.articles.toList().size}")
                        newsAdapter.differ.submitList(it.articles.toList())
                        //20 is the default page size of the api

                        if(it.totalResults % 20 == 0){
                            pages = it.totalResults/20
                        }
                        else {
                            pages = it.totalResults/20 + 1
                        }
                        isLastPage = page == pages
                        //now, we can write codes to decide pagination

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
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun showProgressBar(){
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }

    //we need to write codes to set isScrolling as true if the user scrollong the list
    private val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        //manually implement pagination
        //but there is a better way of doing this with android jetpack pagin library
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            //if you can add a log here to see how these values change when we scroll down the list

            val hasReachedToEnd = topPosition+visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false
            }
        }
    }

    //search
    private fun setSearchView(){
        //we need to write codes to invoke the viewModel's searchNews function passing the typed search query
        //after that, we need to call to the viewSearchedNews function to display the observed results on the RecyclerView
        fragmentNewsBinding.svNews.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(queryString: String?): Boolean {

                viewModel.getSearchedNews("us", queryString.toString(), page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(queryString: String?): Boolean {
                //this functon will be invoked for each text change in the search view
                //but this is not efficient
                //Therefore we should give some time to the user to write some meaningful search query.
                //so will delay the execution by 2 seconds using delay function of the coroutines.
                MainScope().launch {
                    delay(2000)
                    viewModel.getSearchedNews("us", queryString.toString(), page)
                    viewSearchedNews()
                }
                return false
            }

        })

        //reset the list
        //close button click
        fragmentNewsBinding.svNews.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                initRecyclerView()
                viewNewsList()

                return false
            }
        })

    }

    //search
    fun viewSearchedNews(){

        viewModel.searchedNews.observe(viewLifecycleOwner, {response->
            when(response) {
                is com.kenshi.newsapiclient.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{
                        Log.i("MYTAG", "came here ${it.articles.toList().size}")
                        newsAdapter.differ.submitList(it.articles.toList())
                        //20 is the default page size of the api

                        if(it.totalResults % 20 == 0){
                            pages = it.totalResults/20
                        }
                        else {
                            pages = it.totalResults/20 + 1
                        }
                        isLastPage = page == pages
                        //now, we can write codes to decide pagination

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
}