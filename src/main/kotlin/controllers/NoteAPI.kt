package controllers

import models.Note

class NoteAPI {


    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }


    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }

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




    var activeresults = ArrayList<Note>()
    fun listActiveNotes(): String {


        for (i in 0..notes.size-1){
            if(notes[i].isNoteArchived.equals(false)){
activeresults.add(notes[i])
            }
        }
        if (activeresults.isEmpty()) {
            "no active notes"
        }
        return activeresults.toString()

    }




    var archivedresults = ArrayList<Note>()
    fun listArchivedNotes(): String {


        for (i in 0..notes.size-1){
            if(notes[i].isNoteArchived.equals(true)){
                archivedresults.add(notes[i])
            }
        }
        if (archivedresults.isEmpty()) {
            "no archived notes"
        }
        return archivedresults.toString()

    }



    fun numberOfArchivedNotes(): Int {
       return archivedresults.size
    }

    fun numberOfActiveNotes(): Int {
       return activeresults.size
    }
}