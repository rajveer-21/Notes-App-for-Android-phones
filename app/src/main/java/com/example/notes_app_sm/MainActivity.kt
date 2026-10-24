package com.example.notes_app_sm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.notes_app_sm.ui.*
import com.example.notes_app_sm.data.*

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        val application = application as MainApplication
        setContent{
            NotesApp(repository = application.repository)
        }
    }
}