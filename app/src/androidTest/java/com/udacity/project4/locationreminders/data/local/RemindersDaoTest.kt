package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {


    private lateinit var database: RemindersDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun close() {
        database.close()
    }

    @Test
    fun saveData_getDataById() {
        val data = ReminderDTO("TestTitle","TestDes","TestLocation",70.0,80.0,"2")

        runBlockingTest {
            database.reminderDao().saveReminder(data)

            val result = database.reminderDao().getReminderById(data.id)
            assertThat(result as ReminderDTO, notNullValue())

            with(result) {
                assertThat(id, `is`(data.id))
                assertThat(title, `is`(data.title))
                assertThat(description, `is`(data.description))
                assertThat(latitude, `is`(data.latitude))
                assertThat(longitude, `is`(data.longitude))
                assertThat(location, `is`(data.location))
            }
        }
    }


    @Test
    fun saveData_deleteData_ReturnEmptyList() {
        val data1 = ReminderDTO("TestTitle1","TestDes1","TestLocation1",70.0,80.0,"1")
        val data2 = ReminderDTO("TestTitle2","TestDes2","TestLocation2",70.0,80.0,"2")
        val data3 = ReminderDTO("TestTitle3","TestDes3","TestLocation3",70.0,80.0,"3")
        val data4 = ReminderDTO("TestTitle4","TestDes4","TestLocation4",70.0,80.0,"4")

        runBlockingTest {
            with(database.reminderDao()) {
                saveReminder(data1)
                saveReminder(data2)
                saveReminder(data3)
                saveReminder(data4)

                deleteAllReminders()

                assertThat(getReminders(), `is`(emptyList()))
            }
        }
    }

    @Test
    fun saveData_getAllData_ReturnList()  {
        val data1 = ReminderDTO("TestTitle1","TestDes1","TestLocation1",70.0,80.0,"1")
        val data2 = ReminderDTO("TestTitle2","TestDes2","TestLocation2",70.0,80.0,"2")
        val data3 = ReminderDTO("TestTitle3","TestDes3","TestLocation3",70.0,80.0,"3")
        val data4 = ReminderDTO("TestTitle4","TestDes4","TestLocation4",70.0,80.0,"4")

        runBlockingTest {

            with(database.reminderDao()) {
                saveReminder(data1)
                saveReminder(data2)
                saveReminder(data3)
                saveReminder(data4)

                assertThat(getReminders().size, `is`(4))
            }
        }
    }

    @Test
    fun getDataById_notExists_returnNull()  {
        val data1 = ReminderDTO("TestTitle1","TestDes1","TestLocation1",70.0,80.0,"1")
        runBlockingTest {
            val result = database.reminderDao().getReminderById(data1.id)
            assertThat(result, nullValue())
        }
    }

}