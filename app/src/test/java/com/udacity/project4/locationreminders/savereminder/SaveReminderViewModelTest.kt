package com.udacity.project4.locationreminders.savereminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import com.udacity.project4.locationreminders.reminderslist.getOrAwaitValue

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var repo: FakeDataSource
    private lateinit var saveReminderViewModelTest: SaveReminderViewModel
    @Before
    fun setup() {
        repo = FakeDataSource()
        saveReminderViewModelTest = SaveReminderViewModel(ApplicationProvider.getApplicationContext(),repo)
    }

    @Test
    fun checkLiveData_setsNewTaskEvent_NotNull(){
        val data = ReminderDataItem("TitleTest", "Test", "TestDes", 12.0, 20.0,"1")
        saveReminderViewModelTest.reminderTitle.value = data.title
        saveReminderViewModelTest.reminderDescription.value = data.description
        saveReminderViewModelTest.reminderSelectedLocationStr.value = data.location
        saveReminderViewModelTest.latitude.value= data.latitude
        saveReminderViewModelTest.longitude.value = data.longitude

        val value1 = saveReminderViewModelTest.reminderTitle.getOrAwaitValue()
        val value2 = saveReminderViewModelTest.reminderDescription.getOrAwaitValue()
        val value3 = saveReminderViewModelTest.reminderSelectedLocationStr.getOrAwaitValue()
        val value4 = saveReminderViewModelTest.latitude.getOrAwaitValue()
        val value5= saveReminderViewModelTest.longitude.getOrAwaitValue()

        MatcherAssert.assertThat(value1, CoreMatchers.not(CoreMatchers.nullValue()))
        MatcherAssert.assertThat(value2, CoreMatchers.not(CoreMatchers.nullValue()))
        MatcherAssert.assertThat(value3, CoreMatchers.not(CoreMatchers.nullValue()))
        MatcherAssert.assertThat(value4, CoreMatchers.not(CoreMatchers.nullValue()))
        MatcherAssert.assertThat(value5, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}