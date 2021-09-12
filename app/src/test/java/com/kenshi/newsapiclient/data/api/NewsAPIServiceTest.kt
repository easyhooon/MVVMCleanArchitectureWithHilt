package com.kenshi.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var service:NewsAPIService
    private lateinit var server:MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)

        val source = inputStream.source().buffer()

        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        //enqueue the mock response to the mock web server
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receiveExpected(){
        //runBlocking is the coroutine builder we use for testing.
        //This run a new coroutine and blocks the current thread until its completion
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            //Test passed
            //that means we are not getting a null response body from the mock web server

            //for the mockwebserver we are not adding the base url
            //therefore remove base url part from url
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=309b2476ac80468f9a202cc502c848cb")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking{
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking{
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles

            val article = articlesList[0]

            assertThat(article.author).isEqualTo("Dakin Andone, Stella Chan and Cheri Mossburg, CNN")

            assertThat(article.url).isEqualTo("Matthew Futterman")
            assertThat(article.url).isEqualTo("https://www.nytimes.com/2021/09/09/sports/tennis/us-open-fernandez-sabalenka.html")
            assertThat(article.publishedAt).isEqualTo("2021-09-10T02:09:28Z")
        }
    }



    //inside this teardown function, we will write codes to shutdown the MockWebServer
    @After
    fun tearDown() {
        server.shutdown()
    }
    //we don't have to write codes to start the server
    //Because when we are invoking server's functions it will be automatically started
    //But we have to write codes to shut down the server after all the testing are done
}