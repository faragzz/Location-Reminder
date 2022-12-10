package com.udacity.project4.locationreminders.reminderslist

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.data.FakeDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@Config(sdk = intArrayOf(Build.VERSION_CODES.O_MR1))
class RemindersListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var repo: FakeDataSource
    private lateinit var remindersListViewModelTest:RemindersListViewModel
    @Before
    fun setup() {
        repo = FakeDataSource()
        remindersListViewModelTest = RemindersListViewModel(ApplicationProvider.getApplicationContext(),repo)
    }

    @Test
    fun addNewTask_setsNewTaskEvent(){
        val data = ReminderDataItem("TitleTest", "Test", "TestDes", 12.0, 20.0,"1")
        remindersListViewModelTest.addNewTask(data)
        val value = remindersListViewModelTest.remindersList.getOrAwaitValue()
        assertThat(value,not(nullValue()))
    }
    //TODO: provide testing to the RemindersListViewModel and its live data objectsZ

}