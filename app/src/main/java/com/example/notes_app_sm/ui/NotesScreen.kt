package com.example.notes_app_sm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.notes_app_sm.data.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel : NotesViewModel, onAddNote: () -> Unit, onEditNote: (Int) -> Unit)
{
    val notes by viewModel.notes.collectAsState()
    val query by viewModel.query.collectAsState()

    var noteToDelete by remember{
        mutableStateOf<NoteEntity?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My Notes", fontWeight = FontWeight.Bold)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Text(
                    text = "Add",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ){paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp))
        {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = query,
                onValueChange = {
                    viewModel.updateSearchQuery(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Search Notes...")},
                trailingIcon = {
                    if(query.isNotEmpty()){
                        TextButton(onClick = {
                            viewModel.updateSearchQuery("")
                        }){
                            Text("Clear")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if(notes.isEmpty())
                EmptyNotesMessage()
            else{
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = paddingValues
                ) {
                    items(
                        items = notes,
                        key = {it.id}
                    ){note -> NoteCard(
                        note = note,
                        onClick = {onEditNote(note.id)},
                        onPin = {viewModel.togglePin(note)},
                        onDelete = {noteToDelete = note}
                    )}
                }
            }
        }
    }

    noteToDelete?.let{note->
        AlertDialog(
            onDismissRequest = {
            noteToDelete = null
            },
            title = {
               Text(text = "Delete Note?")
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteNote(note)
                    noteToDelete = null
                })
                {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        noteToDelete = null
                    }
                )
                {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

@Composable
fun NoteCard(
    note : NoteEntity,
    onClick : () -> Unit,
    onPin : () -> Unit,
    onDelete : () -> Unit)
{
    Card(modifier = Modifier.fillMaxWidth().clickable{
        onClick()}) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = if(note.title.isBlank()) "Untitled" else note.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold)
                TextButton(onClick = onPin) {
                    Text(text = if(note.isPinned)"Unpin" else "Pin")
                }
                TextButton(onClick = onDelete){
                    Text(text = "Delete")
                }
                if(note.content.isNotBlank())
                {
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 4
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyNotesMessage()
{
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Notebook",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = "No notes yet!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            Text(
                text = "Tap Add to make you first note!"
            )
        }
    }
}