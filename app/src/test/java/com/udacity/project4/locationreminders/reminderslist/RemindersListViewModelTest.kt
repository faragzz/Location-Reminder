package com.udacity.project4.locationreminders.reminderslist

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@Config(sdk = intArrayOf(Build.VERSION_CODES.O_MR1))
class RemindersListViewModelTest {
    private lateinit var  dataList: MutableList<ReminderDTO>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var repo: FakeDataSource
    private lateinit var remindersListViewModelTest:RemindersListViewModel
    @Before
    fun setup() {
        repo = FakeDataSource()
        remindersListViewModelTest = RemindersListViewModel(ApplicationProvider.getApplicationContext(),repo)
        runBlockingTest {
            dataList = mutableListOf(
                ReminderDTO("TestTitle1","TestDesc1","TestLocation1",3.6,3.4),
                ReminderDTO("TestTitle2","TestDesc2","TestLocation2",3.6,3.4),
                ReminderDTO("TestTitle3","TestDesc3","TestLocation3",3.6,3.4)
            )
            repo.saveReminder(dataList[0])
            repo.saveReminder(dataList[1])
            repo.saveReminder(dataList[2])
        }
    }
    @After
    fun tearDown()= runBlocking {
        stopKoin()
        repo.deleteAllReminders()
    }

    @Test
    fun loadReminders_DisplayLoading()= runBlockingTest{
        mainCoroutineRule.pauseDispatcher()
        remindersListViewModelTest.loadReminders()
        assertThat(remindersListViewModelTest.showLoading.value,`is`(true))
        mainCoroutineRule.resumeDispatcher()
        assertThat(remindersListViewModelTest.showLoading.value,`is`(false))
    }

    @Test
    fun getRemindersList(){
        remindersListViewModelTest.loadReminders()
        assertThat( remindersListViewModelTest.remindersList.value?.size, `is`(dataList.size))
    }
    @Test
    fun loadReminders_DisplayReturnError(){
        repo.setReturnError(true)
        remindersListViewModelTest.loadReminders()
        assertThat(remindersListViewModelTest.showSnackBar.value,`is`("Error getting reminders"))
    }
}