package com.example.android_graphql

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.example.android_graphql.ui.theme.Android_graphqlTheme
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_graphqlTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    FetchData()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Android_graphqlTheme {
        Greeting("Android")
    }
}


@Composable
fun FetchData() {
    // Create a client
    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://countries.trevorblades.com/graphql")
        .build()

    LaunchedEffect(Unit) {
        try {
            val response = apolloClient.query(GetContinentsQuery()).execute()
            val continents = response.data?.continents ?: emptyList()

            // Use the fetched data in your UI
            println("Continents: $continents")

        } catch (e: ApolloException) {
            // Handle exceptions like network errors
            println("Error fetching continents: $e")
        }
    }
}

