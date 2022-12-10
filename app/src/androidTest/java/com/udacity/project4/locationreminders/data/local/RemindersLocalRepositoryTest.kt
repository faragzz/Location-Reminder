package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: RemindersLocalRepository
    private lateinit var database: RemindersDatabase

    @After
    fun close() {
        database.close()
    }

    @Before
    fun setup() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()

        repo = RemindersLocalRepository(
            database.reminderDao(),
            Dispatchers.Main
        )
    }

    @Test
    fun getReminder_id()= runBlocking{
        val data = ReminderDTO("TestTitle","TestDes","TestLocation",70.0,80.0,"2")
        repo.saveReminder(data)
        val dataReturned = repo.getReminder(data.id)

            assertThat(data.title, `is`(dataReturned.let { data.title }))
            assertThat(data.description, `is`(dataReturned.let {  data.description}))
            assertThat(data.latitude, `is`(dataReturned.let { data.latitude}))
            assertThat(data.longitude, `is`(dataReturned.let { data.longitude }))
            assertThat(data.location, `is`(dataReturned.let { data.location }))


    }


    @Test
    fun deleteReminder()= runBlocking{
        val data = ReminderDTO("TestTitle","TestDes","TestLocation",70.0,80.0,"2")
        repo.saveReminder(data)
        repo.deleteAllReminders()
        val dataReturned = repo.getReminder(data.id)
        assertThat(dataReturned is Result.Error, `is`(true))
    }

}

