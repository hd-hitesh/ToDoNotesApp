package com.example.todonotesapp.clickListeners

import com.example.todonotesapp.model.Notes

interface ItemClickListeners {
    fun onClick(notes: Notes)
}