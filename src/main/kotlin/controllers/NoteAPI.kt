package controllers

import models.Note
import persistence.Serializer

class NoteAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType


    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            foundNote.status = note.status
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }





    fun listAllNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(notes)


    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, notes);
    }

    fun listnoteswithletter( letter:String): String =
        if  (numberOfNotes() == 0)  "No notes stored"
    else formatListString(notes.filter { note -> note.noteTitle.startsWith(letter) })




    fun listActiveNotes(): String =
        if  (numberOfActiveNotes() == 0)  "No active notes stored"
        else formatListString(notes.filter { note -> !note.isNoteArchived})

    fun listArchivedNotes(): String =
        if  (numberOfArchivedNotes() == 0) "No archived notes stored"
        else formatListString(notes.filter { note -> note.isNoteArchived})

    fun numberofnoteswithletter(letter: String)=
    notes.filter { note -> note.noteTitle.startsWith(letter) }
    .count()


    fun numberOfArchivedNotes(): Int = notes
        .filter { note: Note -> note.isNoteArchived }
        .count()

    fun numberOfActiveNotes(): Int = notes
        .filter { note: Note -> !note.isNoteArchived }
        .count()

    fun listNotesBySelectedPriority(priority: Int): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                if (notes[i].notePriority == priority) {
                    listOfNotes +=
                        """$i: ${notes[i]}
                        """.trimIndent()
                }
            }
            if (listOfNotes.equals("")) {
                "No notes with priority: $priority"
            } else {
                "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
            }
        }
    }

/*
    fun listNotesBySelectedPriority(priority: Int): String =
        if(notes.isEmpty()) "no notes stored"
    else notes.filter { note: Note -> notes[priority].notePriority == priority}
            .joinToString (separator = "\n"){ note -> notes.indexOf(note).toString()+": "+note.toString() }
    */

    fun numberOfNotesByPriority(priority: Int): Int {

        return notes.stream()
            .filter{note: Note -> note.notePriority == priority}
            .count()
            .toInt()
    }


    fun archiveNote(noteToArchive: Int): Boolean {
        if (isValidIndex(noteToArchive)) {
            val noteToArchive = notes[noteToArchive]
            if (!noteToArchive.isNoteArchived) {
                noteToArchive.isNoteArchived = true
                return true
            }
        }
        return false
    }

    fun searchByTitle (searchString : String) =
        formatListString(
            notes.filter { note -> note.noteTitle.contains(searchString, ignoreCase = true) })

    fun formatListString(notesToFormat : List<Note>) : String =
        notesToFormat
            .joinToString (separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString() }

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }
}