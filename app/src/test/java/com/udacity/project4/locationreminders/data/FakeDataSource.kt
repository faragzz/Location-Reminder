package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var tasks:MutableList<ReminderDTO> = mutableListOf()): ReminderDataSource {

//    TODO: Create a fake data source to act as a double to the real data source

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        TODO("Return the reminders")
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        tasks?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        return tasks.firstOrNull { it.id == id }?.let {
            Result.Success(it)
        } ?: Result.Error("data 404")
    }

    override suspend fun deleteAllReminders() {
        tasks?.clear()
    }


}