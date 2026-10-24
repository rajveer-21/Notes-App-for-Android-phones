package com.example.notes_app_sm.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes_app_sm.data.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNotesScreen(note : NoteEntity?, onSave:(String, String) -> Unit, onBack:() -> Unit) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.title ?: "") }
    val isEditing: Boolean = note != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if(isEditing) "Edit Note" else "New Note")
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if(title.isNotBlank() || content.isNotBlank())
                                onSave(title, content)
                        }
                    ){
                        Text("Save")
                    }
                }
            )
        }
    ){paddingValues -> Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ){
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Title")
            },
            textStyle = MaterialTheme.typography.headlineSmall,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Content")
            },
            textStyle = MaterialTheme.typography.headlineMedium,


        )
    }}

}