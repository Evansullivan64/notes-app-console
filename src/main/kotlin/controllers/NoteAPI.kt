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
           var note:Note =  notes[i]
            if(note.isNoteArchived == false){
               activeresults.add(notes[i])
            }
        }

        return activeresults.toString()

    }




    var archivedresults = ArrayList<Note>()
    fun listArchivedNotes(): String {


        for (i in 0..notes.size-1){
            var note:Note =  notes[i]
            if(note.isNoteArchived){
                activeresults.add(notes[i])
            }
        }

        return activeresults.toString()


    }



    fun numberOfArchivedNotes(): Int {
       return archivedresults.size
    }

    fun numberOfActiveNotes(): Int {
       return activeresults.size
    }

    fun listNotesBySelectedPriority(priority: Int): String {
        var result = ArrayList<Note>()
        for(note in notes){
            if(note.notePriority.equals(priority)){
                result.add(note)
            }
        }
        return result.toString()
    }

    fun numberOfNotesByPriority(): Int {
    for(priority:Int in 0..4){
        if(){

        }
    }
    }
}