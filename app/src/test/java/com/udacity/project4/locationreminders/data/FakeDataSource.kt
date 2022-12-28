package com.udacity.project4.locationreminders.data

import android.widget.Toast
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var tasks:MutableList<ReminderDTO> = mutableListOf()): ReminderDataSource {

var shouldReturnError = false

    var remindersList: MutableList<ReminderDTO> = mutableListOf()

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }
    override suspend fun getReminders(): Result<List<ReminderDTO>> =
        if (shouldReturnError)
            Result.Error("Error getting reminders")
        else
            Result.Success(remindersList)

    override suspend fun saveReminder(reminder: ReminderDTO) {
        remindersList.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> =
        if (shouldReturnError)
            Result.Error("Error getting reminder with id $id")
        else {
            remindersList.firstOrNull { it.id == id }?.let {
                Result.Success(it)
            } ?: Result.Error("Reminder not found")
        }

    override suspend fun deleteAllReminders() {
        remindersList.clear()
    }


}