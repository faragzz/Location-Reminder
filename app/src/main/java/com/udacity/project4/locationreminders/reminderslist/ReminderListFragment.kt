package com.udacity.project4.locationreminders.reminderslist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.udacity.project4.R
import com.udacity.project4.authentication.AuthenticationActivity
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentRemindersBinding
import com.udacity.project4.locationreminders.RemindersActivity
import com.udacity.project4.locationreminders.savereminder.CurrentDataViewModel
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import com.udacity.project4.utils.setTitle
import com.udacity.project4.utils.setup
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListFragment : BaseFragment() {
    //use Koin to retrieve the ViewModel instance
    private val currentviewModel by activityViewModels<CurrentDataViewModel>()
    override val _viewModel: RemindersListViewModel by viewModel()

    private lateinit var binding: FragmentRemindersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_reminders, container, false
            )
        binding.viewModel = _viewModel

        binding.reminderssRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))

        if(_viewModel.containInList.value == false){
            binding.noDataTextView.setVisibility(View.INVISIBLE);
        }else{
            binding.noDataTextView.setVisibility(View.VISIBLE);
        }

        binding.refreshLayout.setOnRefreshListener { _viewModel.loadReminders() }
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.addReminderFAB.setOnClickListener {
            navigateToAddReminder()
        }
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        //load the reminders list on the ui
        _viewModel.loadReminders()
    }

    private fun navigateToAddReminder() {
        //use the navigationCommand live data to navigate between the fragments
        currentviewModel.title.value = ""
        currentviewModel.des.value = ""
        currentviewModel.lat.value = 0.0
        currentviewModel.long.value = 0.0
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(
                ReminderListFragmentDirections.toSaveReminder()
            )
        )
    }

    private fun setupRecyclerView() {
        _viewModel.remindersList.observe(viewLifecycleOwner, Observer {
            binding.reminderssRecyclerView.adapter = RemindersListAdapter(
                object : RemindersListAdapter.OnClickListener {
                    override fun onClick(item: ReminderDataItem) {
                        currentviewModel.title.value = item.title
                        currentviewModel.des.value = item.description
                        currentviewModel.lat.value = item.latitude
                        currentviewModel.long.value = item.longitude
                        _viewModel.navigationCommand.postValue(NavigationCommand.To
                            (ReminderListFragmentDirections.toSaveReminder()))
                    }
                }, it
            )
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AuthUI.getInstance().signOut(requireContext())
                Thread.sleep(1000)
                val intent = Intent(requireContext(), AuthenticationActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
//        display logout as menu item
        inflater.inflate(R.menu.main_menu, menu)
    }

}
