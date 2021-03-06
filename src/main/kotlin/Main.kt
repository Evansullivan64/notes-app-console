import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import persistence.YAMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.utils
import utils.utils.isValidCategory
import utils.utils.isValidStatus
import utils.utils.validpriority
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
//private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
private val noteAPI = NoteAPI(YAMLSerializer(File("notes.yml")))

fun main(args: Array<String>) {
    runMenu()
}

fun runMenu() {
    do {
        val option = mainMenu()

        when (option) {
            1  -> addNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            5 -> archiveNotes()
            6 -> load()
            7 -> save()
            0  -> exitApp()
            else -> System.out.println("Invalid  option entered: ${option}")
        }
    } while (true)
}




fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > | _               ___               
| \ | |     | |             / _ \              
|  \| | ___ | |_ ___  ___  / /_\ \_ __  _ __   
| . ` |/ _ \| __/ _ \/ __| |  _  | '_ \| '_ \  
| |\  | (_) | ||  __/\__ \ | | | | |_) | |_) | 
\_| \_/\___/ \__\___||___/ \_| |_/ .__/| .__/  
                                 | |   | |     
                                 |_|   |_|            
         > ----------------------------------
         > |                                |
         > |   1) Add a note                |
         > |   2) List all notes methods    |
         > |   3) Update a note             |
         > |   4) Delete a note             |
         > |   5) Archive a note            |
         > |   6) Load Note                 |  
         > |   7) Save note                |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))


}

fun listNotes() {
    if (noteAPI.numberOfNotes() > 0) {
    val option = readNextInt(""" 
         > ----------------------------------
            > _     _     _     _   _       _             
            >| |   (_)   | |   | \ | |     | |            
            >| |    _ ___| |_  |  \| | ___ | |_ ___  ___  
            >| |   | / __| __| | . ` |/ _ \| __/ _ \/ __| 
            >| |___| \__ \ |_  | |\  | (_) | ||  __/\__ \ 
            >\_____/_|___/\__| \_| \_/\___/ \__\___||___/ 
                                                      
         > ----------------------------------
         > |   1) List all notes            |
         > |   2) List active notes         |
         > |   3) List archived note        |
         > |   4) List note starting with   |
         > |   5) Search Note by title      |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
    when(option){
        1-> listAllNotes()
        2 -> listActiveNotes()
        3-> listArchivedNotes()
        4 -> listNoteWithLetter()
        5 -> searchNotes()
        else -> println("Invalid option entered: $option");
    }
} else {
    println("Option Invalid - No notes stored");
}

}

 fun listActiveNotes(){
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes(){
    println(noteAPI.listArchivedNotes())
}

fun listNoteWithLetter(){
    val letter: String = readNextLine("Enter a letter")
    println(noteAPI.listnoteswithletter(letter))
}

fun addNote(){
    //logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")
    var notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    while(!validpriority(notePriority)){
        notePriority = readNextInt("Value not in range, Enter a priority (1-low, 2, 3, 4, 5-high): ")
    }
    var noteCategory = readNextLine("Enter a category for the note: ")
    while(!isValidCategory(noteCategory)){
        noteCategory = readNextLine(""" 
           > |    Categories:
           > | Home:
           > | Reminder:
           > | behaviour:
           > | music:
           > | art:
           > | Enter a category for the note:
        """.trimMargin(">"))
    }
    val status = "ToDo"
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false,status))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listAllNotes(){

    println(noteAPI.listAllNotes())
}

fun archiveNotes(){
    listAllNotes()

    if(noteAPI.numberOfNotes()> 0){
        val indextoArchive = readNextInt("enter a note to archive")
        if(noteAPI.isValidIndex(indextoArchive)){
           if(noteAPI.archiveNote(indextoArchive)){
               println("Archived successfully")
           }else{
               println("archive failed")
           }
        }
    }
}

fun updateNote(){
    listAllNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            var notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            while(!validpriority(notePriority)){
                notePriority = readNextInt("Value not in range, Enter a priority (1-low, 2, 3, 4, 5-high): ")
            }
            var noteCategory = readNextLine("Enter a category for the note: ")
            while(!isValidCategory(noteCategory)){
                noteCategory = readNextLine(""" 
           > |    Categories:
           > | Home:
           > | Reminder:
           > | behaviour:
           > | music:
           > | art:
           > | Enter a category for the note:
        """.trimMargin(">"))
            }
            var status = readNextLine("Enter a status (Todo or Done) for the note: ")
            while (!isValidStatus(status)){
                status = readNextLine("Enter a status (Todo or Done) for the note: ")
            }




            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false,status))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }

}

fun deleteNote(){
    listAllNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun searchNotes() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = noteAPI.searchByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}



fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}