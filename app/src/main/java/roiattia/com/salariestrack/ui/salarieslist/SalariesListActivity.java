package roiattia.com.salariestrack.ui.salarieslist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import roiattia.com.salariestrack.R;
import roiattia.com.salariestrack.model.SalaryListItem;
import roiattia.com.salariestrack.ui.salary.SalaryActivity;
import roiattia.com.salariestrack.utils.Analytics;

import static roiattia.com.salariestrack.utils.Constants.SALARY_ID_EXTRA;

public class SalariesListActivity extends AppCompatActivity
    implements SalariesAdapter.OnSalaryClickHandler, SortDialog.SortDialogListener{

    private String TAG = SalariesListActivity.class.getSimpleName();

    private SalariesAdapter mAdapter;
    private SalariesViewModel mViewModel;
    private int mSortOption;
    private SortDialog mSortDialog;

    @BindView(R.id.rv_salaries) RecyclerView mSalaries;
    @BindView(R.id.tv_sort_title) TextView mSortTitle;
    @BindView(R.id.tv_no_salaries) TextView mNoSalaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaries_list);
        ButterKnife.bind(this);

        setupSortDialog();

        setupAd();

        setupRecyclerView();

        setupViewHolder();
    }

    private void setupSortDialog() {
        mSortOption = 0;
        mSortDialog = new SortDialog();
        final String[] sortOptions = getResources().getStringArray(R.array.sort_selection_options);
        mSortDialog.setData(sortOptions);
        mSortDialog.setTitle(getString(R.string.sort_selection_title));
    }

    private void setupAd() {
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    /**
     * Salary click event - open edit salary activity
     * @param salaryId salary's id to get sent to salary activity
     */
    @Override
    public void onSalaryClick(long salaryId) {
        Intent intent = new Intent(SalariesListActivity.this, SalaryActivity.class);
        intent.putExtra(SALARY_ID_EXTRA, salaryId);
        startActivity(intent);
    }

    /**
     * Setup salaries list recycler view
     */
    private void setupRecyclerView() {
        mAdapter = new SalariesAdapter(this, this);
        mSalaries.setLayoutManager(new LinearLayoutManager(this));
        mSalaries.setHasFixedSize(true);
        mSalaries.setAdapter(mAdapter);
    }

    /**
     * Setup view holder and set it's observe method
     */
    private void setupViewHolder() {
        mViewModel = ViewModelProviders.of(this).get(SalariesViewModel.class);
        mViewModel.getMutableLiveDataSalaries().observe(this, new Observer<List<SalaryListItem>>() {
            @Override
            public void onChanged(@Nullable List<SalaryListItem> salaryListItems) {
                if(salaryListItems != null){
                    mAdapter.setSalariesData(salaryListItems);
                    if(salaryListItems.size()>0) {
                        mNoSalaries.setVisibility(View.GONE);
                    } else {
                        mNoSalaries.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salaries_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_sort: // Open sort salaries dialog selection
                mSortDialog.show(getSupportFragmentManager(), "sort");
                break;
            case R.id.mi_debug_print: // log all salaries in db
                mViewModel.debugPrint();
                break;
            case R.id.mi_new_salary:
                Intent intent = new Intent(SalariesListActivity.this, SalaryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the selection number from the sort dialog
     * @param whichSelected index of selected sort oprion
     */
    @Override
    public void onDialogFinishClick(int whichSelected) {
        mSortOption = whichSelected;
        mSortTitle.setText(getResources().getStringArray(R.array.sort_selection_options)[whichSelected]);
        Analytics.logEventSortClick(this, mSortOption);
        loadDataByCategory();
    }

    /**
     * Load salaries according to selected sort option
     */
    private void loadDataByCategory() {
        switch (mSortOption){
            case 0: // not paid
                mViewModel.getSalariesItemsNotPaid();
                break;
            case 1: // no contract
                mViewModel.getSalariesItemsNoContract();
                break;
            case 2: // no receipt
                mViewModel.getSalariesItemsNoReceipt();
                break;
            case 3: // all salaries
                mViewModel.getSalariesItems();
                break;
        }
    }

    /**
     * Trigger salaries data retrieval by category
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadDataByCategory();
    }
}
