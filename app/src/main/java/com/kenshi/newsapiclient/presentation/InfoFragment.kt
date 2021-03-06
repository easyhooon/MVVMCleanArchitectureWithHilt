package com.kenshi.newsapiclient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kenshi.newsapiclient.R
import com.kenshi.newsapiclient.databinding.FragmentInfoBinding
import com.kenshi.newsapiclient.presentation.viewmodel.NewsViewModel

class InfoFragment : Fragment() {
    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var viewModel : NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)

        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle

        viewModel = (activity as MainActivity).viewModel

        //now, we will write codes to display the news article on the webView
        fragmentInfoBinding.wvInfo.apply {
            webViewClient = WebViewClient()
            //load url
            if(article.url != null){
                loadUrl(article.url)
            }
        }

        fragmentInfoBinding.fabSave.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Saved Successfully", Snackbar.LENGTH_LONG).show()
            //Room also allows us to get the inserted row id as a return value
            //we could have used that for the verification
        }

    }
}