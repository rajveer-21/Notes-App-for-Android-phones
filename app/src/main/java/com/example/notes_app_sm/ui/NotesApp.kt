package com.example.notes_app_sm.ui
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes_app_sm.data.*
import com.example.notes_app_sm.ui.*

@Composable
fun NotesApp(repository: NotesRepository)
{
    val navController = rememberNavController()
    val viewModel: NotesViewModel = viewModel(factory = NotesViewModelFactory(repository))

    NavHost(navController = navController, startDestination = "notes")
    {
        composable("notes")
        {
            NotesScreen(viewModel = viewModel, onAddNote = {navController.navigate("add")}, onEditNote = { noteId -> navController.navigate("edit/$noteId")})
        }
        composable("add")
        {
            AddEditNoteScreen(note = null, onSave = {title, content -> viewModel.addNote(title, content)
            navController.popBackStack()}, onBack = {navController.popBackStack()})
        }
        composable("edit/{noteId}", arguments = listOf(navArgument("noteId"){type = NavType.IntType}))
        {
            backStackEntry -> backStackEntry.arguments?.getInt("noteId") ?: return@composable
            var note by remember{mutableStateOf(viewModel.notes.value.find{it.id == noteId})}
            LaunchedEffect(noteId){note = viewModel.getNote(noteId)}
            AddEditNoteScreen(note = note, onSave = {title, content -> viewModel.updateNote(id = noteId, title = title, content = content) navController.popBackStack()}
            , onBack = { navController.popBackStack() })
        }
    }
}