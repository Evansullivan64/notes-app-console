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




   // var activeresults = ArrayList<Note>()
    fun listActiveNotes(): String {


       return if (numberOfActiveNotes() == 0) {
           "No active notes stored"
       } else {
           var listOfNotes = ""
           for (i in notes.indices) {
               if(!notes.get(i).isNoteArchived) {


                   listOfNotes += "${i}: ${notes[i]} \n"
               }
           }
           listOfNotes
       }







    }





    fun listArchivedNotes(): String {
        return if (numberOfArchivedNotes() == 0) {
            "No archived notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                if(notes.get(i).isNoteArchived) {

                    listOfNotes += "${i}: ${notes[i]} \n"
                }
            }
            listOfNotes
        }
    }



    fun numberOfArchivedNotes(): Int {
       return  listArchivedNotes().length
    }

    fun numberOfActiveNotes(): Int {
       return listActiveNotes().length
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

    fun numberOfNotesByPriority(priority:Int): Int {

        return listNotesBySelectedPriority(priority).length
    }
}